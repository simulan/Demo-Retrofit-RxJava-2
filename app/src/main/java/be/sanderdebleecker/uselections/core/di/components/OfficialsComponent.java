package be.sanderdebleecker.uselections.core.di.components;

import be.sanderdebleecker.uselections.OfficialsActivity;
import be.sanderdebleecker.uselections.core.di.modules.OfficialsModule;
import be.sanderdebleecker.uselections.core.di.scope.PerActivity;
import dagger.Component;

/**
 * @author simulan
 * @version 1.0.0
 * @since 22/05/2017
 */
@PerActivity
@Component(modules = OfficialsModule.class, dependencies = ApplicationComponent.class)
public interface OfficialsComponent {
    void inject(OfficialsActivity activity);
}
