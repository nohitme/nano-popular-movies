package info.ericlin.pupularmovies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import dagger.android.AndroidInjection;
import info.ericlin.moviedb.model.MovieVideo;
import info.ericlin.pupularmovies.details.DetailsAdapter;
import info.ericlin.pupularmovies.details.MovieDetailsViewModel;
import info.ericlin.pupularmovies.factory.ViewModelProviderFactory;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity implements DetailsAdapter.DetailsCallback {

  private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
  private static final String YOUTUBE_WEB_URL_PREFIX = "https://www.youtube.com/watch?v=";
  private static final String YOUTUBE_VND_SCHEME = "vnd.youtube:";

  private final CompositeDisposable disposables = new CompositeDisposable();

  @Inject ViewModelProviderFactory viewModelProviderFactory;

  @BindView(R.id.details_toolbar)
  Toolbar toolbar;

  @BindView(R.id.details_recycler_view)
  RecyclerView recyclerView;

  @BindView(R.id.details_fab)
  FloatingActionButton floatingActionButton;

  private final DetailsAdapter detailsAdapter = new DetailsAdapter(this);

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

    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setAdapter(detailsAdapter);
    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
      @Override public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        if (dy > 0 && floatingActionButton.getVisibility() == View.VISIBLE) {
          floatingActionButton.hide();
        } else if (dy < 0 && floatingActionButton.getVisibility() != View.VISIBLE) {
          floatingActionButton.show();
        }
      }
    });

    final int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
    final MovieDetailsViewModel movieDetailsViewModel =
        ViewModelProviders.of(this, viewModelProviderFactory).get(MovieDetailsViewModel.class);

    movieDetailsViewModel.setMovieId(movieId);
    movieDetailsViewModel
        .getDetailsListLiveData()
        .observe(this, list -> {
          // only set FAB visible when we have movie loaded
          floatingActionButton.setVisibility(View.VISIBLE);
          detailsAdapter.submitList(list);
        });

    movieDetailsViewModel.isFavoriteLiveData().observe(this, saved -> {
      floatingActionButton.setActivated(saved);
      if (saved) {
        Toast.makeText(this, R.string.details_movie_saved, Toast.LENGTH_SHORT).show();
      }
    });

    floatingActionButton.setOnClickListener(v -> {
      movieDetailsViewModel.toggleFavorite();
    });
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    disposables.clear();
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) {
      onBackPressed();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override public void onClickVideo(@NonNull MovieVideo video) {
    String videoKey = video.key();

    // reference: https://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent
    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_VND_SCHEME + videoKey));
    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_WEB_URL_PREFIX + videoKey));
    try {
      startActivity(appIntent);
    } catch (ActivityNotFoundException ex) {
      Toast.makeText(this, getString(R.string.details_launch_by_web), Toast.LENGTH_SHORT).show();
      startActivity(webIntent);
    }
  }
}
