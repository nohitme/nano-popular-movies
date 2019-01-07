package info.ericlin.util;

import androidx.annotation.NonNull;

import com.google.common.base.Supplier;

import io.reactivex.Single;
import timber.log.Timber;

/** A specialized {@link Supplier} that does lazy initialization always at background thread */
public abstract class BackgroundSupplier<T> implements Supplier<T> {

  private final ExecutorProvider executorProvider;

  BackgroundSupplier(ExecutorProvider executorProvider) {
    this.executorProvider = executorProvider;
  }

  @Override
  public final T get() {
    try {
      return Single.fromCallable(this::getActual)
          .subscribeOn(executorProvider.ioScheduler())
          .retry()
          .blockingGet();
    } catch (Exception e) {
      Timber.w(e, "failed to get actual value");
      return getActual();
    }
  }

  @NonNull
  protected abstract T getActual();
}
