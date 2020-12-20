/*
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 *
 * 项目名：openapi-server
 * 文件名：UploadMethodHandlerFactory.java
 * 模块说明：
 * 修改历史：
 * 2020/11/27 下午2:49 - Walker Han - 创建。
 */

/*
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 *
 * 项目名：openapi-server
 * 文件名：UploadMethodFactory.java
 * 模块说明：
 * 修改历史：
 * 2020/11/26 下午6:38 - Walker Han - 创建。
 */

package me.mingshan.log.export;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Walker Han
 * @date 2020/11/26 18:38
 */
public class LogExportHandlerFactory {
  private static volatile Map<String, LogExportHandler> uploaderMap;

  private static void assignUploader() {
    if (uploaderMap == null || uploaderMap.isEmpty()) {
      synchronized (LogExportHandlerFactory.class) {
        if (uploaderMap == null || uploaderMap.isEmpty()) {
          if (uploaderMap == null) {
            uploaderMap = new HashMap<>();
          }

          Map<String, LogExportHandler> handlers = null;
          for (String key : handlers.keySet()) {
            uploaderMap.put(handlers.get(key).getExportType().name(), handlers.get(key));
          }
        }
      }
    }
  }

  public static LogExportHandler getUploader(LogExportType logExportType) {
    assignUploader();

    return uploaderMap.get(logExportType.name());
  }
}
