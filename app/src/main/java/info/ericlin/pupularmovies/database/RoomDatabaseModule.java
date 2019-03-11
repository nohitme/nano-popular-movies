package info.ericlin.pupularmovies.database;

import android.app.Application;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public abstract class RoomDatabaseModule {

  @Provides
  @Singleton
  public static AppDatabase appDatabase(Application application) {
    return Room.databaseBuilder(application, AppDatabase.class, "app-database").build();
  }
}
