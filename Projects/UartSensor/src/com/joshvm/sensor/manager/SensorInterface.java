package com.josh.sensor.manager;

public interface SensorInterface {

	abstract void onReceive(String type,short a, short b, short c);
	abstract void onReceive(String type, short a, short b, short c, short d);
	abstract void onConnected();

}
