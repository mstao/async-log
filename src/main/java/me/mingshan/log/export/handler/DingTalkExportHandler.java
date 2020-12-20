/*
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 *
 * 项目名：openapi-server
 * 文件名：DingTalkExportHandler.java
 * 模块说明：
 * 修改历史：
 * 2020/12/17 下午11:01 - 明山 - 创建。
 */

package me.mingshan.log.export.handler;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;
import com.taobao.api.internal.toplink.embedded.websocket.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import me.mingshan.log.api.Message;
import me.mingshan.log.export.LogExportHandler;
import me.mingshan.log.export.LogExportType;

/**
 * 钉钉机器人上传
 *
 * @param <E>
 */
@Slf4j
public class DingTalkExportHandler<E extends Message> implements LogExportHandler<E> {
  private DingTalkConfig dingTalkConfig;

  @Override
  public LogExportType getExportType() {
    return LogExportType.DING_TALK;
  }

  @Override
  public void export(E message) {
    if (message == null) {
      return;
    }

    String webHook = dingTalkConfig.getWebHook();
    if (webHook == null || webHook.length() == 0) {
      log.warn("未配置钉钉WebHook地址，如需对接钉钉，请配置该项");
      return;
    }

    DingTalkClient client = new DefaultDingTalkClient(webHook);
    OapiRobotSendRequest request = new OapiRobotSendRequest();
    request.setMsgtype("text");
    OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
    text.setContent(message.getMessage());
    request.setText(text);
//    OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
//    at.setAtMobiles(Arrays.asList("17621973797"));
//    // isAtAll类型如果不为Boolean，请升级至最新SDK
//    at.setIsAtAll(false);
//    request.setAt(at);

    try {
      OapiRobotSendResponse response = client.execute(request);
      log.info(response.getErrmsg());
    } catch (ApiException e) {
      log.error("发送消息到钉钉失败，原因：{}", e.getErrMsg());
    }
  }
}
