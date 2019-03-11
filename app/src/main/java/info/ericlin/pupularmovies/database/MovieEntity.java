package info.ericlin.pupularmovies.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import io.reactivex.annotations.Nullable;

@Entity(tableName = "movie")
public class MovieEntity {

  @PrimaryKey
  public int id;

  @Nullable
  public String poster_path;

  @Nullable
  public String backdrop_path;

  public String overview;

  public String release_date;

  public String original_title;

  public String original_language;

  public String title;

  public double vote_average;

  public int vote_count;

  public double popularity;
}
