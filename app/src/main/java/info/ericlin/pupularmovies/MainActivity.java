package info.ericlin.pupularmovies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import info.ericlin.pupularmovies.discovery.MovieCategory;
import info.ericlin.pupularmovies.discovery.MoviePosterAdapter;
import info.ericlin.pupularmovies.discovery.MoviePosterViewModel;
import info.ericlin.pupularmovies.factory.ViewModelProviderFactory;

public class MainActivity extends AppCompatActivity {

  @Inject ViewModelProviderFactory viewModelProviderFactory;

  @BindView(R.id.main_recyclerview)
  RecyclerView recyclerView;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  private MoviePosterAdapter moviePosterAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    AndroidInjection.inject(this);
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    toolbar.setTitle("Movies!");

    final int spanCount = getResources().getInteger(R.integer.movie_poster_grid_span);
    final StaggeredGridLayoutManager layoutManager =
        new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);

    recyclerView.setLayoutManager(layoutManager);
    moviePosterAdapter = new MoviePosterAdapter();
    recyclerView.setAdapter(moviePosterAdapter);

    final MoviePosterViewModel viewModel =
        ViewModelProviders.of(this, viewModelProviderFactory).get(MoviePosterViewModel.class);
    viewModel.init(MovieCategory.POPULAR);
    viewModel.getMovieLists().observe(this, moviePosterAdapter::submitList);
  }
}
