package info.ericlin.pupularmovies.database;

import android.app.Application;
import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class RoomDatabaseModule {

  @Provides
  public static AppDatabase appDatabase(Application application) {
    return Room.databaseBuilder(application, AppDatabase.class, "app-database").build();
  }
}
