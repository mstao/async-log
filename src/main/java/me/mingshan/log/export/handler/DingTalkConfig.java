/*
 * 版权所有(C)，上海海鼎信息工程股份有限公司，2020，所有权利保留。
 *
 * 项目名：openapi-server
 * 文件名：DingTalkConfig.java
 * 模块说明：
 * 修改历史：
 * 2020/12/18 上午9:11 - Walker Han - 创建。
 */

package me.mingshan.log.export.handler;

import lombok.Getter;
import lombok.Setter;

/**
 * 钉钉配置信息
 *
 * @author Walker Han
 * @date 2020/12/18 9:11
 */
@Setter
@Getter
public class DingTalkConfig {
  private String webHook;
}
