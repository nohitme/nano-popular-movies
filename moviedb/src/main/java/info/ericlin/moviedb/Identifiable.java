package info.ericlin.moviedb;

import androidx.annotation.NonNull;

/**
 * An simple interface to provide an unique string ID so that we can use it in {@code
 * DiffUtil.ItemCallback}
 */
public interface Identifiable {

  @NonNull
  String identifier();
}
