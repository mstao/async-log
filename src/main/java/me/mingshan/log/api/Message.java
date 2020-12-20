package me.mingshan.log.api;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 日志消息体
 *
 * @author Walker Han
 */
public class Message implements Serializable {
  private static final long serialVersionUID = 7894314175824306032L;
  private String serviceName;
  private String methodName;
  private Object[] args;
  private Object result;
  private long executedTime;
  private String message;
  private String level;
  private Throwable e;

  public String getMethodName() {
    return methodName;
  }

  public void setMethodName(String methodName) {
    this.methodName = methodName;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public Object[] getArgs() {
    return args;
  }

  public void setArgs(Object[] args) {
    this.args = args;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

  public long getExecutedTime() {
    return executedTime;
  }

  public void setExecutedTime(long executedTime) {
    this.executedTime = executedTime;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Throwable getE() {
    return e;
  }

  public void setE(Throwable e) {
    this.e = e;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

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