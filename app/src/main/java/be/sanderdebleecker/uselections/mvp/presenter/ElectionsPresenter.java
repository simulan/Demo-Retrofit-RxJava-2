package be.sanderdebleecker.uselections.mvp.presenter;

import android.content.pm.PackageManager;

import javax.inject.Inject;

import be.sanderdebleecker.uselections.api.CivicService;
import be.sanderdebleecker.uselections.mvp.model.data.envelope.ElectionsEnvelope;
import be.sanderdebleecker.uselections.mvp.presenter.expansions.PermissionPresenter;
import be.sanderdebleecker.uselections.mvp.view.ElectionsView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Sander De Bleecker
 * @version 1.0.0
 * @since 19/05/2017
 */

public class ElectionsPresenter extends BasePresenter<ElectionsView> implements Observer<ElectionsEnvelope>, PermissionPresenter {
    @Inject protected CivicService mApiService;
    final int PERMISSION_INTERNET = 1;
    boolean hasInternetPermission = false;
    Disposable responseDisposable;

    @Inject
    public ElectionsPresenter() {

    }

    public void getElections() {
        getView().onShowDialog("Loading cakes...");
        subscribe(mApiService.listElections(),this);
    }

    @Override
    public void onSubscribe (Disposable d) {
        responseDisposable = d;
    }

    @Override
    public void onNext (ElectionsEnvelope electionsEnvelope) {
        if(electionsEnvelope.getElections()==null) return;
        getView().onClearItems();
        getView().onElectionsLoaded(electionsEnvelope.getElections());
    }

    @Override
    public void onError (Throwable e) {
        getView().onShowToast(e.getMessage());
        responseDisposable.dispose();
    }

    @Override
    public void onComplete () {
        responseDisposable.dispose();
    }

    //interface PermissionPresenter
    @Override
    public void requestPermissions () {

    }
    @Override
    public void onPermissionsResult (int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length < 1) return;
        if (requestCode == PERMISSION_INTERNET && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            hasInternetPermission = true;
        } else {
            getView().onShowToast("Actie werd niet toegestaan");
        }

    }
}
