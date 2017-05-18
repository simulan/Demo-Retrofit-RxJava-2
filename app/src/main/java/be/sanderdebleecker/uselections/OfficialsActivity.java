package be.sanderdebleecker.uselections;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import be.sanderdebleecker.uselections.controller.api.CivicService;
import be.sanderdebleecker.uselections.controller.api.ServiceFactory;
import be.sanderdebleecker.uselections.model.data.envelope.OfficialsEnvelope;
import be.sanderdebleecker.uselections.model.view.ElectionVM;
import be.sanderdebleecker.uselections.model.view.OfficialVM;
import be.sanderdebleecker.uselections.view.adapters.OfficialsAdapter;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Activity displaying Officials
 * Created by Sander De Bleecker
 */

public class OfficialsActivity extends AppCompatActivity {
    Observable<OfficialsEnvelope> officials;
    RecyclerView recyclerView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officials);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerOfficials);
        ElectionVM electionVM = getIntent().getParcelableExtra(ElectionsActivity.EXTRA_ELECTION);
        load(electionVM);
    }
    private void load(ElectionVM electionVM) {
        OfficialsAdapter adapter = new OfficialsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.getClickObservable().subscribe(this::onOfficialsListClick);
        CivicService service = ServiceFactory.create(CivicService.class);
        service.listOfficials(electionVM.getOcdDivisionId())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(adapter);
    }
    public void onOfficialsListClick (OfficialVM official) {
        Toast.makeText(this,official.getName(),Toast.LENGTH_SHORT).show();
    }

}
