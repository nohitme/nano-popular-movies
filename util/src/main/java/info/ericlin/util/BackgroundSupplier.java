package info.ericlin.util;

import androidx.annotation.NonNull;

import com.google.common.base.Supplier;

import io.reactivex.Single;

/** A specialized {@link Supplier} that does lazy initialization always at background thread */
public abstract class BackgroundSupplier<T> implements Supplier<T> {

  private final ExecutorProvider executorProvider;

  BackgroundSupplier(ExecutorProvider executorProvider) {
    this.executorProvider = executorProvider;
  }

  @Override
  public final T get() {
    return Single.fromCallable(this::getActual)
        .subscribeOn(executorProvider.ioScheduler())
        .retry()
        .blockingGet();
  }

  @NonNull
  protected abstract T getActual();
}
