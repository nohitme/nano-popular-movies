package info.ericlin.pupularmovies.dagger;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import info.ericlin.pupularmovies.MainActivity;

@Module
abstract class ActivityModule {

  @ContributesAndroidInjector
  abstract MainActivity mainActivity();
}
