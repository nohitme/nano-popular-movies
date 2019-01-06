package info.ericlin.pupularmovies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import info.ericlin.moviedb.model.Movie;
import info.ericlin.pupularmovies.paging.MoviePosterAdapter;
import info.ericlin.pupularmovies.paging.MoviePosterDataSourceFactory;
import info.ericlin.pupularmovies.paging.MoviePosterDataSourceFactoryFactory;

public class MainActivity extends AppCompatActivity {

  @Inject MoviePosterDataSourceFactoryFactory moviePosterDataSourceFactoryFactory;

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

    // TODO: get proper view model setup...
    final MoviePosterDataSourceFactory sourceFactory =
        moviePosterDataSourceFactoryFactory.create(MoviePosterDataSourceFactory.Category.POPULAR);
    // size does not matter since movie db does not support page size
    final LiveData<PagedList<Movie>> liveData =
        new LivePagedListBuilder<>(sourceFactory, 20).build();
    liveData.observe(this, moviePosterAdapter::submitList);
  }
}
