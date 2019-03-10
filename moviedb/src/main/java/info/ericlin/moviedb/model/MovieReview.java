package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class MovieReview {

  public abstract String author();

  public abstract String content();

  public abstract String url();

  public static JsonAdapter<MovieReview> jsonAdapter(Moshi moshi) {
      return new AutoValue_MovieReview.MoshiJsonAdapter(moshi);
  }
}
