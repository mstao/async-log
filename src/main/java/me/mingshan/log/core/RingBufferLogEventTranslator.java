package me.mingshan.log.core;

import com.lmax.disruptor.EventTranslator;
import com.lmax.disruptor.RingBuffer;
import me.mingshan.log.api.Message;

/**
 * Implementations translate (write) data representations into events claimed from the {@link RingBuffer}.
 *
 * @author mingshan
 */
public class RingBufferLogEventTranslator<E extends Message> implements EventTranslator<RingBufferLogEvent<E>> {
  private E message;
  private long threadId;
  private String threadName;

  @Override
  public void translateTo(RingBufferLogEvent event, long sequence) {
    event.setValues(message, threadId, threadName);
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
