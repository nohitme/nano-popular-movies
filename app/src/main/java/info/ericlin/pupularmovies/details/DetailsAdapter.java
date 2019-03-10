package info.ericlin.pupularmovies.details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import com.google.common.base.Objects;
import info.ericlin.moviedb.Identifiable;
import info.ericlin.moviedb.model.MovieWithDetails;
import info.ericlin.pupularmovies.R;

public class DetailsAdapter extends ListAdapter<Identifiable, ItemViewHolder> {

  private static final DiffUtil.ItemCallback<Identifiable> ITEM_CALLBACK =
      new DiffUtil.ItemCallback<Identifiable>() {
        @Override public boolean areItemsTheSame(@NonNull Identifiable oldItem,
            @NonNull Identifiable newItem) {
          return Objects.equal(oldItem.identifier(), newItem.identifier());
        }

        @Override public boolean areContentsTheSame(@NonNull Identifiable oldItem,
            @NonNull Identifiable newItem) {
          return Objects.equal(oldItem, newItem);
        }
      };

  public DetailsAdapter() {
    super(ITEM_CALLBACK);
  }

  @NonNull @Override
  public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View itemView = inflater.inflate(viewType, parent, false);
    if (viewType == R.layout.item_details_image_row) {
      return new MovieHeaderImageViewHolder(itemView);
    } else if (viewType == R.layout.item_details_basic_info_row) {
      return new MovieBasicInfoViewHolder(itemView);
    }

    throw new IllegalArgumentException("unknown view type: " + viewType);
  }

  @SuppressWarnings("unchecked")
  @Override public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
    Identifiable item = getItem(position);
    holder.bind(item);
  }

  @Override public void onViewRecycled(@NonNull ItemViewHolder holder) {
    super.onViewRecycled(holder);
    holder.unbind();
  }

  @Override public int getItemViewType(int position) {
    Identifiable item = getItem(position);
    if (item instanceof MovieSwatch) {
      return R.layout.item_details_image_row;
    } else if (item instanceof MovieWithDetails) {
      return R.layout.item_details_basic_info_row;
    }

    throw new IllegalStateException("unknown type: " + item.getClass().getName());
  }
}
