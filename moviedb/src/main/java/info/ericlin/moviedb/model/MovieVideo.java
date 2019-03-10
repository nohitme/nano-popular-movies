package info.ericlin.moviedb.model;

import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import info.ericlin.moviedb.Identifiable;

@AutoValue
public abstract class MovieVideo implements Identifiable {

  @NonNull @Override public String identifier() {
    return id();
  }

  public abstract String id();

  public abstract String key();

  public abstract String name();

  public abstract String site();

  public abstract String type();

  public static JsonAdapter<MovieVideo> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieVideo.MoshiJsonAdapter(moshi);
  }
}
