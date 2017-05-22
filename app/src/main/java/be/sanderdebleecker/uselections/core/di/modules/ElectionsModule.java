package be.sanderdebleecker.uselections.core.di.modules;

import be.sanderdebleecker.uselections.api.CivicService;
import be.sanderdebleecker.uselections.core.di.scope.PerActivity;
import be.sanderdebleecker.uselections.mvp.view.ElectionsView;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author simulan
 * @version 1.0.0
 * @since 20/05/2017
 */
@Module
public class ElectionsModule {
    private ElectionsView mView;

    public ElectionsModule(ElectionsView view) {
        mView = view;
    }

    @PerActivity
    @Provides
    CivicService provideApiService(Retrofit retrofit) {
        return retrofit.create(CivicService.class);
    }

    @PerActivity
    @Provides
    ElectionsView provideView() {
        return mView;
    }
}
