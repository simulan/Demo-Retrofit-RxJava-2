package be.sanderdebleecker.uselections.core.di.components;

import be.sanderdebleecker.uselections.ElectionsActivity;
import be.sanderdebleecker.uselections.core.di.modules.ElectionsModule;
import be.sanderdebleecker.uselections.core.di.scope.PerActivity;
import dagger.Component;

/**
 * @author simulan
 * @version 1.0.0
 * @since 21/05/2017
 */
@PerActivity
@Component(modules = ElectionsModule.class, dependencies = ApplicationComponent.class)
public interface ElectionsComponent {
    void inject(ElectionsActivity activity);
}
