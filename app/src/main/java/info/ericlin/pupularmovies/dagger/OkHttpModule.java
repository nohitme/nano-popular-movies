package info.ericlin.pupularmovies.dagger;

import android.content.Context;

import com.google.common.collect.Lists;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import info.ericlin.pupularmovies.MovieDbApiKeyInterceptor;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class OkHttpModule {

    @Provides
    OkHttpClient okHttpClient(Context context) {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // cache
        int cacheSize = 64 * 1024 * 1024; // 64 MiB
        Cache cache = new Cache(context.getCacheDir(), cacheSize);
        builder.cache(cache);

        // interceptors
        for (Interceptor interceptor : getInterceptors()) {
            builder.addInterceptor(interceptor);
        }

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return builder
                .addNetworkInterceptor(loggingInterceptor)
                .build();
    }

    private List<Interceptor> getInterceptors() {
        return Lists.newArrayList(
                new MovieDbApiKeyInterceptor()
        );
    }

}
