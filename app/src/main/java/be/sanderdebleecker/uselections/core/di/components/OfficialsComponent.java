package be.sanderdebleecker.uselections.core.di.components;

import javax.inject.Singleton;

import be.sanderdebleecker.uselections.ElectionsActivity;
import be.sanderdebleecker.uselections.core.di.modules.OfficialsModule;
import dagger.Component;

/**
 * @author Sander De Bleecker
 * @version 1.0.0
 * @since 22/05/2017
 */
@Singleton
@Component(modules = OfficialsModule.class)
public interface OfficialsComponent {
    void inject(ElectionsActivity activity);
}
