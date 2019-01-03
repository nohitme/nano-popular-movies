package info.ericlin.pupularmovies;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import info.ericlin.moviedb.MovieDbService;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

  @Inject MovieDbService movieDbService;

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

    movieDbService
        .getTopRatedMovies()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(movieList -> moviePosterAdapter.submitList(movieList.results()));
  }
}
