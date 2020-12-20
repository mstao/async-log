package me.mingshan.log.core;


import me.mingshan.log.api.Event;
import me.mingshan.log.api.Message;

/**
 * The event for disruptor.
 *
 * @author mingshan
 */
public class RingBufferLogEvent<E extends Message> implements Event<E> {
  private static final long serialVersionUID = -5979529297652042300L;
  private long threadId;
  private String threadName;
  private E message;
  private boolean endOfBatch;

  @Override
  public E getMessage() {
    return message;
  }

  @Override
  public String getThreadName() {
    return this.threadName;
  }

  @Override
  public long getThreadId() {
    return this.threadId;
  }

  @Override
  public boolean isEndOfBatch() {
    return this.isEndOfBatch();
  }

  public void setValues(E message, long threadId, String threadName) {
    this.message = message;
    this.threadId = threadId;
    this.threadName = threadName;
  }

  public void clear() {
    this.message = null;
    this.threadName = null;
    this.threadId = 0L;
  }

}
