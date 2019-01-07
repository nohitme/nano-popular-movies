package info.ericlin.pupularmovies.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

/** Factory class for view model class */
public interface ViewModelFactory {

  @NonNull
  ViewModel create(@NonNull Application application);
}
