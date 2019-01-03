package info.ericlin.pupularmovies.timber;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

/** A timber tree that logs thread name at beginning of every log statement */
public class ThreadNameTree extends Timber.DebugTree {

  @Override
  protected void log(int priority, String tag, @NotNull String message, Throwable t) {
    final String threadName = Thread.currentThread().getName();
    final String newMessage = String.format("{%s} %s", threadName, message);
    super.log(priority, tag, newMessage, t);
  }
}
