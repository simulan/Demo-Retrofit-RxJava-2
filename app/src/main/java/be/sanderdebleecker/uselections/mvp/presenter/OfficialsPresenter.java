package be.sanderdebleecker.uselections.mvp.presenter;

import javax.inject.Inject;

import be.sanderdebleecker.uselections.api.CivicService;
import be.sanderdebleecker.uselections.mvp.model.data.envelope.OfficialsEnvelope;
import be.sanderdebleecker.uselections.mvp.view.OfficialsView;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Sander De Bleecker
 * @version 1.0.0
 * @since 22/05/2017
 */

public class OfficialsPresenter extends BasePresenter<OfficialsView> implements Observer<OfficialsEnvelope> {
    @Inject protected CivicService mApiService;
    Disposable responseDisposable;

    public OfficialsPresenter () {
    }
    public void getOfficials (String ocdId) {
        getView().onShowDialog("Loading cakes...");
        subscribe(mApiService.listOfficials(ocdId),this);
    }

    //interface Observer
    @Override
    public void onSubscribe (Disposable d) {
        responseDisposable = d;
    }

    @Override
    public void onNext (OfficialsEnvelope officialsEnvelope) {
        if (officialsEnvelope.getOfficials() == null) return;
        getView().onClearItems();
        getView().onOfficialsLoaded(officialsEnvelope.getOfficials());
    }

    @Override
    public void onError (Throwable e) {
        responseDisposable.dispose();
    }

    @Override
    public void onComplete () {
        responseDisposable.dispose();
    }
}
