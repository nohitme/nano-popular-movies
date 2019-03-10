package info.ericlin.moviedb.model;

import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import info.ericlin.moviedb.Identifiable;

@AutoValue
public abstract class MovieReview implements Identifiable {

  @NonNull @Override public String identifier() {
    return id();
  }

  public abstract String id();

  public abstract String author();

  public abstract String content();

  public abstract String url();

  public static JsonAdapter<MovieReview> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieReview.MoshiJsonAdapter(moshi);
  }
}
