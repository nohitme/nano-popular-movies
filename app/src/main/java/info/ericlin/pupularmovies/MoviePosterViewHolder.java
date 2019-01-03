package info.ericlin.pupularmovies;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviePosterViewHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.movie_poster_name)
  TextView posterName;

  @BindView(R.id.movie_poster_image)
  ImageView posterImage;

  public MoviePosterViewHolder(@NonNull View itemView) {
    super(itemView);
    ButterKnife.bind(this, itemView);
  }
}
