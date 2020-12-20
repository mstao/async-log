/*
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 *
 * 项目名：openapi-server
 * 文件名：LogWrite.java
 * 模块说明：
 * 修改历史：
 * 2020/12/17 下午9:02 - 明山 - 创建。
 */

package me.mingshan.log.export;

import me.mingshan.log.api.Exporter;
import me.mingshan.log.api.Message;

/**
 * 日志消息导出处理器接口
 *
 * @param <E> 数据实体泛型
 * @author Walker Han
 */
public interface LogExportHandler<E extends Message> extends Exporter<E> {
  /**
   * 导出平台的类型
   *
   * @return LogExportType
   */
  LogExportType getExportType();
}
