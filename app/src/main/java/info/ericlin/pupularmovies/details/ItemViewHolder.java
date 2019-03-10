package info.ericlin.pupularmovies.details;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;

/**
 * A simple abstraction of a view holder that can bind to a specific type of item
 *
 * @param <T> data item type
 */
public abstract class ItemViewHolder<T> extends RecyclerView.ViewHolder {

  protected ItemViewHolder(@NonNull View itemView) {
    super(itemView);
    // we don't need to unbind here
    ButterKnife.bind(this, itemView);
  }

  public abstract void bind(T item);

  public abstract void unbind();
}
