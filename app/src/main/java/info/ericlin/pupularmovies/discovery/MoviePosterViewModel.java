package info.ericlin.pupularmovies.discovery;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;

import info.ericlin.moviedb.model.Movie;
import info.ericlin.pupularmovies.factory.ViewModelFactory;

/** View model for {@link MoviePosterFragment} */
@AutoFactory(implementing = ViewModelFactory.class)
public class MoviePosterViewModel extends AndroidViewModel {

  private final MoviePosterDataSourceFactoryFactory moviePosterDataSourceFactoryFactory;

  @Nullable private LiveData<PagedList<Movie>> movieLists;

  public MoviePosterViewModel(
      @NonNull Application application,
      @Provided MoviePosterDataSourceFactoryFactory moviePosterDataSourceFactoryFactory) {
    super(application);
    this.moviePosterDataSourceFactoryFactory = moviePosterDataSourceFactoryFactory;
  }

  public void init(@NonNull MovieCategory category) {
    final MoviePosterDataSourceFactory dataSourceFactory =
        moviePosterDataSourceFactoryFactory.create(category);

    // size does not matter since movie db does not support page size
    movieLists = new LivePagedListBuilder<>(dataSourceFactory, 20).build();
  }

  @Nullable
  public LiveData<PagedList<Movie>> getMovieLists() {
    return movieLists;
  }
}
