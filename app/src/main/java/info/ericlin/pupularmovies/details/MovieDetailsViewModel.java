package info.ericlin.pupularmovies.details;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.request.FutureTarget;
import com.google.auto.factory.AutoFactory;
import com.google.auto.factory.Provided;
import com.google.common.base.Objects;

import info.ericlin.moviedb.MovieDbService;
import info.ericlin.moviedb.glide.MovieDbImagePath;
import info.ericlin.moviedb.model.Movie;
import info.ericlin.moviedb.model.MovieWithDetails;
import info.ericlin.pupularmovies.dagger.GlideApp;
import info.ericlin.pupularmovies.factory.ViewModelFactory;
import info.ericlin.util.ExecutorProvider;
import io.reactivex.Single;

/** View model for {@link info.ericlin.pupularmovies.DetailActivity} */
@AutoFactory(implementing = ViewModelFactory.class)
public class MovieDetailsViewModel extends AndroidViewModel {

  @NonNull private final MovieDbService movieDbService;
  @NonNull private final ExecutorProvider executorProvider;

  private final MutableLiveData<Integer> movieIdLiveData = new MutableLiveData<>();
  private final LiveData<MoviePalette> movieLiveData =
      Transformations.switchMap(movieIdLiveData, this::getMoviePaletteById);

  MovieDetailsViewModel(
      @NonNull Application application,
      @NonNull @Provided MovieDbService movieDbService,
      @NonNull @Provided ExecutorProvider executorProvider) {
    super(application);
    this.movieDbService = movieDbService;
    this.executorProvider = executorProvider;
  }

  public LiveData<MoviePalette> getMovieLiveData() {
    return movieLiveData;
  }

  public void setMovieId(int movieId) {
    if (!Objects.equal(movieId, movieIdLiveData.getValue())) {
      movieIdLiveData.postValue(movieId);
    }
  }

  private LiveData<MoviePalette> getMoviePaletteById(int movieId) {
    final Single<MovieWithDetails> movie = movieDbService.getMovieWithDetails(movieId);
    final Single<Palette> palette = movie.flatMap(this::extractPalette);
    final Single<MoviePalette> moviePalette = movie.zipWith(palette, MoviePalette::create);

    return LiveDataReactiveStreams.fromPublisher(moviePalette.toFlowable());
  }

  private Single<Palette> extractPalette(@NonNull MovieWithDetails movie) {

    final FutureTarget<Bitmap> futureTarget =
        GlideApp.with(getApplication())
            .asBitmap()
            .load(MovieDbImagePath.backdrop(movie.backdrop_path()))
            .submit(800, 450);

    return Single.fromFuture(futureTarget)
        .observeOn(executorProvider.ioScheduler())
        .map(bitmap -> Palette.from(bitmap).generate());
  }
}
