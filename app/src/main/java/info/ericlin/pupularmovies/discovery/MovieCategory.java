package info.ericlin.pupularmovies.discovery;

import androidx.annotation.StringRes;

import info.ericlin.pupularmovies.R;

/**
 * Category for movies, see <a
 * href="https://developers.themoviedb.org/3/movies">https://developers.themoviedb.org/3/movies</a>
 * for api details
 */
public enum MovieCategory {
  NOW_PLAYING(R.string.tab_now_playing),
  POPULAR(R.string.tab_popular),
  TOP_RATED(R.string.tab_top_rated),
  UPCOMING(R.string.tab_upcoming);

  private final int stringRes;

  MovieCategory(@StringRes int stringRes) {
    this.stringRes = stringRes;
  }

  @StringRes
  public int getStringRes() {
    return stringRes;
  }
}
