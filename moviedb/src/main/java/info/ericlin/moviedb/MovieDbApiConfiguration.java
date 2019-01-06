package info.ericlin.moviedb;

import com.google.auto.value.AutoValue;

import io.reactivex.annotations.NonNull;

/** Configurations for the movie db api service */
@AutoValue
public abstract class MovieDbApiConfiguration {

  @NonNull
  public abstract String baseUrl();

  @NonNull
  public abstract String apiKey();

  public static MovieDbApiConfiguration create(@NonNull String baseUrl, @NonNull String apiKey) {
    return new AutoValue_MovieDbApiConfiguration(baseUrl, apiKey);
  }
}
