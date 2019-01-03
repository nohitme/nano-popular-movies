package info.ericlin.moviedb;

import info.ericlin.moviedb.model.Configuration;
import info.ericlin.moviedb.model.MovieList;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieDbService {

  @GET("/3/configuration")
  Single<Configuration> getConfiguration();

  @GET("/3/movie/popular")
  Single<MovieList> getPopularMovies();

  @GET("/3/movie/top_rated")
  Single<MovieList> getTopRatedMovies();

  @GET("/3/movie/latest")
  Single<MovieList> getLatestMovies();

  @GET("/3/movie/now_playing")
  Single<MovieList> getNowPlayingMovies();

  @GET("/3/movie/upcoming")
  Single<MovieList> getUpcomingMovies();
}
