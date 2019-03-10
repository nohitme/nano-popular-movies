package info.ericlin.pupularmovies.details;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import info.ericlin.moviedb.model.MovieVideo;
import info.ericlin.pupularmovies.R;

public class MovieVideoViewHolder extends ItemViewHolder<MovieVideo> {

  public interface OnClickVideoListener {

    void onClickVideo(@NonNull MovieVideo video);
  }

  private final OnClickVideoListener onClickVideoListener;

  @BindView(R.id.details_video_title)
  TextView titleText;

  public MovieVideoViewHolder(
      @NonNull View itemView,
      @NonNull OnClickVideoListener onClickVideoListener) {
    super(itemView);
    this.onClickVideoListener = onClickVideoListener;
  }

  @Override public void bind(MovieVideo item) {
    titleText.setText(item.name());
    itemView.setOnClickListener(v -> onClickVideoListener.onClickVideo(item));
  }

  @Override public void unbind() {
    itemView.setOnClickListener(null);
  }
}
