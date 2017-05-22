package be.sanderdebleecker.uselections.core.di.components;

import javax.inject.Singleton;

import be.sanderdebleecker.uselections.ElectionsActivity;
import be.sanderdebleecker.uselections.core.di.modules.ElectionsModule;
import dagger.Component;

/**
 * @author simulan
 * @version 1.0.0
 * @since 21/05/2017
 */
@Singleton
@Component(modules = ElectionsModule.class)
public interface ElectionsComponent {
    void inject(ElectionsActivity activity);
}
