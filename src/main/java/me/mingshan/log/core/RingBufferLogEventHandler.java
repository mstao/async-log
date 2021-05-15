package me.mingshan.log.core;

import com.lmax.disruptor.Sequence;
import com.lmax.disruptor.SequenceReportingEventHandler;
import me.mingshan.log.api.Message;
import me.mingshan.log.export.LogExportHandler;
import me.mingshan.log.export.LogExportHandlerFactory;
import me.mingshan.log.export.LogExportType;

import java.util.List;

/**
 * Handles the event that disruptor publish.
 *
 * @author mingshan
 */
public class RingBufferLogEventHandler<E extends Message> implements
    SequenceReportingEventHandler<RingBufferLogEvent<E>> {
  private Sequence sequenceCallback;
  private static final int NOTIFY_PROGRESS_THRESHOLD = DisruptorUtil.NOTIFY_PROGRESS_THRESHOLD;
  private int counter;

  @Override
  public void setSequenceCallback(Sequence sequenceCallback) {
    this.sequenceCallback = sequenceCallback;
  }

  @Override
  public void onEvent(RingBufferLogEvent<E> event, long sequence, boolean endOfBatch) throws Exception {
    try {
      if (event.getMessage() != null) {
        exportMessage(event);
      }
    } catch (Throwable e) {
      // NOOP
      // 默认消费报错，也认为消费完毕
    }
    finally {
      event.clear();
      // notify the BatchEventProcessor that the sequence has progressed.
      // Without this callback the sequence would not be progressed
      // until the batch has completely finished.
      notifyCallback(sequence);
    }
  }

  private void notifyCallback(long sequence) {
    if (++counter > NOTIFY_PROGRESS_THRESHOLD) {
      sequenceCallback.set(sequence);
      counter = 0;
    }
  }

  private static <E extends Message> void exportMessage(RingBufferLogEvent<E> event) {
    List<LogExportType> logExportTypes = LogExportType.fetchAllEnable();
    if (logExportTypes == null || logExportTypes.isEmpty()) {
      return;
    }

    for (LogExportType logExportType : logExportTypes) {
      LogExportHandler<E> uploader = LogExportHandlerFactory.getUploader(logExportType);
      if (uploader != null) {
        uploader.export(event.getMessage());
      }
    }
  }
}
