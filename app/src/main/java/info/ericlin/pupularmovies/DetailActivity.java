package info.ericlin.pupularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import info.ericlin.moviedb.glide.MovieDbImagePath;
import info.ericlin.moviedb.model.Movie;
import info.ericlin.pupularmovies.dagger.GlideApp;
import info.ericlin.pupularmovies.details.MovieDetailsViewModel;
import info.ericlin.pupularmovies.factory.ViewModelProviderFactory;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity {

  private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
  private final CompositeDisposable disposables = new CompositeDisposable();

  @Inject ViewModelProviderFactory viewModelProviderFactory;

  @BindView(R.id.details_toolbar)
  Toolbar toolbar;

  @BindView(R.id.details_backdrop_image)
  ImageView backdropImage;

  @BindView(R.id.details_poster_image)
  ImageView posterImage;

  @BindView(R.id.details_header_frame)
  View headerFrameView;

  @BindView(R.id.details_title)
  TextView titleText;

  @BindView(R.id.details_release_date)
  TextView releaseDateText;

  @BindView(R.id.details_votes)
  TextView voteText;

  @BindView(R.id.details_overview)
  TextView overviewText;

  public static Intent newIntent(@NonNull Context context, int movieId) {
    final Intent intent = new Intent(context, DetailActivity.class);
    intent.putExtra(EXTRA_MOVIE_ID, movieId);
    return intent;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    getWindow()
        .getDecorView()
        .setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    getWindow().setStatusBarColor(Color.TRANSPARENT);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    final int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
    final MovieDetailsViewModel movieDetailsViewModel =
        ViewModelProviders.of(this, viewModelProviderFactory).get(MovieDetailsViewModel.class);

    movieDetailsViewModel.setMovieId(movieId);
    movieDetailsViewModel
        .getMovieLiveData()
        .observe(
            this,
            moviePalette -> {
              onPaletteUpdated(moviePalette.palette());
              onMovieUpdated(moviePalette.movie());
            });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    disposables.clear();
  }

  private void onMovieUpdated(Movie movie) {
    titleText.setText(movie.title());
    releaseDateText.setText(getString(R.string.details_release_date, movie.release_date()));
    voteText.setText(getString(R.string.details_vote, movie.vote_average(), movie.vote_count()));
    overviewText.setText(movie.overview());

    GlideApp.with(this)
        .load(MovieDbImagePath.backdrop(movie.backdrop_path()))
        .thumbnail(0.1f)
        .into(backdropImage);

    GlideApp.with(this)
        .load(MovieDbImagePath.poster(movie.poster_path()))
        .placeholder(R.drawable.placeholder_600x900)
        .into(posterImage);
  }

  private void onPaletteUpdated(Palette palette) {
    Palette.Swatch swatch = palette.getDarkVibrantSwatch();
    if (swatch == null) {
      swatch = palette.getDominantSwatch();
    }

    if (swatch == null) {
      Timber.w("cannot find swatch?!");
      return;
    }

    final int rgb = swatch.getRgb();
    final int titleTextColor = swatch.getTitleTextColor();
    headerFrameView.setBackgroundColor(rgb);
    titleText.setTextColor(titleTextColor);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
