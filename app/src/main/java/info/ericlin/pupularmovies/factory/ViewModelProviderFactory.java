package info.ericlin.pupularmovies.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.google.common.base.Preconditions.checkNotNull;

@Singleton
public class ViewModelProviderFactory implements ViewModelProvider.Factory {

  private final Application application;
  private final Map<Class<?>, ViewModelFactory> factories;

  @Inject
  public ViewModelProviderFactory(
      Application application, Map<Class<?>, ViewModelFactory> factories) {
    this.application = application;
    this.factories = factories;
  }

  @NonNull
  @Override
  public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    final ViewModelFactory viewModelFactory = factories.get(modelClass);
    checkNotNull(viewModelFactory, "missing view model factory for class %s", modelClass);
    return (T) viewModelFactory.create(application);
  }
}
