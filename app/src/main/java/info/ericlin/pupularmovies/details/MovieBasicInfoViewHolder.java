package info.ericlin.pupularmovies.details;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import info.ericlin.moviedb.model.MovieWithDetails;
import info.ericlin.pupularmovies.R;

public class MovieBasicInfoViewHolder extends ItemViewHolder<MovieWithDetails> {

  @BindView(R.id.details_release_date)
  TextView releaseDateText;

  @BindView(R.id.details_votes)
  TextView voteText;

  @BindView(R.id.details_overview)
  TextView overviewText;

  protected MovieBasicInfoViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @Override public void bind(MovieWithDetails movie) {
    Context context = itemView.getContext();

    releaseDateText.setText(context.getString(R.string.details_release_date, movie.release_date()));
    voteText.setText(
        context.getString(R.string.details_vote, movie.vote_average(), movie.vote_count()));
    overviewText.setText(movie.overview());
  }

  @Override public void unbind() {
    // do nothing
  }
}
