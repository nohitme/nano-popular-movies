package info.ericlin.moviedb;

import info.ericlin.moviedb.model.Configuration;
import io.reactivex.Single;
import retrofit2.http.GET;

public interface MovieDbService {

    @GET("configuration")
    Single<Configuration> getConfiguration();

}
