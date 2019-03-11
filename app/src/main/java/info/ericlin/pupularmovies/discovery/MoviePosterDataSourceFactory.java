package info.ericlin.pupularmovies.discovery;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.paging.DataSource;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import info.ericlin.moviedb.MovieDbService;
import info.ericlin.moviedb.model.Movie;
import info.ericlin.moviedb.model.MovieList;
import info.ericlin.pupularmovies.database.AppDatabase;
import info.ericlin.pupularmovies.database.ModelConverter;
import info.ericlin.util.ExecutorProvider;
import io.reactivex.Single;

/** Data source factory for movie posters */
@AutoFactory
public class MoviePosterDataSourceFactory extends DataSource.Factory<Integer, Movie> {

  private final MovieCategory category;
  private final MovieDbService movieDbService;
  private final ExecutorProvider executorProvider;
  private final DataSource.Factory<Integer, Movie> saveMovieFactory;

  public MoviePosterDataSourceFactory(
      MovieCategory category,
      @Provided MovieDbService movieDbService,
      @Provided AppDatabase appDatabase,
      @Provided ModelConverter modelConverter,
      @Provided ExecutorProvider executorProvider) {
    this.category = category;
    this.movieDbService = movieDbService;
    this.executorProvider = executorProvider;
    saveMovieFactory = appDatabase.movieDao().getAllMovies().map(modelConverter::convertToMovie);
  }

  @NonNull
  @Override
  public DataSource<Integer, Movie> create() {
    if (category == MovieCategory.FAVORITE) {
      return saveMovieFactory.create();
    }

    final Function<Integer, Single<MovieList>> movieFetcher;
    switch (category) {
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

    return new MoviePosterDataSource(movieFetcher, executorProvider);
  }
}
