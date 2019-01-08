package info.ericlin.pupularmovies.discovery;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.base.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.ericlin.moviedb.glide.MovieDbImagePath;
import info.ericlin.moviedb.model.Movie;
import info.ericlin.pupularmovies.OnItemClickListener;
import info.ericlin.pupularmovies.R;
import info.ericlin.pupularmovies.dagger.GlideApp;

public class MoviePosterViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.movie_poster_name)
  TextView posterName;

  @BindView(R.id.movie_poster_image)
  ImageView posterImage;

  MoviePosterViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }

  void bindTo(@Nullable Movie movie, OnItemClickListener<Movie> onItemClickListener) {
    // always clear previous image loading
    GlideApp.with(posterImage).clear(posterImage);

    if (movie == null) {
      posterName.setText(null);
      itemView.setOnClickListener(null);
      return;
    }

    itemView.setOnClickListener(v -> onItemClickListener.onItemClick(movie));

    final String name;
    if (Objects.equal(movie.title(), movie.original_title())) {
      name = movie.title();
    } else {
      name = String.format("%s (%s)", movie.original_title(), movie.title());
    }
    posterName.setText(name);

    GlideApp.with(posterImage)
        .load(MovieDbImagePath.poster(movie.poster_path()))
        .placeholder(R.drawable.placeholder_600x900)
        .into(posterImage);
  }
}
