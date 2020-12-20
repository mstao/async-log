package me.mingshan.log.api;

/**
 * 日志收集数据接口
 *
 * @param <E> 数据实体泛型
 * @author Walker Han
 */
public interface Reader<E extends Message> {

  /**
   * 收集数据消息
   *
   * @param message 消息体
   */
  void read(E message);
}
