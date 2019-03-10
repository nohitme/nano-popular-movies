package info.ericlin.pupularmovies.details;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.palette.graphics.Palette;
import butterknife.BindView;
import info.ericlin.moviedb.glide.MovieDbImagePath;
import info.ericlin.moviedb.model.MovieWithDetails;
import info.ericlin.pupularmovies.R;
import info.ericlin.pupularmovies.dagger.GlideApp;

public class MovieHeaderImageViewHolder extends ItemViewHolder<MovieSwatch> {

  @BindView(R.id.details_backdrop_image)
  ImageView backdropImage;

  @BindView(R.id.details_poster_image)
  ImageView posterImage;

  @BindView(R.id.details_header_frame)
  View headerFrameView;

  @BindView(R.id.details_title)
  TextView titleText;

  public MovieHeaderImageViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @Override public void bind(MovieSwatch item) {
    final MovieWithDetails movie = item.movie();

    titleText.setText(movie.title());

    GlideApp.with(itemView)
        .load(MovieDbImagePath.backdrop(movie.backdrop_path()))
        .thumbnail(0.1f)
        .into(backdropImage);

    GlideApp.with(itemView)
        .load(MovieDbImagePath.poster(movie.poster_path()))
        .placeholder(R.drawable.placeholder_600x900)
        .into(posterImage);

    final Palette.Swatch swatch = item.swatch();
    if (swatch != null) {
      final int rgb = swatch.getRgb();
      final int titleTextColor = swatch.getTitleTextColor();
      headerFrameView.setBackgroundColor(rgb);
      titleText.setTextColor(titleTextColor);
    }
  }

  @Override public void unbind() {
    GlideApp.with(itemView).clear(backdropImage);
    GlideApp.with(itemView).clear(posterImage);
  }
}
