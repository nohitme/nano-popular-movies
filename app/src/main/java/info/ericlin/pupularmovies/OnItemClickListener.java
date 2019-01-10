package info.ericlin.pupularmovies;

import androidx.annotation.NonNull;

/** An item click listener for recycler view items.. */
public interface OnItemClickListener<T, VH> {

  void onItemClick(@NonNull T item, VH viewHolder);
}
