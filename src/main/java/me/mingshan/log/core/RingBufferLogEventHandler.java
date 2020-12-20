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
  private int batchCounter = DisruptorUtil.NOTIFY_PROGRESS_THRESHOLD;

  @Override
  public void setSequenceCallback(Sequence sequenceCallback) {
    this.sequenceCallback = sequenceCallback;
  }

  @Override
  public void onEvent(RingBufferLogEvent event, long sequence, boolean endOfBatch) throws Exception {
    final boolean pseudoEndOfBatch = endOfBatch || --batchCounter == 0;

    List<LogExportType> logExportTypes = LogExportType.fetchAllEnable();
    if (logExportTypes != null && !logExportTypes.isEmpty()) {
      for (LogExportType logExportType : logExportTypes) {
        LogExportHandler uploader = LogExportHandlerFactory.getUploader(logExportType);
        if (uploader != null) {
          uploader.export(event.getMessage());
        }
      }
    }

    event.clear();

    // ----
    if (pseudoEndOfBatch) {
      batchCounter = DisruptorUtil.NOTIFY_PROGRESS_THRESHOLD;
      sequenceCallback.set(sequence);
    }
  }
}
