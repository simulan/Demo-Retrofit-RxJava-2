package be.sanderdebleecker.uselections;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import be.sanderdebleecker.uselections.controller.api.CivicService;
import be.sanderdebleecker.uselections.controller.api.ServiceFactory;
import be.sanderdebleecker.uselections.model.view.ElectionVM;
import be.sanderdebleecker.uselections.view.adapters.ElectionsAdapter;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ElectionsActivity extends AppCompatActivity {
    public final static String EXTRA_ELECTION = "EXTRA_ELECTION";
    final int PERMISSION_INTERNET = 1;
    boolean hasInternetPermission = false;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elections);
        recyclerView = (RecyclerView) findViewById(R.id.elections_recycler);
        getPermissions();
        if (hasInternetPermission) load();
    }

    private void load () {
        ElectionsAdapter adapter = new ElectionsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.getClickObservable().subscribe(this::onListClick);
        CivicService service = ServiceFactory.create(CivicService.class);
        service.listElections()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);

    }
    public void onListClick (ElectionVM electionVM) {
        Intent intent = new Intent(getBaseContext(), OfficialsActivity.class);
        intent.putExtra(EXTRA_ELECTION, electionVM);
        startActivity(intent);
    }
    protected void getPermissions () {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] PERMISSIONS = {Manifest.permission.INTERNET};
            if (!hasPermissions(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_INTERNET);
            } else {
                hasInternetPermission = true;
            }
        }
    }
    public boolean hasPermissions (Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length < 1) return;
        if (requestCode == PERMISSION_INTERNET && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            hasInternetPermission = true;
        } else {
            Toast.makeText(this, "Actie werd niet toegestaan", Toast.LENGTH_SHORT).show();
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
