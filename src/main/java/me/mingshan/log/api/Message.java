package me.mingshan.log.api;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.event.Level;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 日志消息体
 *
 * @author Walker Han
 */
@Getter
@Setter
public class Message implements Serializable {
  private static final long serialVersionUID = 7894314175824306032L;
  private String serviceName;
  private String methodName;
  private Object[] args;
  private Object result;
  private long executedTime;
  private String message;
  private Level level;
  private Throwable e;

  @Override
  public String toString() {
    return "Message{" +
        "methodName='" + methodName + '\'' +
        ", serviceName='" + serviceName + '\'' +
        ", args=" + Arrays.toString(args) +
        ", result=" + result +
        ", executedTime=" + executedTime +
        ", message='" + message + '\'' +
        ", level=" + level +
        ", e=" + e +
        '}';
  }
}