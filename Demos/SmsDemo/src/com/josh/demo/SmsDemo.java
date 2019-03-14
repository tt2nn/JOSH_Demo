package com.josh.demo;

import java.io.IOException;
import java.io.InterruptedIOException;

import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import javax.wireless.messaging.TextMessage;

/**
 * SmsDemo
 * <p>
 * 提供短信收发功能
 * 
 * @author Administrator
 *
 */
public class SmsDemo {

	private static String phoneNumber = "+8618641826057"; // 对于手机号码需要+86，平台号码不需要.
	private static String getPort = "0"; // 接收短信的端口，暂时不支持端口短信，默认为0。

	public static void main(String[] args) {

		System.out.println("start sms demo...");

		// 等待20s用于网络初始化
		try {
			Thread.sleep(20 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/*
		 * 发送短信
		 */
		try {

			String sendAddress = "sms://" + phoneNumber + ":" + getPort; // 设置接收者的地址
			MessageConnection sendMsgconn = (MessageConnection) Connector.open(sendAddress);// 打开短信连接
			TextMessage textmsg = (TextMessage) sendMsgconn.newMessage(MessageConnection.TEXT_MESSAGE); // 新建可以承载消息内容的负载文本

			String sendMessage = "Hello Josh!"; // 设置要发送的内容
			textmsg.setPayloadText(sendMessage); // 把要发送的内容添加进负载文本

			sendMsgconn.send(textmsg); // 发送短信

			System.out.println(" Already Send:" + sendAddress + ":" + sendMessage);

		} catch (IOException e) {
			e.printStackTrace();
		}

		/*
		 * 接收短信
		 */
		try {

			// 设置短信接收模式。默认填写
			String receiveAddressSet = "sms://:" + getPort;
			final MessageConnection receiveMsgconn = (MessageConnection) Connector.open(receiveAddressSet);

			// 设置监听
			receiveMsgconn.setMessageListener(new MessageListener() {

				public void notifyIncomingMessage(MessageConnection conn) {

					try {

						// receive为阻塞状态,建议放入子线程
						Message message = receiveMsgconn.receive();
						if (message != null) {

							String senderAddress = message.getAddress(); // 从接收到的消息中获取地址
							System.out.println(" receive sms from : " + senderAddress);

							// 检查接收的消息类型
							if (message instanceof TextMessage) {

								String text = ((TextMessage) message).getPayloadText(); // 获取消息的文本数据

								System.out.println(" ==== Recieved Text Messsage: " + text);

							} else if (message instanceof BinaryMessage) {

								byte[] data = ((BinaryMessage) message).getPayloadData(); // 获取消息的二进制数据

								System.out.println(" ==== Recieved Binary Messsage: " + new String(data));
							}
						}

					} catch (InterruptedIOException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
