/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.mingshan.log.core;

import com.lmax.disruptor.EventTranslatorOneArg;
import me.mingshan.log.api.Message;
import me.mingshan.log.api.Reader;

import static me.mingshan.log.core.EventRoute.ENQUEUE;

/**
 * The implementation of logger.
 *
 * @author mingshan
 */
public class AsyncReader<E extends Message> implements Reader<E>, EventTranslatorOneArg<RingBufferLogEvent, E> {
  private final LogDisruptor loggerDisruptor;

  public AsyncReader(LogDisruptor loggerDisruptor) {
    this.loggerDisruptor = loggerDisruptor;
  }

  /**
   * Log message.
   *
   * @param message the message
   */
  private void logWithOneArgTranslator(E message) {
    // 使用{@link RingBuffer#tryPublishEvent} 会先尝试放入event，当RingBuffer会返回false，
    // 放入失败，此时需要进行处理
    if (!this.loggerDisruptor.getDisruptor().getRingBuffer()
            .tryPublishEvent(this, message)) {
        handleRingBufferFull(message);
    }
  }

  /**
   * Deals with queue full cases.
   *
   * @param message the message
   */
  private void handleRingBufferFull(E message) {
    if (message == null) {
      return;
    }

    final EventRoute eventRoute = LogDisruptor.getEventRoute(message.getLevel());
    switch (eventRoute) {
      case ENQUEUE:
        this.loggerDisruptor.getDisruptor().getRingBuffer()
            .publishEvent(this, message);
        break;
      case DISCARD:
        break;
      default:
        throw new IllegalStateException("Unknown EventRoute " + eventRoute);
    }
  }

  @Override
  public void translateTo(RingBufferLogEvent event, long sequence, E arg) {
    Thread currentThread = Thread.currentThread();
    event.setValues(arg, currentThread.getId(), currentThread.getName());
  }

  @Override
  public void read(E message) {
    logWithOneArgTranslator(message);
  }
}
