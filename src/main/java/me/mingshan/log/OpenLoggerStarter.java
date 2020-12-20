package me.mingshan.log;

import me.mingshan.log.api.Message;
import me.mingshan.log.core.AsyncReader;
import me.mingshan.log.core.LogDisruptor;

/**
 * 日志与第三方集成起始类
 *
 * @author Walker Han
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class OpenLoggerStarter {
  private static final LogDisruptor LOG_DISRUPTOR = new LogDisruptor();
  private static volatile AsyncReader asyncReader;

  /**
   * Start Disruptor.
   *
   */
  public static void start() {
    if (!checkDisruptorStatus()) {
      LOG_DISRUPTOR.start();
    }
  }

  /**
   * Stop Disruptor.
   */
  public static void stop() {
    LOG_DISRUPTOR.stop();
  }

  /**
   * Records info via entity.
   *
   * @param message the entity of message
   * @param <E>     the generics class
   */
  public static <E> void read(E message) {
    boolean disruptorStatus = checkDisruptorStatus();
    if (disruptorStatus) {
      getAsyncReader().read((Message) message);
    }
  }

  /**
   * Checks the status of the Disruptor, if the Disruptor is not running,
   * throw RuntimeException.
   */
  private static boolean checkDisruptorStatus() {
    return LOG_DISRUPTOR.checkDisruptorStatus();
  }

  /**
   * Gets async reader.
   *
   * @return the reader
   */
  private static <E extends Message> AsyncReader<E> getAsyncReader() {
    if (asyncReader == null) {
      synchronized (AsyncReader.class) {
        if (asyncReader == null) {
          asyncReader = new AsyncReader<E>(LOG_DISRUPTOR);
        }
      }
    }

    return asyncReader;
  }
}
