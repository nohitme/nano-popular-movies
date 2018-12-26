package info.ericlin.pupularmovies;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDbApiKeyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        // TODO: inject api key wrapper
        Request request = chain.request();
        HttpUrl httpUrl = request.url()
                .newBuilder()
                .addQueryParameter("api_key", BuildConfig.MOVIE_DB_API_KEY)
                .build();

        Request newRequest = request.newBuilder().url(httpUrl).build();

        return chain.proceed(newRequest);
    }
}
