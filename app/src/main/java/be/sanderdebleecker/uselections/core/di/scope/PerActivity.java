package be.sanderdebleecker.uselections.core.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * @author Sander De Bleecker
 * @version 1.0.0
 * @since 20/05/2017
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
