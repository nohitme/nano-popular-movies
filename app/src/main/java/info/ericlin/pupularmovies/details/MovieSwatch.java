package info.ericlin.pupularmovies.details;

import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;
import com.google.auto.value.AutoValue;
import info.ericlin.moviedb.Identifiable;
import info.ericlin.moviedb.model.MovieWithDetails;
import javax.annotation.Nullable;

@AutoValue
public abstract class MovieSwatch implements Identifiable {

  @NonNull @Override public String identifier() {
    return String.valueOf(movie().id());
  }

  public abstract MovieWithDetails movie();

  @Nullable
  public abstract Palette.Swatch swatch();

  public static MovieSwatch create(MovieWithDetails movie, Palette.Swatch swatch) {
    return new AutoValue_MovieSwatch(movie, swatch);
  }
}
