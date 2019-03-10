package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Movie extends BaseMovie {

  public static JsonAdapter<Movie> jsonAdapter(Moshi moshi) {
    return new AutoValue_Movie.MoshiJsonAdapter(moshi);
  }
}
