package info.ericlin.pupularmovies.details;

import androidx.palette.graphics.Palette;

import com.google.auto.value.AutoValue;

import info.ericlin.moviedb.model.Movie;
import info.ericlin.moviedb.model.MovieWithDetails;

@AutoValue
public abstract class MoviePalette {

  public abstract MovieWithDetails movie();

  public abstract Palette palette();

  public static MoviePalette create(MovieWithDetails movie, Palette palette) {
    return new AutoValue_MoviePalette(movie, palette);
  }
}
