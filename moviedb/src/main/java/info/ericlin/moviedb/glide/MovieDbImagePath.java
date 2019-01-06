package info.ericlin.moviedb.glide;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;

/** A wrapper class of movie db's image path for Glide to recognize the custom model loader */
@AutoValue
public abstract class MovieDbImagePath {

  public enum Type {
    POSTER,
    BACKDROP
  }

  @NonNull
  public abstract Type type();

  @Nullable
  public abstract String path();

  public static MovieDbImagePath poster(@Nullable String path) {
    return create(Type.POSTER, path);
  }

  public static MovieDbImagePath backdrop(@Nullable String path) {
    return create(Type.BACKDROP, path);
  }

  private static MovieDbImagePath create(@NonNull Type type, @Nullable String path) {
    return new AutoValue_MovieDbImagePath(type, path);
  }
}
