package be.sanderdebleecker.uselections.core.di.components;

import android.content.Context;

import javax.inject.Singleton;

import be.sanderdebleecker.uselections.core.di.modules.ApplicationModule;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * @author simulan
 * @version 1.0.0
 * @since 20/05/2017
 */

@Singleton
@Component(modules = ApplicationModule.class, dependencies = ApplicationComponent.class)
public interface ApplicationComponent {

    Retrofit exposeRetrofit();

    Context exposeContext();
}