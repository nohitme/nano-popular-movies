package info.ericlin.pupularmovies.dagger;

import android.app.Application;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import info.ericlin.moviedb.MovieDbModule;
import info.ericlin.pupularmovies.MovieApplication;
import info.ericlin.pupularmovies.database.RoomDatabaseModule;
import info.ericlin.util.ResourceModule;
import javax.inject.Singleton;

@Singleton
@Component(
    modules = {
        AndroidSupportInjectionModule.class,
        AndroidViewModule.class,
        ApplicationModule.class,
        OkHttpModule.class,
        MovieDbModule.class,
        ViewModelModule.class,
        ResourceModule.class,
        RoomDatabaseModule.class,
    })
public interface ApplicationComponent extends AndroidInjector<MovieApplication> {

  // exposed for glide
  void inject(MovieGlideModule movieGlideModule);

  @Component.Builder
  interface Builder {

    @BindsInstance
    ApplicationComponent.Builder application(Application application);

    ApplicationComponent build();
  }
}
