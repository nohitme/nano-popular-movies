package info.ericlin.pupularmovies.paging;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.google.common.base.Objects;

import info.ericlin.moviedb.glide.MovieDbImagePath;
import info.ericlin.moviedb.model.Movie;
import info.ericlin.pupularmovies.R;
import info.ericlin.pupularmovies.dagger.GlideApp;

public class MoviePosterAdapter extends PagedListAdapter<Movie, MoviePosterViewHolder> {

  private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
      new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
          return oldItem.id() == newItem.id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
          return Objects.equal(oldItem, newItem);
        }
      };

  public MoviePosterAdapter() {
    super(DIFF_CALLBACK);
  }

  @NonNull
  @Override
  public MoviePosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    final View itemView = inflater.inflate(R.layout.item_movie_poster, parent, false);
    return new MoviePosterViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull MoviePosterViewHolder holder, int position) {
    final Movie movie = getItem(position);
    if (movie == null) {
      // TODO: fine.. let's do a "bind" method...
      GlideApp.with(holder.posterImage).clear(holder.posterImage);
      holder.posterName.setText(null);
      return;
    }

    final String name;
    if (Objects.equal(movie.title(), movie.original_title())) {
      name = movie.title();
    } else {
      name = String.format("%s (%s)", movie.original_title(), movie.title());
    }

    holder.posterName.setText(name);

    GlideApp.with(holder.posterImage).clear(holder.posterImage);

    GlideApp.with(holder.posterImage)
        .load(MovieDbImagePath.poster(movie.poster_path()))
        .placeholder(R.drawable.placeholder_600x900)
        .into(holder.posterImage);
  }
}
