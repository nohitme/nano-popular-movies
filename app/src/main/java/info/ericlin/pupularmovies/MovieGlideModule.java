package info.ericlin.pupularmovies;

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

import okhttp3.OkHttpClient;

@GlideModule
@Excludes({com.bumptech.glide.integration.okhttp3.OkHttpLibraryGlideModule.class})
@SuppressWarnings("unused")
public class MovieGlideModule extends AppGlideModule {
  @Override
  public void registerComponents(
      @NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    final MovieApplication movieApplication = (MovieApplication) context.getApplicationContext();
    final OkHttpClient okHttpClient = movieApplication.getApplicationComponent().okHttpClient();
    registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(okHttpClient));
  }

  @Override
  public boolean isManifestParsingEnabled() {
    return false;
  }
}
