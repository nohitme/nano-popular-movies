package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.List;

@AutoValue
public abstract class MovieList extends PagedResult {

  public abstract List<Movie> results();

  public static JsonAdapter<MovieList> jsonAdapter(Moshi moshi) {
    return new AutoValue_MovieList.MoshiJsonAdapter(moshi);
  }
}
