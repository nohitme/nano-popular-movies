package info.ericlin.moviedb.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

@AutoValue
public abstract class Movie extends BaseMovie {

  public static Builder builder() {
    return new AutoValue_Movie.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {

    public abstract Builder poster_path(String poster_path);

    public abstract Builder backdrop_path(String backdrop_path);

    public abstract Builder overview(String overview);

    public abstract Builder release_date(String release_date);

    public abstract Builder id(int id);

    public abstract Builder original_title(String original_title);

    public abstract Builder original_language(String original_language);

    public abstract Builder title(String title);

    public abstract Builder vote_average(double vote_average);

    public abstract Builder vote_count(int vote_count);

    public abstract Builder popularity(double popularity);

    public abstract Movie build();
  }

  public static JsonAdapter<Movie> jsonAdapter(Moshi moshi) {
    return new AutoValue_Movie.MoshiJsonAdapter(moshi);
  }
}
