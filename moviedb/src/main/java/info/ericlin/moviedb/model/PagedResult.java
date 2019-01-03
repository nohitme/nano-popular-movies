package info.ericlin.moviedb.model;

/**
 * Movie DB uses shared properties for most of their pagination response. Let's create one abstract
 * class to mimic that!
 *
 * <p>>see: https://developers.themoviedb.org/3/movies/get-popular-movies
 */
public abstract class PagedResult {

  public abstract int page();

  public abstract int total_results();

  public abstract int total_pages();
}
