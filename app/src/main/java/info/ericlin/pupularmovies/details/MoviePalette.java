package info.ericlin.pupularmovies.details;

import androidx.palette.graphics.Palette;

import com.google.auto.value.AutoValue;

import info.ericlin.moviedb.model.Movie;

@AutoValue
public abstract class MoviePalette {

  public abstract Movie movie();

  public abstract Palette palette();

  public static MoviePalette create(Movie movie, Palette palette) {
    return new AutoValue_MoviePalette(movie, palette);
  }
}
