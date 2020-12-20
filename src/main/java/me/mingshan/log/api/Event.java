package me.mingshan.log.api;

import java.io.Serializable;

/**
 * Interface that logEvent.
 *
 * @author Walker Han
 */
public interface Event<E extends Message> extends Serializable {

  /**
   * Gets byte array of the message.
   *
   * @return the byte array
   */
  E getMessage();

  /**
   * Gets the name of current thread.
   *
   * @return the name
   */
  String getThreadName();

  /**
   * Gets the id of current thread.
   *
   * @return the id
   */
  long getThreadId();

  /**
   * The flag to indicate if this is the last event in a batch from the {@code RingBuffer}.
   *
   * @return see comments
   */
  boolean isEndOfBatch();
}
