package com.joshvm.sensor;

import com.joshvm.sensor.manager.AccManager;
import com.joshvm.sensor.manager.QuateManager;
import com.joshvm.sensor.manager.SensorInterface;
import com.joshvm.sensor.manager.SensorManager;

/**
 * 该项目检测串口相关传感器数据读取
 * 
 * @author Administrator
 *
 */
public class SensorApplication implements SensorInterface {

	public static void main(String[] args) {

		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		SensorManager.register(new QuateManager(new SensorApplication()));
		SensorManager.connectSensor();

	}

	public void onConnected() {

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 发送查询指令
		AccManager.send(200);

	}

	public void onReceive(String type, short a, short b, short c) {
		System.out.println(type + "=====x:" + a + "=====y:" + b + "======z:" + c);

	}

	public void onReceive(String type, short a, short b, short c, short d) {
		System.out.println(type + "=====q0:" + a + "=====q1:" + b + "======q2:" + c + "======q3:" + d);

	}

}
