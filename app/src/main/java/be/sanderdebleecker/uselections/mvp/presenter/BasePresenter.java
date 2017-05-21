package be.sanderdebleecker.uselections.mvp.presenter;

import javax.inject.Inject;

import be.sanderdebleecker.uselections.mvp.view.BaseView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Sander De Bleecker
 * @version 1.0.0
 * @since 19/05/2017
 */

public class BasePresenter<V extends BaseView> {
    @Inject protected V mView;

    protected V getView () {
        return mView;
    }

    //todo: toSingle was deprecated
    protected <T> void subscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
