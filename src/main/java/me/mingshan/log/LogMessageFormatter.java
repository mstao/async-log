/*
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 *
 * 项目名：openapi-server
 * 文件名：LogConstants.java
 * 模块说明：
 * 修改历史：
 * 2020/12/18 上午9:25 - Walker Han - 创建。
 */

package me.mingshan.log;

import me.mingshan.log.api.Message;

/**
 * 消息体格式化
 *
 * @author Walker Han
 * @date 2020/12/18 9:25
 */
public class LogMessageFormatter {
  /** 下发接口 接收校验 报警消息前缀 */
  public static final String DOWNLOAD_RECEIVE_MESSAGE_PREFIX = "下发接口接收校验报警";
  /** 下发接口 保存到iwms 报警消息前缀 */
  public static final String DOWNLOAD_SAVE_MESSAGE_PREFIX = "下发接口接入系统报警";
  /** 上传接口报警消息前缀 */
  public static final String UPLOAD_MESSAGE_PREFIX = "上传接口报警";

  /**
   * 下发校验消息格式化
   *
   * @param message 原始消息信息
   * @return 格式化的消息信息
   */
  public static Message downloadVerifyMessageFormat(String message) {
    Message entity = new Message();
    entity.setMessage(String.format("[%s] - %s", DOWNLOAD_RECEIVE_MESSAGE_PREFIX, message));

    return entity;
  }

  /**
   * 下发数据接入系统消息格式化
   *
   * @param message 原始消息信息
   * @return 格式化的消息信息
   */
  public static Message downloadSaveMessageFormat(String message) {
    Message entity = new Message();
    entity.setMessage(String.format("[%s] - %s", DOWNLOAD_SAVE_MESSAGE_PREFIX, message));

    return entity;
  }

  /**
   * 上传消息格式化
   *
   * @param message 原始消息信息
   * @return 格式化的消息信息
   */
  public static Message uploadMessageFormat(String message) {
    Message entity = new Message();
    entity.setMessage(String.format("[%s] - %s", UPLOAD_MESSAGE_PREFIX, message));

    return entity;
  }
}
