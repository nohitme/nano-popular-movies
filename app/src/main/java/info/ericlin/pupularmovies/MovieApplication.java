package info.ericlin.pupularmovies;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import info.ericlin.pupularmovies.dagger.DaggerApplicationComponent;

public class MovieApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }
}
