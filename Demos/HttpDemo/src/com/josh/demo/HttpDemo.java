package com.josh.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

/**
 * Http Demo
 * <p>
 * 实现Http功能
 * <p>
 * 提供：连接，设置请求方式，设置头信息，获取数据，发送请求，断开连接等功能
 * 
 * @author JOSH
 *
 */
public class HttpDemo {

	private static String url = "http://www.baidu.com";

	private static HttpConnection httpConnection;
	private static InputStream inputStream;
	private static OutputStream outputStream;

	public static void main(String[] args) {

		try {
			// 上电后需要等待一会，进行网络初始化等
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		try {

			// 建立连接
			System.out.println("http connect : " + url);
			httpConnection = (HttpConnection) Connector.open(url);
			inputStream = httpConnection.openInputStream();
			outputStream = httpConnection.openOutputStream();
			System.out.println("http connected : " + url);

			// 设置请求格式
			httpConnection.setRequestMethod(HttpConnection.GET);
			// 设置头信息
			// httpConnection.setRequestProperty("Content-Type",
			// "application/json");

			// 获取Http响应code：2xx、4xx、5xx
			int respCode = httpConnection.getResponseCode();
			System.out.println("http resp code : " + respCode);

			// 获取Http响应的数据长度
			int responseLen = (int) httpConnection.getLength();
			System.out.println("http resp len : " + responseLen);

			if (responseLen > 0) {

				int readLen = 0;
				int readState = 0;
				byte[] readBuffer = new byte[responseLen];

				while (responseLen != readLen && readState != -1) {

					readState = inputStream.read(readBuffer, readLen, responseLen - readLen);
					readLen += readState;
				}

				System.out.println("http resp message : " + new String(readBuffer, 0, readLen));

			}

		} catch (IOException e) {

			e.printStackTrace();

		} finally {

			try {
				disConnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 发送请求
	 * 
	 * @param message
	 * @throws IOException
	 */
	private static void sendMessage(String message) throws IOException {

		if (outputStream != null) {

			outputStream.write(message.getBytes());
		}
	}

	/**
	 * 断开连接
	 * <p>
	 * 如果是阻塞读数据的时候，可以由外部进行关闭
	 * 
	 * @throws IOException
	 */
	private static void disConnect() throws IOException {

		if (inputStream != null) {

			inputStream.close();
		}

		if (outputStream != null) {

			outputStream.close();
		}

		if (httpConnection != null) {

			httpConnection.close();
		}
	}
}
