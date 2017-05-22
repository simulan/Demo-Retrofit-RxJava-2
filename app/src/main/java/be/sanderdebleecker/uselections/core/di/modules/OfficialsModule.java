package be.sanderdebleecker.uselections.core.di.modules;

import be.sanderdebleecker.uselections.api.CivicService;
import be.sanderdebleecker.uselections.core.di.scope.PerActivity;
import be.sanderdebleecker.uselections.mvp.view.OfficialsView;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * @author Sander De Bleecker
 * @version 1.0.0
 * @since 22/05/2017
 */
@Module
public class OfficialsModule {
    private OfficialsView mView;

    public OfficialsModule(OfficialsView view) {
        mView = view;
    }

    @PerActivity
    @Provides
    CivicService provideApiService(Retrofit retrofit) {
        return retrofit.create(CivicService.class);
    }

    @PerActivity
    @Provides
    OfficialsView provideView() {
        return mView;
    }
}
