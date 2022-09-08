package com.abab.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serial;
import java.io.Serializable;

/**
 * 服务器响应
 *
 * @author 故故sb
 * @date 2022/09/08
 */ //字段为空的话，不序列化进json数据，即不向前台返回空的值。
@JsonSerialize
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ServerResponse<T> implements Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	private int status;
	private String message;
	private T data;

	/**
	 * 获得地位
	 *
	 * @return int
	 */
	public int getStatus() {
		return this.status;
	}

	/**
	 * 获取数据
	 *
	 * @return {@link T}
	 */
	public T getData() {
		return data;
	}

	/**
	 * 得到消息
	 *
	 * @return {@link String}
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 是成功
	 *
	 * @return boolean
	 */
	@JsonIgnore
	public boolean isSuccess() {
		return this.status==ResponseCode.SUCCESS.getCode();
	}

	/**
	 * 服务器响应
	 *
	 * @param status 状态
	 */
	private ServerResponse(int status) {
		this.status = status;
	}

	/**
	 * 服务器响应
	 *
	 * @param status 状态
	 * @param data   数据
	 */
	private ServerResponse(int status, T data) {
		this.status = status;
		this.data = data;
	}

	/**
	 * 服务器响应
	 *
	 * @param status  状态
	 * @param message 消息
	 * @param data    数据
	 */
	private ServerResponse(int status, String message, T data) {
		this.status = status;
		this.message = message;
		this.data = data;
	}

	/**
	 * 服务器响应
	 *
	 * @param status  状态
	 * @param message 消息
	 */
	private ServerResponse(int status, String message) {
		this.status = status;
		this.message = message;
	}

	/**
	 * 通过成功创建职责
	 *
	 * @return {@link ServerResponse}<{@link T}>
	 */
	public static <T> ServerResponse<T> createRespBySuccess() {
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode());
	}

	/**
	 * 通过成功创建职责
	 *
	 * @param data 数据
	 * @return {@link ServerResponse}<{@link T}>
	 */
	public static <T> ServerResponse<T> createRespBySuccess(T data) {
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), data);
	}

	/**
	 * 通过成功创建职责
	 *
	 * @param msg  味精
	 * @param data 数据
	 * @return {@link ServerResponse}<{@link T}>
	 */
	public static <T> ServerResponse<T> createRespBySuccess(String msg, T data) {
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg, data);
	}

	/**
	 * 创建resp成功消息
	 *
	 * @param msg 味精
	 * @return {@link ServerResponse}<{@link T}>
	 */
	public static <T> ServerResponse<T> createRespBySuccessMessage(String msg) {
		return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg);
	}

	/**
	 * 创建职责由错误
	 *
	 * @return {@link ServerResponse}<{@link T}>
	 */
	public static <T> ServerResponse<T> createRespByError() {
		return new ServerResponse<>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
	}

	/**
	 * 创建错误消息
	 *
	 * @param errorMessage 错误消息
	 * @return {@link ServerResponse}<{@link T}>
	 */
	public static <T> ServerResponse<T> createByErrorMessage(String errorMessage) {
		return new ServerResponse<T>(ResponseCode.ERROR.getCode(), errorMessage);
	}

	/**
	 * 错误代码创建消息
	 *
	 * @param errorCode    错误代码
	 * @param errorMessage 错误消息
	 * @return {@link ServerResponse}<{@link T}>
	 */
	public static <T> ServerResponse<T> createByErrorCodeMessage(int errorCode, String errorMessage) {
		return new ServerResponse<T>(errorCode, errorMessage);
	}
}
