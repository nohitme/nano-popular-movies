package info.ericlin.pupularmovies.discovery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.common.base.Preconditions;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import info.ericlin.moviedb.model.Movie;
import info.ericlin.pupularmovies.DetailActivity;
import info.ericlin.pupularmovies.R;
import info.ericlin.pupularmovies.factory.ViewModelProviderFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Displays a list of movie based on {@link MovieCategory}
 */
public class MoviePosterFragment extends Fragment {

  private static final String ARGS_CATEGORY = "ARGS_CATEGORY";
  private MovieCategory category;

  @Inject
  ViewModelProviderFactory viewModelProviderFactory;

  @BindView(R.id.main_recyclerview)
  RecyclerView recyclerView;

  private Unbinder unbinder;

  public static MoviePosterFragment newInstance(@NonNull MovieCategory category) {
    final Bundle args = new Bundle();
    args.putSerializable(ARGS_CATEGORY, category);
    final MoviePosterFragment fragment = new MoviePosterFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    AndroidSupportInjection.inject(this);
    super.onAttach(context);
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    checkNotNull(getArguments());
    this.category = (MovieCategory) getArguments().getSerializable(ARGS_CATEGORY);
    checkNotNull(this.category);
  }

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_main, container, false);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    unbinder = ButterKnife.bind(this, view);

    final int spanCount = getResources().getInteger(R.integer.movie_poster_grid_span);
    final StaggeredGridLayoutManager layoutManager =
        new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);

    recyclerView.setLayoutManager(layoutManager);
    MoviePosterAdapter moviePosterAdapter = new MoviePosterAdapter(this::onMovieClicked);
    recyclerView.setAdapter(moviePosterAdapter);

    final MoviePosterViewModel viewModel =
        ViewModelProviders.of(this, viewModelProviderFactory).get(MoviePosterViewModel.class);
    viewModel.init(category);
    viewModel.getMovieLists().observe(this, moviePosterAdapter::submitList);
  }

  private void onMovieClicked(@NonNull Movie movie, @NonNull MoviePosterViewHolder viewHolder) {
    final Intent intent = DetailActivity.newIntent(requireContext(), movie.id());
    startActivity(intent);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    if (unbinder != null) {
      unbinder.unbind();
    }
  }
}
