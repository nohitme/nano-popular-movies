package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

import io.reactivex.annotations.NonNull;

@AutoValue
public abstract class Configuration {

  @NonNull
  public abstract Images images();

  public static JsonAdapter<Configuration> jsonAdapter(Moshi moshi) {
    return new AutoValue_Configuration.MoshiJsonAdapter(moshi);
  }

  @AutoValue
  public abstract static class Images {

    @NonNull
    public abstract String secure_base_url();

    @NonNull
    public abstract List<String> poster_sizes();

    public static JsonAdapter<Images> jsonAdapter(Moshi moshi) {
      return new AutoValue_Configuration_Images.MoshiJsonAdapter(moshi);
    }
  }
}
