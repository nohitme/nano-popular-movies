package info.ericlin.pupularmovies.database;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import info.ericlin.moviedb.model.Movie;
import java.util.List;
import javax.annotation.Nullable;

/**
 * Room DAO for {@link Movie}
 */
@Dao
public interface MovieDao {

  @Query("SELECT * FROM movie")
  List<MovieEntity> getAllMovies();

  @Query("SELECT COUNT(*) FROM movie WHERE id = :id")
  LiveData<Integer> isMovieSaved(int id);

  @Query("SELECT * FROM movie WHERE id = :id")
  @Nullable
  MovieEntity getMovieById(int id);

  @Insert
  void insertMovie(@NonNull MovieEntity movie);

  @Delete
  void deleteMovie(@NonNull MovieEntity movie);
}
