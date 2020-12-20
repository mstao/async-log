package me.mingshan.log.core;

import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

  public Disruptor<RingBufferLogEvent> getDisruptor() {
    return disruptor;
  }

  /**
   * Start Disruptor
   */
  public synchronized void start() {
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
    final WaitStrategy waitStrategy = DisruptorUtil.createWaitStrategy(DisruptorWaitStrategy.TIMEOUT);
    disruptor = new Disruptor<>(RingBufferLogEvent::new,
        ringBufferSize, threadFactory, ProducerType.SINGLE, waitStrategy);

    final ExceptionHandler<RingBufferLogEvent> errorHandler = new DefaultExceptionHandler();
    disruptor.setDefaultExceptionHandler(errorHandler);

    final EventHandler[] handlers = {new RingBufferLogEventHandler()};
    disruptor.handleEventsWith(handlers);
    disruptor.start();
    LOGGER.info("Disruptor started");
  }

  /**
   * Close Disruptor
   */
  public void stop() {
    disruptor.shutdown();
  }

  public boolean tryPublish(RingBufferLogEventTranslator translator) {
    try {
      return disruptor.getRingBuffer().tryPublishEvent(translator);
    } catch (final NullPointerException npe) {
      return false;
    }
  }

  /**
   * Checks the status of disruptor.
   *
   * @return returns {@code true}, the disruptor is started, returns {@code false},
   * the disruptor
   */
  public boolean checkDisruptorStatus() {
    return disruptor != null;
  }
}
