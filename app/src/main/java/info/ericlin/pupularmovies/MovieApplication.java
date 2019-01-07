package info.ericlin.pupularmovies;

import android.os.StrictMode;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import info.ericlin.pupularmovies.dagger.ApplicationComponent;
import info.ericlin.pupularmovies.dagger.DaggerApplicationComponent;
import info.ericlin.pupularmovies.timber.ThreadNameTree;
import timber.log.Timber;

public class MovieApplication extends DaggerApplication {

  private ApplicationComponent applicationComponent;

  @Override
  public void onCreate() {
    applicationComponent = DaggerApplicationComponent.builder().application(this).build();
    super.onCreate();
    Timber.plant(new ThreadNameTree());
    Timber.d("timber trees planted, count: %s", Timber.treeCount());

    StrictMode.setThreadPolicy(
        new StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
    StrictMode.setVmPolicy(
        new StrictMode.VmPolicy.Builder().detectAll().penaltyLog().penaltyDeath().build());
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }

  @Override
  protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
    return applicationComponent;
  }
}
