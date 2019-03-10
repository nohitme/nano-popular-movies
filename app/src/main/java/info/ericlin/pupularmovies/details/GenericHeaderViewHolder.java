package info.ericlin.pupularmovies.details;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import butterknife.BindView;
import info.ericlin.pupularmovies.R;

public class GenericHeaderViewHolder extends ItemViewHolder<HeaderString> {

  @BindView(R.id.details_header_text)
  TextView titleText;

  public GenericHeaderViewHolder(@NonNull View itemView) {
    super(itemView);
  }

  @Override public void bind(HeaderString item) {
    titleText.setText(item.text());
  }

  @Override public void unbind() {
    // do nothing
  }
}
