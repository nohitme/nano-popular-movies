package info.ericlin.pupularmovies;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PreferencesManager {

  private static final String KEY_IS_DIRTY = "is-dirty";
  private final SharedPreferences sharedPreferences;

  @Inject
  public PreferencesManager(Application application) {
    sharedPreferences = application.getSharedPreferences("movie-preferences", Context.MODE_PRIVATE);
  }

  /**
   * Returns true if the favorite has been modified and we need to update the favorite list
   */
  public boolean isFavoritesDirty() {
    return sharedPreferences.getBoolean(KEY_IS_DIRTY, false);
  }

  public void setFavoritesDirty(boolean isDirty) {
    sharedPreferences.edit().putBoolean(KEY_IS_DIRTY, isDirty).apply();
  }
}
