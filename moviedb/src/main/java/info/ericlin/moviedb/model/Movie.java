package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import io.reactivex.annotations.Nullable;

@AutoValue
public abstract class Movie {

  @Nullable
  public abstract String poster_path();

  public abstract String overview();

  public abstract String release_date();

  public abstract int id();

  public abstract String original_title();

  public abstract String original_language();

  public abstract String title();

  public abstract double popularity();

  public static JsonAdapter<Movie> jsonAdapter(Moshi moshi) {
    return new AutoValue_Movie.MoshiJsonAdapter(moshi);
  }
}
