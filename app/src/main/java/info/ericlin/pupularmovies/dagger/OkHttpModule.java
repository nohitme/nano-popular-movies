package info.ericlin.pupularmovies.dagger;

import android.content.Context;
import android.net.TrafficStats;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;

import javax.net.SocketFactory;

import dagger.Module;
import dagger.Provides;
import info.ericlin.pupularmovies.MovieDbApiKeyInterceptor;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
class OkHttpModule {

  @Provides
  OkHttpClient okHttpClient(Context context) {

    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    // cache
    int cacheSize = 64 * 1024 * 1024; // 64 MiB
    Cache cache = new Cache(context.getCacheDir(), cacheSize);
    builder.cache(cache);

    // just to get rid of strict mode warning
    // reference:
    builder.socketFactory(new TagSocketFactory());

    // interceptors
    for (Interceptor interceptor : getInterceptors()) {
      builder.addInterceptor(interceptor);
    }

    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

    return builder.addNetworkInterceptor(loggingInterceptor).build();
  }

  private List<Interceptor> getInterceptors() {
    return Lists.newArrayList(new MovieDbApiKeyInterceptor());
  }

  /**
   * A socket factory delegates actual call to {@link SocketFactory#getDefault()} and sets the
   * thread stats to get rid of strict mode warnings.
   */
  private static class TagSocketFactory extends SocketFactory {

    // by default okhttp only use parameter-less method
    // see:
    // https://square.github.io/okhttp/3.x/okhttp/okhttp3/OkHttpClient.Builder.html#socketFactory-javax.net.SocketFactory-
    static final String DEFAULT_ERROR_MESSAGE = "okhttp should not call this method";

    @Override
    public Socket createSocket() throws IOException {
      Socket socket = SocketFactory.getDefault().createSocket();
      TrafficStats.setThreadStatsTag(TagSocketFactory.class.hashCode());
      return socket;
    }

    @Override
    public Socket createSocket(String host, int port) {
      throw new UnsupportedOperationException(DEFAULT_ERROR_MESSAGE);
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) {
      throw new UnsupportedOperationException(DEFAULT_ERROR_MESSAGE);
    }

    @Override
    public Socket createSocket(InetAddress host, int port) {
      throw new UnsupportedOperationException(DEFAULT_ERROR_MESSAGE);
    }

    @Override
    public Socket createSocket(
        InetAddress address, int port, InetAddress localAddress, int localPort) {
      throw new UnsupportedOperationException(DEFAULT_ERROR_MESSAGE);
    }
  }
}
