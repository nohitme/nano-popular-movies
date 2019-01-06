package info.ericlin.pupularmovies.paging;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.paging.DataSource;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import info.ericlin.moviedb.MovieDbService;
import info.ericlin.moviedb.model.Movie;
import info.ericlin.moviedb.model.MovieList;
import io.reactivex.Single;

/** Data source factory for movie posters */
@AutoFactory
public class MoviePosterDataSourceFactory extends DataSource.Factory<Integer, Movie> {

  /**
   * Category for movies, see <a
   * href="https://developers.themoviedb.org/3/movies">https://developers.themoviedb.org/3/movies</a>
   * for details
   */
  public enum Category {
    LATEST,
    NOW_PLAYING,
    POPULAR,
    TOP_RATED,
    UPCOMING
  }

  private final Category category;
  private final MovieDbService movieDbService;

  public MoviePosterDataSourceFactory(Category category, @Provided MovieDbService movieDbService) {
    this.category = category;
    this.movieDbService = movieDbService;
  }

  @NonNull
  @Override
  public DataSource<Integer, Movie> create() {

    final Function<Integer, Single<MovieList>> movieFetcher;
    switch (category) {
      case LATEST:
        movieFetcher = movieDbService::getLatestMovies;
        break;
      case NOW_PLAYING:
        movieFetcher = movieDbService::getNowPlayingMovies;
        break;
      case TOP_RATED:
        movieFetcher = movieDbService::getTopRatedMovies;
        break;
      case UPCOMING:
        movieFetcher = movieDbService::getUpcomingMovies;
        break;
      case POPULAR:
      default:
        movieFetcher = movieDbService::getPopularMovies;
        break;
    }

    return new MoviePosterDataSource(movieFetcher);
  }
}
