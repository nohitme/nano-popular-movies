package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import io.reactivex.annotations.Nullable;

/**
 * This is the same model object as {@link Movie} but with additional {@link ReviewList} and {@link
 * VideoList} using append_to_response
 *
 * <p>This model should be compatible to original {@link Movie} so we can convert to and persist the
 * movie directly
 */
@AutoValue
public abstract class MovieWithDetails {

  @Nullable
  public abstract String poster_path();

  @Nullable
  public abstract String backdrop_path();

  public abstract String overview();

  public abstract String release_date();

  public abstract int id();

  public abstract String original_title();

  public abstract String original_language();

  public abstract String title();

  public abstract double vote_average();

  public abstract int vote_count();

  public abstract double popularity();

  public abstract ReviewList reviews();

  public abstract VideoList videos();

  public static JsonAdapter<MovieWithDetails> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieWithDetails.MoshiJsonAdapter(moshi);
  }
}
