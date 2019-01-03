package info.ericlin.pupularmovies.dagger;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import info.ericlin.moviedb.MovieDbModule;
import info.ericlin.pupularmovies.MovieApplication;
import okhttp3.OkHttpClient;

@Singleton
@Component(
    modules = {
      AndroidSupportInjectionModule.class,
      ApplicationModule.class,
      ActivityModule.class,
      OkHttpModule.class,
      MovieDbModule.class,
    })
public interface ApplicationComponent extends AndroidInjector<MovieApplication> {

  // exposed for glide
  OkHttpClient okHttpClient();

  @Component.Builder
  interface Builder {

    @BindsInstance
    ApplicationComponent.Builder application(Application application);

    ApplicationComponent build();
  }
}
