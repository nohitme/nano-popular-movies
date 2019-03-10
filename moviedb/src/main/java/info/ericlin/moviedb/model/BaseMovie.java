package info.ericlin.moviedb.model;

import androidx.annotation.NonNull;
import info.ericlin.moviedb.Identifiable;
import io.reactivex.annotations.Nullable;

/**
 * The shared model between the {@link Movie} and {@link MovieWithDetails}. This model ensures two
 * models has the identical fields.
 */
public abstract class BaseMovie implements Identifiable {

  @NonNull @Override public String identifier() {
    return String.valueOf(id());
  }

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
}
