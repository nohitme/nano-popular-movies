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
import com.google.common.collect.Lists;
import info.ericlin.moviedb.Identifiable;
import info.ericlin.moviedb.MovieDbService;
import info.ericlin.moviedb.glide.MovieDbImagePath;
import info.ericlin.moviedb.model.MovieWithDetails;
import info.ericlin.pupularmovies.dagger.GlideApp;
import info.ericlin.pupularmovies.factory.ViewModelFactory;
import info.ericlin.util.ExecutorProvider;
import io.reactivex.Single;
import java.util.List;

/** View model for {@link info.ericlin.pupularmovies.DetailActivity} */
@AutoFactory(implementing = ViewModelFactory.class)
public class MovieDetailsViewModel extends AndroidViewModel {

  @NonNull private final MovieDbService movieDbService;
  @NonNull private final ExecutorProvider executorProvider;

  private final MutableLiveData<Integer> movieIdLiveData = new MutableLiveData<>();
  private final LiveData<List<Identifiable>> detailsListLiveData =
      Transformations.switchMap(movieIdLiveData, this::getDetailsListItem);

  MovieDetailsViewModel(
      @NonNull Application application,
      @NonNull @Provided MovieDbService movieDbService,
      @NonNull @Provided ExecutorProvider executorProvider) {
    super(application);
    this.movieDbService = movieDbService;
    this.executorProvider = executorProvider;
  }

  public void setMovieId(int movieId) {
    if (!Objects.equal(movieId, movieIdLiveData.getValue())) {
      movieIdLiveData.postValue(movieId);
    }
  }

  public LiveData<List<Identifiable>> getDetailsListLiveData() {
    return detailsListLiveData;
  }

  private LiveData<List<Identifiable>> getDetailsListItem(int movieId) {
    final Single<MovieWithDetails> movie = movieDbService.getMovieWithDetails(movieId);
    final Single<Palette> palette = movie.flatMap(this::extractPalette);
    final Single<MovieSwatch> moviePalette =
        movie.zipWith(palette, (m, p) -> MovieSwatch.create(m, p.getDominantSwatch()));
    Single<List<Identifiable>> items = moviePalette.map(this::convertToRecyclerViewItems);

    return LiveDataReactiveStreams.fromPublisher(items.toFlowable());
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

  private List<Identifiable> convertToRecyclerViewItems(MovieSwatch movieSwatch) {
    return Lists.newArrayList(movieSwatch, movieSwatch.movie());
  }
}
