package info.ericlin.pupularmovies.details;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import info.ericlin.moviedb.model.MovieReview;
import info.ericlin.pupularmovies.R;

public class MovieReviewViewHolder extends ItemViewHolder<MovieReview> {

  @BindView(R.id.details_review_author)
  TextView authorText;

  @BindView(R.id.details_review_comment)
  TextView commentText;

  protected MovieReviewViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @Override public void bind(MovieReview item) {
    Context context = itemView.getContext();
    authorText.setText(context.getString(R.string.details_review_author, item.author()));
    commentText.setText(item.content());
  }

  @Override public void unbind() {
    // do nothing
  }
}
