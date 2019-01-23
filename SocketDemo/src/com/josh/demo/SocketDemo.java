package com.josh.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 * Demo For Socket
 * <p>
 * 实现Socket功能
 * <p>
 * 提供：连接，读，写，关闭等功能
 * 
 * @author josh
 *
 */
public class SocketDemo {

	// IP
	private static String ip = "www.baidu.com";
	// 端口
	private static String port = "80";

	private static StreamConnection streamConnection;
	private static InputStream inputStream;
	private static OutputStream outputStream;

	public static void main(String[] args) {

		String host = "socket://" + ip + ":" + port;

		try {
			// 上电后需要等待一会，进行网络初始化等
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		try {

			// 建立连接
			streamConnection = (StreamConnection) Connector.open(host);

			inputStream = streamConnection.openInputStream();
			outputStream = streamConnection.openOutputStream();

			// 发送请求
			String request = "GET / HTTP/1.0\n\n";
			byte[] reqBytes = request.getBytes();
			sendData(reqBytes, 0, reqBytes.length);

			int len = 0;
			byte[] buffer = new byte[256];
			// 读数据
			while (inputStream != null && (len = inputStream.read(buffer)) != -1) {

				System.out.println(new String(buffer, 0, len, "utf-8"));
			}

		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 发送数据
	 * 
	 * @param buffer
	 * @param off
	 * @param len
	 * @throws IOException
	 */
	private static void sendData(byte[] buffer, int off, int len) throws IOException {

		if (outputStream != null) {

			outputStream.write(buffer, off, len);
		}
	}

	/**
	 * 关闭串口连接
	 * <p>
	 * 如果是阻塞读数据的时候，可以由外部进行关闭
	 * 
	 * @throws IOException
	 */
	private static void closeStream() throws IOException {

		if (inputStream != null) {

			inputStream.close();
			inputStream = null;
		}

		if (outputStream != null) {

			outputStream.close();
			outputStream = null;
		}

		if (streamConnection != null) {

			streamConnection.close();
			streamConnection = null;
		}
	}
}
