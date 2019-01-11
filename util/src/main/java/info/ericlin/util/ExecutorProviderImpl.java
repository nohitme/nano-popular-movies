package info.ericlin.util;

import androidx.annotation.NonNull;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.netflix.concurrency.limits.executors.BlockingAdaptiveExecutor;
import com.netflix.concurrency.limits.limiter.SimpleLimiter;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class ExecutorProviderImpl implements ExecutorProvider {

  private final Executor ioExecutor;
  private final Scheduler ioScheduler;

  @Inject
  ExecutorProviderImpl() {
    ioExecutor = newIoExecutor();
    ioScheduler = Schedulers.from(ioExecutor);
  }

  private Executor newIoExecutor() {
    if (BuildConfig.VERSION_CODE >= 24) {
      final ThreadFactory threadFactory =
          new ThreadFactoryBuilder().setNameFormat("adaptive-limiter-%d").build();
      final ExecutorService executorService = Executors.newCachedThreadPool(threadFactory);
      // internally Log10RootFunction uses java8 function.andThen(), which requires API 24..
      return new BlockingAdaptiveExecutor(SimpleLimiter.newBuilder().build(), executorService);
    } else {
      final ThreadFactory threadFactory =
          new ThreadFactoryBuilder().setNameFormat("fixed-io-%d").build();
      return Executors.newCachedThreadPool(threadFactory);
    }
  }

  @NonNull
  @Override
  public Executor ioExecutor() {
    return ioExecutor;
  }

  @NonNull
  @Override
  public Scheduler ioScheduler() {
    return ioScheduler;
  }
}
