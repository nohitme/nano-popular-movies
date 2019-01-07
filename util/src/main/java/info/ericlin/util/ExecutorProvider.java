package info.ericlin.util;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;

import io.reactivex.Scheduler;

public interface ExecutorProvider {

  /** Returns executor for I/O operations */
  @NonNull
  Executor ioExecutor();

  @NonNull
  Scheduler ioScheduler();
}
