package guru.bonacci.gcp.tracing1;


import java.util.concurrent.Callable;
import java.util.concurrent.Future;

import brave.Tracing;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.util.concurrent.ListenableFuture;

public class TraceAsyncListenableTaskExecutor implements AsyncListenableTaskExecutor {

  private final AsyncListenableTaskExecutor delegate;
  private final Tracing tracing;

  TraceAsyncListenableTaskExecutor(AsyncListenableTaskExecutor delegate,
      Tracing tracing) {
    this.delegate = delegate;
    this.tracing = tracing;
  }

  @Override
  public ListenableFuture<?> submitListenable(Runnable task) {
    return this.delegate.submitListenable(this.tracing.currentTraceContext().wrap(task));
  }

  @Override
  public <T> ListenableFuture<T> submitListenable(Callable<T> task) {
    return this.delegate.submitListenable(this.tracing.currentTraceContext().wrap(task));
  }

  @Override
  public void execute(Runnable task, long startTimeout) {
    this.delegate.execute(this.tracing.currentTraceContext().wrap(task), startTimeout);
  }

  @Override
  public Future<?> submit(Runnable task) {
    return this.delegate.submit(this.tracing.currentTraceContext().wrap(task));
  }

  @Override
  public <T> Future<T> submit(Callable<T> task) {
    return this.delegate.submit(this.tracing.currentTraceContext().wrap(task));
  }

  @Override
  public void execute(Runnable task) {
    this.delegate.execute(this.tracing.currentTraceContext().wrap(task));
  }

}