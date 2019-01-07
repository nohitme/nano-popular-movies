package info.ericlin.moviedb;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Singleton
public class MovieDbApiKeyInterceptor implements Interceptor {

  private final MovieDbApiConfiguration configuration;
  private final HttpUrl targetHttpUrl;

  @Inject
  MovieDbApiKeyInterceptor(MovieDbApiConfiguration configuration) {
    targetHttpUrl = HttpUrl.parse(configuration.baseUrl());
    this.configuration = configuration;
  }

  @NotNull
  @Override
  public Response intercept(@NotNull Chain chain) throws IOException {

    Request request = chain.request();

    // make sure we only add api key to the right service
    if (request.url().host().equals(targetHttpUrl.host())) {
      HttpUrl httpUrl =
          request.url().newBuilder().addQueryParameter("api_key", configuration.apiKey()).build();

      request = request.newBuilder().url(httpUrl).build();
    }

    return chain.proceed(request);
  }
}
