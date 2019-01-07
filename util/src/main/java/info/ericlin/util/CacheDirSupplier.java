package info.ericlin.util;

import android.app.Application;

import androidx.annotation.NonNull;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class CacheDirSupplier extends BackgroundSupplier<File> {

  private final Application application;

  @Inject
  public CacheDirSupplier(Application application, ExecutorProvider executorProvider) {
    super(executorProvider);
    this.application = application;
  }

  @NonNull
  @Override
  protected File getActual() {
    return application.getCacheDir();
  }
}
