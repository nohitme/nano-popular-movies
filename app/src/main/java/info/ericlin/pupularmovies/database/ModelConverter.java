package info.ericlin.pupularmovies.database;

import androidx.annotation.NonNull;
import info.ericlin.moviedb.model.BaseMovie;
import info.ericlin.moviedb.model.Movie;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Convert model object to other. Used for converting models from database to json model.
 */
@Singleton
public class ModelConverter {

  @Inject
  public ModelConverter() {
    // for dagger
  }

  @NonNull
  public Movie convertToMovie(@NonNull MovieEntity entity) {
    return Movie.builder()
        .poster_path(entity.poster_path)
        .backdrop_path(entity.backdrop_path)
        .overview(entity.overview)
        .release_date(entity.release_date)
        .id(entity.id)
        .original_title(entity.original_title)
        .original_language(entity.original_language)
        .title(entity.title)
        .vote_average(entity.vote_average)
        .vote_count(entity.vote_count)
        .popularity(entity.popularity)
        .build();
  }

  @NonNull
  public MovieEntity convertToEntity(@NonNull BaseMovie movie) {
    MovieEntity entity = new MovieEntity();
    entity.poster_path = movie.poster_path();
    entity.backdrop_path = movie.backdrop_path();
    entity.overview = movie.overview();
    entity.release_date = movie.release_date();
    entity.id = movie.id();
    entity.original_title = movie.original_title();
    entity.original_language = movie.original_language();
    entity.title = movie.title();
    entity.vote_average = movie.vote_average();
    entity.vote_count = movie.vote_count();
    entity.popularity = movie.popularity();

    return entity;
  }
}
