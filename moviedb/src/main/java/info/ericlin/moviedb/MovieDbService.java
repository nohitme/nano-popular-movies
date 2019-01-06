package info.ericlin.moviedb;

import androidx.annotation.Nullable;

import info.ericlin.moviedb.model.Configuration;
import info.ericlin.moviedb.model.MovieList;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieDbService {

  @GET("/3/configuration")
  Single<Configuration> getConfiguration();

  @GET("/3/movie/popular")
  Single<MovieList> getPopularMovies(@Query("page") @Nullable Integer page);

  @GET("/3/movie/top_rated")
  Single<MovieList> getTopRatedMovies(@Query("page") @Nullable Integer page);

  @GET("/3/movie/latest")
  Single<MovieList> getLatestMovies(@Query("page") @Nullable Integer page);

  @GET("/3/movie/now_playing")
  Single<MovieList> getNowPlayingMovies(@Query("page") @Nullable Integer page);

  @GET("/3/movie/upcoming")
  Single<MovieList> getUpcomingMovies(@Query("page") @Nullable Integer page);
}
