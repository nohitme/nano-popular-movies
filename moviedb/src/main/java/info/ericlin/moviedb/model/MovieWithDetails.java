package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * This is the same model object as {@link Movie} but with additional {@link ReviewList} and {@link
 * VideoList} using append_to_response
 *
 * <p>This model should be compatible to original {@link Movie} so we can convert to and persist the
 * movie directly
 */
@AutoValue
public abstract class MovieWithDetails extends BaseMovie {

  public abstract ReviewList reviews();

  public abstract VideoList videos();

  public static JsonAdapter<MovieWithDetails> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieWithDetails.MoshiJsonAdapter(moshi);
  }
}
