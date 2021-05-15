package me.mingshan.log;

import lombok.extern.slf4j.Slf4j;
import me.mingshan.log.api.Message;
import me.mingshan.log.core.AsyncReader;
import me.mingshan.log.core.LogDisruptor;

/**
 * 日志与第三方集成起始类
 *
 * @author Walker Han
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Slf4j
public class OpenLoggerStarter {
  private static final LogDisruptor LOG_DISRUPTOR = new LogDisruptor();
  private static volatile AsyncReader asyncReader;

  /**
   * Stop Disruptor.
   */
  public static void stop() {
    LOG_DISRUPTOR.stop();
  }

  /**
   * 记录消息
   *
   * @param message 消息体
   * @param <E> 泛型信息
   */
  public static <E extends Message> void read(E message) {
    try {
      LOG_DISRUPTOR.start();
      if (!checkDisruptorStatus()) {
        return;
      }

      boolean ignore = checkIgnore(message);
      if (ignore) {
        return;
      }

      getAsyncReader().read(message);
    } catch (Exception e) {
      log.error("日志：{}发送失败，原因：{}", message, e.getMessage());
    }
  }

  /**
   * 检查消息是否忽略
   *
   * @param message 消息
   * @param <E> 泛型信息
   * @return true：忽略；false：不忽略
   */
  private static <E extends Message> boolean checkIgnore(E message) {
    return false;
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
