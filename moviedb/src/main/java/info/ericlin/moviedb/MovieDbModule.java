package info.ericlin.moviedb;

import com.squareup.moshi.Moshi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class MovieDbModule {

    private static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org";

    @Provides
    MovieDbService movieDbService(OkHttpClient okHttpClient) {
        Moshi moshi = new Moshi.Builder().add(MovieDbAdapterFactory.create()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl(MOVIE_DB_BASE_URL)
                .build();

        return retrofit.create(MovieDbService.class);
    }
}
