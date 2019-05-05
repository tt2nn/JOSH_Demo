package com.joshvm.sensor.manager;

public interface SensorCallback {
	
	void onProcess(byte[] b, int len);
	void onConnected();

}
