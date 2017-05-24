package be.sanderdebleecker.uselections;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import be.sanderdebleecker.uselections.core.di.components.DaggerElectionsComponent;
import be.sanderdebleecker.uselections.core.di.modules.ElectionsModule;
import be.sanderdebleecker.uselections.mvp.model.view.ElectionVM;
import be.sanderdebleecker.uselections.mvp.presenter.ElectionsPresenter;
import be.sanderdebleecker.uselections.mvp.view.ElectionsView;
import be.sanderdebleecker.uselections.mvp.view.adapters.ElectionsAdapter;
import butterknife.BindView;


public class ElectionsActivity extends BaseActivity implements ElectionsView {
    @BindView(R.id.elections_recycler) RecyclerView recyclerView;
    @Inject protected ElectionsPresenter mPresenter;
    private ElectionsAdapter mElectionsAdapter;
    public final static String EXTRA_ELECTION = "EXTRA_ELECTION";

    @Override
    protected void onViewReady (Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        mPresenter.requestPermissions();
        initializeAdapter();
        mPresenter.getElections();
    }
    private void initializeAdapter() {
        mElectionsAdapter = new ElectionsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mElectionsAdapter);
        mElectionsAdapter.getClickObservable().subscribe(this::onListClick);
    }
    // dagger2
    @Override
    protected void resolveDaggerDependency () {
        DaggerElectionsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .electionsModule(new ElectionsModule(this))
                .build().inject(this);
    }

    // abstract base methods
    @Override
    protected int getContentView () {
        return R.layout.activity_elections;
    }

    // recycler delegate
    public void onListClick (ElectionVM electionVM) {
        Intent intent = new Intent(getBaseContext(), OfficialsActivity.class);
        intent.putExtra(EXTRA_ELECTION, electionVM);
        startActivity(intent);
    }

    // interface ElectionsView
    @Override
    public BaseActivity getViewActivity () {
        return this;
    }
    @Override
    public void onElectionsLoaded (List<ElectionVM> elections) {
        mElectionsAdapter.add(elections);
    }
    @Override
    public void onClearItems () {
        mElectionsAdapter.clear();
    }
    @Override
    public void onShowDialog (String message) {
        showDialog(message);
    }
    @Override
    public void onHideDialog () {
        hideDialog();
    }
    @Override
    public void onShowToast (String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    // presenter handles permissions
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.onPermissionsResult(requestCode,permissions,grantResults);
    }
}
