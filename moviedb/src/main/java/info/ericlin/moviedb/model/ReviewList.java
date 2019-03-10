package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.util.List;

@AutoValue
public abstract class ReviewList extends PagedResult {

  public abstract List<MovieReview> results();

  public static JsonAdapter<ReviewList> jsonAdapter(Moshi moshi) {
    return new AutoValue_ReviewList.MoshiJsonAdapter(moshi);
  }
}
