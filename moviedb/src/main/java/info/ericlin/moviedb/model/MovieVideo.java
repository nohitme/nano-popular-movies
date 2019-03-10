package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class MovieVideo {

  public abstract String key();

  public abstract String name();

  public abstract String site();

  public abstract String type();

  public static JsonAdapter<MovieVideo> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieVideo.MoshiJsonAdapter(moshi);
  }
}
