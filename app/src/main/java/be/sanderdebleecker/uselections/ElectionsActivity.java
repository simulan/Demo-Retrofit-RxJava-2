package be.sanderdebleecker.uselections;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import javax.inject.Inject;

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
        initializeAdapter();
        mPresenter.getElections();
    }
    private void initializeAdapter() {
        mElectionsAdapter = new ElectionsAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mElectionsAdapter);
        mElectionsAdapter.getClickObservable().subscribe(this::onListClick);
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
    }
    @Override
    public void onClearItems () {
    }
    @Override
    public void onShowDialog (String message) {
    }
    @Override
    public void onHideDialog () {
    }
    @Override
    public void onShowToast (String message) {
    }

    // presenter handles permissions
    public void onRequestPermissionsResult (int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPresenter.onPermissionsResult(requestCode,permissions,grantResults);
    }
}
