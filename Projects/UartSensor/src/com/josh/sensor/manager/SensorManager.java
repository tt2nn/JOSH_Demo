package com.josh.sensor.manager;

import com.josh.sensor.constant.Constant;
import com.josh.sensor.stream.StreamFactory;
import com.josh.sensor.stream.StreamListener;
import com.josh.sensor.utils.Utils;

public abstract class SensorManager {

	protected static StreamFactory uartStream;

	private static boolean keepReading = false;
	private static Thread thread;

	private static SensorManager sensorManager;
	private static SensorCallback sensorCallback;
	
	public static void setSensorCallback(SensorCallback sensorCallback) {
		SensorManager.sensorCallback = sensorCallback;
	}

	/**
	 * 注册实际业务逻辑NetManager
	 * 
	 * @param netManager
	 */
	public static void register(SensorManager sensorManager) {

		SensorManager.sensorManager = sensorManager;
	}

	public static void send() {
		sensorManager.sendHand();
	}

	public static void send(int rate) {
		sensorManager.sendAuto(rate);
	}

	protected abstract void sendHand();

	protected abstract void sendAuto(int rate);

	public static void connectSensor() {

		uartStream = new StreamFactory();

		uartStream.openUartStream("COM1", "9600", new StreamListener() {

			public void onReceived(byte[] b, int len) {
				
				sensorCallback.onProcess(b, len);
				
				
			}

			public void onFinish() {

			}

			public void onError() {

			}

			public void onDisconnected() {

			}

			public void onConnected() {
				uartStream.send(Constant.SENSOR_API_CLOSE_SEND_SAVE);
				sensorCallback.onConnected();

			}

		});
	}

	public static void closeSensor() {

		keepReading = false;
		if (uartStream != null) {

			uartStream.closeStream();
		}
	}

}
