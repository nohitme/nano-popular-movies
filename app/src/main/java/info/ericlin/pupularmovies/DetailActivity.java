package info.ericlin.pupularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import dagger.android.AndroidInjection;

public class DetailActivity extends AppCompatActivity {

  public static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";

  public static Intent newIntent(@NonNull Context context, int movieId) {
    final Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(EXTRA_MOVIE_ID, movieId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
  }
}
