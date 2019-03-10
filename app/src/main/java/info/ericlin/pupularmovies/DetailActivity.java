package info.ericlin.pupularmovies;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import info.ericlin.pupularmovies.details.DetailsAdapter;
import info.ericlin.pupularmovies.details.MovieDetailsViewModel;
import info.ericlin.pupularmovies.factory.ViewModelProviderFactory;
import io.reactivex.disposables.CompositeDisposable;
import javax.inject.Inject;

public class DetailActivity extends AppCompatActivity {

  private static final String EXTRA_MOVIE_ID = "EXTRA_MOVIE_ID";
  private final CompositeDisposable disposables = new CompositeDisposable();

  @Inject ViewModelProviderFactory viewModelProviderFactory;

  @BindView(R.id.details_toolbar)
  Toolbar toolbar;

  @BindView(R.id.details_recycler_view)
  RecyclerView recyclerView;

  private final DetailsAdapter detailsAdapter = new DetailsAdapter();

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

    final int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, 0);
    final MovieDetailsViewModel movieDetailsViewModel =
        ViewModelProviders.of(this, viewModelProviderFactory).get(MovieDetailsViewModel.class);

    movieDetailsViewModel.setMovieId(movieId);
    movieDetailsViewModel
        .getDetailsListLiveData()
        .observe(this, detailsAdapter::submitList);
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
}
