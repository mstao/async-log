package me.mingshan.log.core;

import com.lmax.disruptor.*;

import java.util.concurrent.TimeUnit;

/**
 * The Util of Disruptor.
 *
 * @author mingshan
 */
public class DisruptorUtil {
  /**
   * RINGBUFFER default value.
   */
  public static final int RINGBUFFER_DEFAULT_SIZE = 256 * 1024;

  /**
   * RINGBUFFER NO GC default value.
   */
  public static final int RINGBUFFER_NO_GC_DEFAULT_SIZE = 4 * 1024;

  /**
   * BlockingWaitStrategy time-out period.
   */
  public static final long BLOCKING_WAIT_STRAGETY_TIMEOUT_MILLIS = 123L;

  /**
   * {@code SequenceReportingEventHandler} The thresholds for the batch Event.
   */
  public static final int NOTIFY_PROGRESS_THRESHOLD = 50;

  /**
   * Gets the {@link WaitStrategy} implementation via {@link DisruptorWaitStrategy}.
   *
   * @param strategyUp the enum {@link DisruptorWaitStrategy}
   * @return the {@link WaitStrategy} implementation
   */
  public static WaitStrategy createWaitStrategy(DisruptorWaitStrategy strategyUp) {
    switch (strategyUp.toString()) {
      case "SLEEP":
        return new SleepingWaitStrategy();
      case "YIELD":
        return new YieldingWaitStrategy();
      case "BLOCK":
        return new BlockingWaitStrategy();
      case "BUSYSPIN":
        return new BusySpinWaitStrategy();
      case "TIMEOUT":
        return new TimeoutBlockingWaitStrategy(BLOCKING_WAIT_STRAGETY_TIMEOUT_MILLIS,
            TimeUnit.MILLISECONDS);
      default:
        return new TimeoutBlockingWaitStrategy(BLOCKING_WAIT_STRAGETY_TIMEOUT_MILLIS,
            TimeUnit.MILLISECONDS);
    }
  }

  /**
   * Calculates the size of RingBuffer.
   *
   * @return the size of RingBuffer
   */
  public static int calculateRingBufferSize() {
    return IntegerUtil.ceilingNextPowerOfTwo(RINGBUFFER_DEFAULT_SIZE);
  }
}
