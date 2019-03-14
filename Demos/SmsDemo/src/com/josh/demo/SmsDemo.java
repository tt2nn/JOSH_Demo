package com.josh.demo;

import javax.microedition.io.Connector;
import javax.wireless.messaging.BinaryMessage;
import javax.wireless.messaging.Message;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.MessageListener;
import javax.wireless.messaging.TextMessage;

public class SmsDemo implements MessageListener {
	
	private static MessageConnection receiveMsgconn;	//用于接收短信的连接
	private static MessageConnection sendMsgconn;		//用于发送短信的连接
	private static Message message;						//存储接收到的信息
	private static String senderAddress;				//接收短信的发送者的地址
	private static String receiverAddress;				//发送短信的接收者的地址
	private static String getPort = "0";				//接收短信的端口，暂时不支持端口短信，默认为0。
	
	public static void main(String[] args) {
		
		System.out.println(" ====  Start SmsDemo..");
		
		try {
			
			/*
			 * 发送短信
			 */
			new Thread(new Runnable() {									
				
				public void run() {
					try {
						System.out.println(" ====  Start Send Message, Please wait 20s..");
						
						Thread.sleep(20 * 1000);
						
						receiverAddress = "sms://" + "+8618641826057" + ":" + getPort;			//设置接收者的地址
						
						sendMsgconn = (MessageConnection) Connector.open(receiverAddress);		//打开短信连接
						TextMessage textmsg = (TextMessage) sendMsgconn.newMessage(MessageConnection.TEXT_MESSAGE);		//新建可以承载消息内容的负载文本
						
						String sendMessage = "Hello Josh!";										//设置要发送的内容
						textmsg.setPayloadText(sendMessage);									//把要发送的内容添加进负载文本
						
						sendMsgconn.send(textmsg); 												//发送短信
						
						System.out.println("\n ====  Already Send:"+sendMessage);
						
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			}).start();
			
			
			/*
			 * 接收短信
			 */
			System.out.println(" ====  Start Receive Message, Please use other devices to send messages..");
			
			SmsDemo smsDemo = new SmsDemo();
			
			String address = "sms://:" + getPort;			
			receiveMsgconn = (MessageConnection) Connector.open(address);		//打开连接
			
			receiveMsgconn.setMessageListener(smsDemo);							//设置监听模式
			
			//一直收取短信，否则只能收一条短信
			while(true){
				
				message = receiveMsgconn.receive();								//获取消息对象
				
				if (message != null) {		
					
	                senderAddress = message.getAddress();						//从接收到的消息中获取地址
	                System.out.println(" ====  From: " + senderAddress);
	                
	                //检查接收的消息类型
	                if (message instanceof TextMessage) {
	                	
	                	String text = ((TextMessage)message).getPayloadText();							//获取消息的文本数据
	                		
	                    System.out.println(" ====  Recieved Text Messsage: " + text);		
	                    
	                } else if(message instanceof BinaryMessage) {
	                	
	                	byte[] data = ((BinaryMessage) message).getPayloadData();						//获取消息的二进制数据
	                   
	                	System.out.println(" ====  Recieved Binary Messsage: "+new String(data));
	                }
	            }
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}

	public void notifyIncomingMessage(MessageConnection conn) {
		
	}
	
	

}
