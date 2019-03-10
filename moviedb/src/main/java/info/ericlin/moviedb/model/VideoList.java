package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import java.util.List;

@AutoValue
public abstract class VideoList {

  public abstract List<MovieVideo> results();

  public static JsonAdapter<VideoList> jsonAdapter(Moshi moshi) {
    return new AutoValue_VideoList.MoshiJsonAdapter(moshi);
  }
}
