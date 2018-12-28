package info.ericlin.pupularmovies.dagger;

import android.app.Application;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

  @Provides
  Context context(Application application) {
    return application;
  }
}
