package info.ericlin.pupularmovies;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.moshi.Moshi;

import info.ericlin.moviedb.MovieDbAdapterFactory;
import info.ericlin.moviedb.MovieDbService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: use dagger for all these...
        Moshi moshi = new Moshi.Builder().add(MovieDbAdapterFactory.create()).build();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new MovieDbApiKeyInterceptor())
                .addNetworkInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .baseUrl("https://api.themoviedb.org/3/")
                .build();

        MovieDbService movieDbService = retrofit.create(MovieDbService.class);
        movieDbService.getConfiguration().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(c -> {
                    Toast.makeText(this, c.toString(), Toast.LENGTH_LONG).show();
                });
    }
}
