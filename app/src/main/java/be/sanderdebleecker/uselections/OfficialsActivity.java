package be.sanderdebleecker.uselections;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import be.sanderdebleecker.uselections.api.CivicService;
import be.sanderdebleecker.uselections.api.ServiceFactory;
import be.sanderdebleecker.uselections.mvp.model.data.envelope.OfficialsEnvelope;
import be.sanderdebleecker.uselections.mvp.model.view.ElectionVM;
import be.sanderdebleecker.uselections.mvp.model.view.OfficialVM;
import be.sanderdebleecker.uselections.mvp.presenter.OfficialsPresenter;
import be.sanderdebleecker.uselections.mvp.view.OfficialsView;
import be.sanderdebleecker.uselections.mvp.view.adapters.OfficialsAdapter;
import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Activity displaying Officials
 * Created by Sander De Bleecker
 */

public class OfficialsActivity extends BaseActivity implements OfficialsView {
    @BindView(R.id.recyclerOfficials) RecyclerView recyclerView;
    @Inject protected OfficialsPresenter mPresenter;

    @Override
    protected void onViewReady (Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        ElectionVM electionVM = getIntent().getParcelableExtra(ElectionsActivity.EXTRA_ELECTION);
        initializeAdapter();
        mPresenter.getOfficials(electionVM.getOcdDivisionId());
    }
    private void initializeAdapter() {
        OfficialsAdapter adapter = new OfficialsAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.getClickObservable().subscribe(this::onOfficialsListClick);
    }

    //interface BaseActivity
    @Override
    protected int getContentView () {
        return R.id.recyclerOfficials;
    }

    //Interface ElectionsView
    @Override
    public void onOfficialsLoaded (List<OfficialVM> elections) {
    }
    @Override
    public void onClearItems () {
    }
    @Override
    public BaseActivity getViewActivity () {
        return this;
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

    //recycler delegate
    public void onOfficialsListClick (OfficialVM official) {
        Toast.makeText(this,official.getName(),Toast.LENGTH_SHORT).show();
    }

}
