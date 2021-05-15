package me.mingshan.log.core;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Encapsulates the Disruptor, providing common operations.
 *
 * @author mingshan
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class LogDisruptor {
  private static final Logger LOGGER = LoggerFactory.getLogger(LogDisruptor.class);
  private volatile Disruptor<RingBufferLogEvent> disruptor;
  /** disruptor启动完成标识, 防止disruptor在启动过程中被误认为启动完成 */
  private volatile boolean disruptorStarted = false;

  public Disruptor<RingBufferLogEvent> getDisruptor() {
    return disruptor;
  }

  /**
   * 启动方法，执行时线程必然是获取到锁的。
   */
  private void actualStart() {
    if (disruptor != null) {
      return;
    }

    ThreadFactory threadFactory = new ThreadFactory() {
      private final AtomicInteger mCount = new AtomicInteger(1);

      @Override
      public Thread newThread(Runnable r) {
        return new Thread(r, "LogAsyncTask #" + mCount.getAndIncrement());
      }
    };

    int ringBufferSize = DisruptorUtil.calculateRingBufferSize();
    final WaitStrategy waitStrategy = DisruptorUtil.createWaitStrategy(DisruptorWaitStrategy.SLEEP);
    disruptor = new Disruptor<>(RingBufferLogEvent::new,
        ringBufferSize, threadFactory, ProducerType.SINGLE, waitStrategy);

    final ExceptionHandler<RingBufferLogEvent> errorHandler = new DefaultExceptionHandler();
    disruptor.setDefaultExceptionHandler(errorHandler);

    final EventHandler[] handlers = {new RingBufferLogEventHandler()};
    disruptor.handleEventsWith(handlers);
    disruptor.start();

    disruptorStarted = true;
    LOGGER.info("Disruptor started");
  }

  /**
   * 启动，支持多线程调用，只会启用一个实例
   */
  public void start() {
    if (!checkDisruptorStatus()) {
      synchronized(LogDisruptor.class) {
        if (!checkDisruptorStatus()) {
          actualStart();
        }
      }
    }
  }

  /**
   * Close Disruptor
   */
  public void stop() {
    disruptor.shutdown();
  }

  /**
   * Checks the status of disruptor.
   *
   * @return returns {@code true}, the disruptor is started, returns {@code false},
   * the disruptor
   */
  public boolean checkDisruptorStatus() {
    return disruptor != null && disruptorStarted;
  }

  public static EventRoute getEventRoute(Level level) {
    if (Level.DEBUG.equals(level) || Level.TRACE.equals(level)) {
      return EventRoute.DISCARD;
    }

    if (Level.WARN.equals(level) || Level.ERROR.equals(level) || Level.INFO.equals(level)) {
      return EventRoute.ENQUEUE;
    }

    return EventRoute.DISCARD;
  }
}
