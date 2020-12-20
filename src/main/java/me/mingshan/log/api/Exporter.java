package me.mingshan.log.api;

/**
 * 数据导出接口
 *
 * @param <E> 数据实体泛型
 * @author Walker Han
 */
public interface Exporter<E extends Message> {

  /**
   * 导出数据到不同对接平台
   *
   * @param message 消息体
   */
  void export(E message);
}
