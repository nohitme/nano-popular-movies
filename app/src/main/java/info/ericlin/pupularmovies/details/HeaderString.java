package info.ericlin.pupularmovies.details;

import androidx.annotation.NonNull;
import com.google.auto.value.AutoValue;
import info.ericlin.moviedb.Identifiable;

@AutoValue
public abstract class HeaderString implements Identifiable {

  @NonNull @Override public String identifier() {
    return text();
  }

  public abstract String text();

  public static HeaderString create(String text) {
    return new AutoValue_HeaderString(text);
  }
}
