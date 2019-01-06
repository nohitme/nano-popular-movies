package info.ericlin.moviedb.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.bumptech.glide.request.target.Target;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;

import info.ericlin.moviedb.MovieDbService;
import info.ericlin.moviedb.model.Configuration;
import timber.log.Timber;

/**
 * A fancy custom model loader for MovieDB that achieves two things:
 *
 * <ol>
 *   <li>constructor the full url (image base url is from {@link
 *       info.ericlin.moviedb.model.Configuration})
 *   <li>determine the size to use based the target size dynamically (see reference: <a
 *       href="http://bit.ly/2Rxdwhq">http://bit.ly/2Rxdwhq</a>)
 * </ol>
 */
public class MovieDbImagePathLoader extends BaseGlideUrlLoader<MovieDbImagePath> {

  private static final String SIZE_ORIGINAL = "original";
  private static final String IMAGE_WIDTH_PREFIX = "w";
  // an arbitrary number since loading anything below is kind of wasteful
  private static final int MINIMAL_IMAGE_WIDTH = 200;
  private final MovieDbService movieDbService;

  @Nullable private Configuration configuration;
  @Nullable private EnumMap<MovieDbImagePath.Type, List<Integer>> typeSizes;

  protected MovieDbImagePathLoader(
      ModelLoader<GlideUrl, InputStream> concreteLoader, MovieDbService movieDbService) {
    super(concreteLoader);
    this.movieDbService = movieDbService;
  }

  @Override
  public boolean handles(@NonNull MovieDbImagePath movieDbImagePath) {
    return true;
  }

  @Override
  protected String getUrl(
      MovieDbImagePath movieDbImagePath, int width, int height, Options options) {
    final String path = movieDbImagePath.path();
    if (path == null) {
      return null;
    }

    ensureConfiguration();
    if (configuration == null) {
      return null;
    }

    // just use original if we cannot get any sizes
    String size = SIZE_ORIGINAL;
    if (width != Target.SIZE_ORIGINAL && typeSizes != null) {
      final List<Integer> availableSizes = typeSizes.get(movieDbImagePath.type());
      size = getImageSizeToUse(width, availableSizes);
    }

    final String secure_base_url = configuration.images().secure_base_url();
    return secure_base_url + size + path;
  }

  private synchronized void ensureConfiguration() {
    if (configuration != null && typeSizes != null) {
      return;
    }

    try {
      configuration = movieDbService.getConfiguration().blockingGet();
      final Configuration.Images images = configuration.images();

      typeSizes = new EnumMap<>(MovieDbImagePath.Type.class);
      typeSizes.put(MovieDbImagePath.Type.POSTER, extractIntSizes(images.poster_sizes()));
      typeSizes.put(MovieDbImagePath.Type.BACKDROP, extractIntSizes(images.backdrop_sizes()));

    } catch (Throwable t) {
      Timber.w(t, "failed to get movie db configuration");
      typeSizes = null;
      configuration = null;
    }
  }

  private List<Integer> extractIntSizes(List<String> sizes) {
    final ArrayList<Integer> integerSizes = new ArrayList<>();
    for (String size : sizes) {
      if (size.startsWith(IMAGE_WIDTH_PREFIX)) {
        try {
          final int integerSize = Integer.parseInt(size.substring(1));
          integerSizes.add(integerSize);
        } catch (NumberFormatException ignored) {
        }
      }
    }
    Timber.d("get image sizes: %s", integerSizes);
    Collections.sort(integerSizes, Integer::compare);
    return integerSizes;
  }

  /** Returns the image size to use in the format of wXXX. */
  private String getImageSizeToUse(int width, List<Integer> availableSizes) {
    Integer size = null;
    for (Integer availableSize : availableSizes) {
      if (availableSize >= MINIMAL_IMAGE_WIDTH && availableSize >= width) {
        size = availableSize;
        break;
      }
    }

    if (size == null && !availableSizes.isEmpty()) {
      final Integer maxSize = availableSizes.get(availableSizes.size() - 1);
      if (maxSize >= MINIMAL_IMAGE_WIDTH) {
        size = maxSize;
      }
    }

    return size != null ? IMAGE_WIDTH_PREFIX + size : SIZE_ORIGINAL;
  }
}
