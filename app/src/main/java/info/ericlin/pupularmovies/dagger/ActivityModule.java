package info.ericlin.pupularmovies.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import info.ericlin.pupularmovies.MainActivity;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract MainActivity mainActivity();
}
