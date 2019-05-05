package com.josh.sensor.stream;

import java.io.IOException;
/**
 * Stream Factory
 * @author Administrator
 *
 */
public class StreamFactory extends Stream implements Runnable {

	private boolean running;
	private String host;

	private byte[] buffer = new byte[256];

	/**
	 * open tcp stream
	 * 
	 * @param ip
	 * @param port
	 * @param streamListener
	 */
	public void openTcpStream(String ip, String port, StreamListener streamListener) {

		host = "socket://" + ip + ":" + port;
		setStreamListener(streamListener);
		open();
	}

	/**
	 * open uart stream
	 * 
	 * @param comNo
	 * @param baudrate
	 * @param streamListener
	 */
	public void openUartStream(String comNo, String baudrate, StreamListener streamListener) {

		host = "comm:" + comNo + ";baudrate=" + baudrate;
		setStreamListener(streamListener);
		open();
	}

	/**
	 * open can stream
	 * 
	 * @param canNo
	 * @param baudrate
	 * @param streamListener
	 */
	public void openCanStream(String canNo, String baudrate, StreamListener streamListener) {

		host = "can:" + canNo + ";baudrate=" + baudrate;
		setStreamListener(streamListener);
		open();
	}

	/**
	 * open stream
	 * 
	 * @param host
	 * @param streamListener
	 */
	public void openStream(String host, StreamListener streamListener) {

		this.host = host;
		setStreamListener(streamListener);
		open();
	}

	/**
	 * send data
	 * 
	 * @param data
	 */
	public void send(byte[] data) {

		try {
			write(data, 0, data.length);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void open() {

		running = true;
		new Thread(this).start();
	}

	public void closeStream() {

		try {
			running = false;
			disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {

		while (running) {

			try {

				connect(host);

				if (getStreamListener() != null) {

					getStreamListener().onConnected();
				}

				read(buffer);

				disconnect();

			} catch (IOException e) {

				e.printStackTrace();

				if (getStreamListener() != null) {

					getStreamListener().onError();
				}

			} finally {

				try {
					disconnect();
				} catch (IOException e) {
					e.printStackTrace();
				}

				if (getStreamListener() != null) {

					getStreamListener().onDisconnected();
				}
			}
		}

		if (getStreamListener() != null) {

			getStreamListener().onFinish();
		}
	}

}
