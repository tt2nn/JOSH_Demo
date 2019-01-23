package com.josh.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 * UartDemo
 * <p>
 * 实现串口通信。
 * <p>
 * 提供：连接，读数据，发数据，关闭功能
 * 
 * @author Administrator
 *
 */
public class UartDemo {

	// 根据硬件及接线设定串口的com口
	private static String com = "COM1";
	// 波特率
	private static int baudrate = 9600;

	private static StreamConnection streamConnection;
	private static InputStream inputStream;
	private static OutputStream outputStream;

	public static void main(String[] args) {

//		new Thread(new Runnable() {
//			public void run() {
//
//				try {
//					Thread.sleep(10 * 1000);
//					// 10s后关闭串口
//					closeStream();
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}).start();

		String host = "comm:" + com + ";baudrate=" + baudrate;

		try {

			// 建立连接
			streamConnection = (StreamConnection) Connector.open(host);

			inputStream = streamConnection.openInputStream();
			outputStream = streamConnection.openOutputStream();

			int len = 0;
			byte[] buffer = new byte[256];
			StringBuffer stringBuffer = null;
			// 读数据
			while ((len = inputStream.read(buffer)) != -1) {

				stringBuffer = new StringBuffer();
				for (int i = 0; i < len; i++) {

					stringBuffer.append(Integer.toHexString(buffer[i] & 0xFF) + " ");
				}
				System.out.println(stringBuffer.toString());

				sendData(buffer, 0, len);
			}

		} catch (IOException e) {

			// Exception说明
			if (e.getMessage().equals(com + " port not found!")) {

				System.out.println("E1:COM设置错误");

			} else if (e.getMessage().equals("Stream closed")) {

				System.out.println("E2:Stream关闭");
			}

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
