package com.josh.sensor.stream;


public interface StreamListener {

	public void onConnected();

	public void onReceived(byte[] b, int len);
	
	public void onError();

	public void onDisconnected();
	
	public void onFinish();

}
