package me.mingshan.log.core;

/**
 * Alternative Wait Strategies.
 *
 * @author mingshan
 */
public enum DisruptorWaitStrategy {

  /**
   * Like the BlockingWaitStrategy the SleepingWaitStrategy it attempts to be conservative with CPU usage, by using a simple busy wait loop,
   * but uses a call to LockSupport.parkNanos(1) in the middle of the loop.
   */
  SLEEP("SleepingWaitStrategy"),

  /**
   * 适合用于低延迟的系统。在要求极高性能且事件处理线数小于 CPU 逻辑核心数的场景中，推荐使用此策略
   */
  YIELD("YieldingWaitStrategy"),

  /**
   * 使用{@code Lock}和 {@code Condition}。CPU资源的占用少，延迟大
   */
  BLOCK("BlockingWaitStrategy"),

  /**
   * 自旋等待，类似Linux Kernel使用的自旋锁。低延迟但同时对CPU资源的占用也多
   */
  BUSYSPIN("BusySpinWaitStrategy"),

  /**
   * 相对于BlockingWaitStrategy来说，设置了等待时间，超过后抛异常
   */
  TIMEOUT("TimeoutBlockingWaitStrategy");

  private String desc;

  DisruptorWaitStrategy(String desc) {
    this.desc = desc;
  }

  public String getDesc() {
    return desc;
  }
}

