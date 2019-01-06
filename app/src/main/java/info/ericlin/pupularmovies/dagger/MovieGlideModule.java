package info.ericlin.pupularmovies.dagger;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.Excludes;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;

import java.io.InputStream;

import javax.inject.Inject;

import info.ericlin.moviedb.glide.MovieDbImagePath;
import info.ericlin.moviedb.glide.MovieDbImagePathLoaderFactory;
import info.ericlin.pupularmovies.MovieApplication;
import okhttp3.OkHttpClient;

@GlideModule
@Excludes({com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule.class})
@SuppressWarnings("unused")
public class MovieGlideModule extends AppGlideModule {

  @Inject OkHttpClient okHttpClient;

  @Inject MovieDbImagePathLoaderFactory movieDbImagePathLoaderFactory;

  @Override
  public void registerComponents(
      @NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    final MovieApplication movieApplication = (MovieApplication) context.getApplicationContext();
    movieApplication.getApplicationComponent().inject(this);

    registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
    registry.append(MovieDbImagePath.class, InputStream.class, movieDbImagePathLoaderFactory);
  }

  @Override
  public boolean isManifestParsingEnabled() {
    return false;
  }
}
