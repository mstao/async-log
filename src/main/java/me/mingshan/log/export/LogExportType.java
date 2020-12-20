/*
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 *
 * 项目名：openapi-server
 * 文件名：LogExportType.java
 * 模块说明：
 * 修改历史：
 * 2020/12/17 下午9:04 - 明山 - 创建。
 */

package me.mingshan.log.export;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志导出类型
 *
 * @author Walker Han
 */
public enum LogExportType {
  DING_TALK("钉钉", true);

  private String caption;
  private boolean enable;

  LogExportType(String caption, boolean enable) {
    this.caption = caption;
    this.enable = enable;
  }

  public static List<LogExportType> fetchAllEnable() {
    LogExportType[] values = values();
    if (values.length == 0) {
      return Collections.emptyList();
    }

    return Arrays.stream(values).filter(item -> item.enable).collect(Collectors.toList());
  }
}
