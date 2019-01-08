package info.ericlin.pupularmovies.discovery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;

import com.google.common.base.Objects;

import info.ericlin.moviedb.model.Movie;
import info.ericlin.pupularmovies.OnItemClickListener;
import info.ericlin.pupularmovies.R;

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
  @NonNull private final OnItemClickListener<Movie> onItemClickListener;

  MoviePosterAdapter(@NonNull OnItemClickListener<Movie> onItemClickListener) {
    super(DIFF_CALLBACK);
    this.onItemClickListener = onItemClickListener;
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
    holder.bindTo(movie, onItemClickListener);
  }
}
