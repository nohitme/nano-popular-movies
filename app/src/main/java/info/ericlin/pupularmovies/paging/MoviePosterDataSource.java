package info.ericlin.pupularmovies.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.core.util.Consumer;
import androidx.paging.PageKeyedDataSource;

import info.ericlin.moviedb.model.Movie;
import info.ericlin.moviedb.model.MovieList;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

/** Data source for movie posters. */
public class MoviePosterDataSource extends PageKeyedDataSource<Integer, Movie> {

  private final CompositeDisposable disposables = new CompositeDisposable();
  @NonNull private final Function<Integer, Single<MovieList>> movieFetcher;

  public MoviePosterDataSource(@NonNull Function<Integer, Single<MovieList>> movieFetcher) {
    this.movieFetcher = movieFetcher;
  }

  @Override
  public void invalidate() {
    super.invalidate();
    disposables.clear();
  }

  @Override
  public void loadInitial(
      @NonNull LoadInitialParams<Integer> params,
      @NonNull LoadInitialCallback<Integer, Movie> callback) {
    loadWithPageKey(
        null,
        movieList ->
            callback.onResult(
                movieList.results(), 0, movieList.total_results(), null, movieList.page() + 1));
  }

  @Override
  public void loadBefore(
      @NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
    throw new UnsupportedOperationException("paged source should not rewind");
  }

  @Override
  public void loadAfter(
      @NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    loadWithPageKey(
        params.key, movieList -> callback.onResult(movieList.results(), movieList.page() + 1));
  }

  private void loadWithPageKey(@Nullable Integer page, Consumer<MovieList> callback) {
    final DisposableSingleObserver<MovieList> disposable =
        movieFetcher
            .apply(page)
            .subscribeOn(Schedulers.io())
            .subscribeWith(
                new DisposableSingleObserver<MovieList>() {
                  @Override
                  public void onSuccess(MovieList movieList) {
                    callback.accept(movieList);
                  }

                  @Override
                  public void onError(Throwable e) {
                    Timber.w(e);
                  }
                });

    disposables.add(disposable);
  }
}
