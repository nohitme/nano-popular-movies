package info.ericlin.moviedb;

import com.squareup.moshi.Moshi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import info.ericlin.util.ExecutorProvider;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
public class MovieDbModule {

  private static final String MOVIE_DB_BASE_URL = "https://api.themoviedb.org";

  @Provides
  @Singleton
  MovieDbService movieDbService(
      OkHttpClient okHttpClient,
      MovieDbApiConfiguration configuration,
      ExecutorProvider executorProvider) {
    Moshi moshi = new Moshi.Builder().add(MovieDbAdapterFactory.create()).build();

    Retrofit retrofit =
        new Retrofit.Builder()
            .client(okHttpClient)
            .addCallAdapterFactory(
                RxJava2CallAdapterFactory.createWithScheduler(executorProvider.ioScheduler()))
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(configuration.baseUrl())
            .build();

    return retrofit.create(MovieDbService.class);
  }

  @Provides
  MovieDbApiConfiguration provideMovieDbApiConfiguration() {
    return MovieDbApiConfiguration.create(MOVIE_DB_BASE_URL, BuildConfig.MOVIE_DB_API_KEY);
  }
}
