package be.sanderdebleecker.uselections.mvp.presenter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import javax.inject.Inject;

import be.sanderdebleecker.uselections.BaseActivity;
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
    final String[] REQ_PERMISSIONS = {Manifest.permission.INTERNET};
    final int PERMISSION_INTERNET = 1;
    boolean hasInternetPermission = false;
    Disposable responseDisposable;

    @Inject
    public ElectionsPresenter() {

    }
    public void getElections() {
        if(hasInternetPermission) {
            getView().onShowDialog("Loading cakes...");
            subscribe(mApiService.listElections(),this);
        }
    }

    //interface Observer
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
        final boolean isNougat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
        if (isNougat) {
            BaseActivity activity = getView().getViewActivity();
            if (!hasPermissions(activity, REQ_PERMISSIONS)) {
                ActivityCompat.requestPermissions(activity, REQ_PERMISSIONS, PERMISSION_INTERNET);
            } else {
                hasInternetPermission = true;
            }
        }else {
            hasInternetPermission = true;
        }
    }
     private boolean hasPermissions (Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
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
