package be.sanderdebleecker.uselections;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import be.sanderdebleecker.uselections.core.di.components.DaggerOfficialsComponent;
import be.sanderdebleecker.uselections.core.di.modules.OfficialsModule;
import be.sanderdebleecker.uselections.mvp.model.view.ElectionVM;
import be.sanderdebleecker.uselections.mvp.model.view.OfficialVM;
import be.sanderdebleecker.uselections.mvp.presenter.OfficialsPresenter;
import be.sanderdebleecker.uselections.mvp.view.OfficialsView;
import be.sanderdebleecker.uselections.mvp.view.adapters.OfficialsAdapter;
import butterknife.BindView;

/**
 * Activity displaying Officials
 * Created by simulan
 */

public class OfficialsActivity extends BaseActivity implements OfficialsView {
    @BindView(R.id.recyclerOfficials) RecyclerView recyclerView;
    @Inject protected OfficialsPresenter mPresenter;
    OfficialsAdapter mAdapter;

    @Override
    protected void onViewReady (Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        ElectionVM electionVM = getIntent().getParcelableExtra(ElectionsActivity.EXTRA_ELECTION);
        initializeAdapter();
        mPresenter.getOfficials(electionVM.getOcdDivisionId());
    }
    private void initializeAdapter() {
        mAdapter = new OfficialsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mAdapter.getClickObservable().subscribe(this::onOfficialsListClick);
    }

    // dagger2
    @Override
    protected void resolveDaggerDependency () {
        DaggerOfficialsComponent.builder()
                .applicationComponent(getApplicationComponent())
                .officialsModule(new OfficialsModule(this))
                .build().inject(this);
    }

    //interface BaseActivity
    @Override
    protected int getContentView () {
        return R.layout.activity_officials;
    }

    //Interface ElectionsView
    @Override
    public void onOfficialsLoaded (List<OfficialVM> officials) {
        mAdapter.add(officials);
    }
    @Override
    public void onClearItems () {
        mAdapter.clear();
    }
    @Override
    public BaseActivity getViewActivity () {
        return this;
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

    //recycler delegate
    public void onOfficialsListClick (OfficialVM official) {
        Toast.makeText(this,official.getName(),Toast.LENGTH_SHORT).show();
    }

}
