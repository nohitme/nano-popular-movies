package info.ericlin.moviedb.glide;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;

import java.io.InputStream;

import javax.inject.Inject;

import info.ericlin.moviedb.MovieDbService;

public class MovieDbImagePathLoaderFactory
    implements ModelLoaderFactory<MovieDbImagePath, InputStream> {

  private final MovieDbService movieDbService;

  @Inject
  public MovieDbImagePathLoaderFactory(MovieDbService movieDbService) {
    this.movieDbService = movieDbService;
  }

  @NonNull
  @Override
  public ModelLoader<MovieDbImagePath, InputStream> build(
      @NonNull MultiModelLoaderFactory multiFactory) {
    final ModelLoader<GlideUrl, InputStream> delegate =
        multiFactory.build(GlideUrl.class, InputStream.class);

    return new MovieDbImagePathLoader(delegate, movieDbService);
  }

  @Override
  public void teardown() {
    // do nothing
  }
}
