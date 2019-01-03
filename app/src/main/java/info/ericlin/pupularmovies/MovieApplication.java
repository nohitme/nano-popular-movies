package info.ericlin.pupularmovies;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import info.ericlin.pupularmovies.dagger.DaggerApplicationComponent;
import info.ericlin.pupularmovies.timber.ThreadNameTree;
import timber.log.Timber;

public class MovieApplication extends DaggerApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    Timber.plant(new ThreadNameTree());
    Timber.d("timber trees planted, count: %s", Timber.treeCount());
  }

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return DaggerApplicationComponent.builder().application(this).build();
  }
}
