package com.joshvm.sensor.stream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
/**
 * Stream
 * @author Administrator
 *
 */
public class Stream {

	private StreamConnection streamConnection;
	private InputStream inputStream;
	private OutputStream outputStream;

	private StreamListener streamListener;

	/**
	 * StreamConnection connect host
	 * 
	 * @param host
	 * @throws IOException
	 */
	protected void connect(String host) throws IOException {

		if (host == null || host.equals("")) {

			throw new NullPointerException("Stream host is empty");
		}
		
		streamConnection = (StreamConnection) Connector.open(host);
		
		if (streamConnection == null) {

			throw new NullPointerException("Stream is null");
		}

		inputStream = streamConnection.openInputStream();
		outputStream = streamConnection.openOutputStream();
	}

	/**
	 * read buffer
	 * 
	 * @param buffer
	 * @throws IOException
	 */
	protected void read(byte[] buffer) throws IOException {

		if (inputStream == null) {

			throw new NullPointerException("InputStream is null");
		}

		int len = 0;
		while ((len = inputStream.read(buffer)) != -1) {

			if (streamListener == null) {

				throw new NullPointerException("StreamListener is null");
			}

			streamListener.onReceived(buffer, len);
		}
	}

	/**
	 * write buffer
	 * 
	 * @param b
	 * @param off
	 * @param len
	 * @throws IOException
	 */
	protected void write(byte[] b, int off, int len) throws IOException {

		if (outputStream == null) {

			throw new NullPointerException("OutputStream is Null");
		}

		outputStream.write(b, off, len);
	}

	/**
	 * StreamConnect disconnect
	 * 
	 * @throws IOException
	 */
	protected void disconnect() throws IOException {

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

	public InputStream getInputStream() {
		return inputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public StreamListener getStreamListener() {
		return streamListener;
	}

	protected void setStreamListener(StreamListener streamListener) {
		this.streamListener = streamListener;
	}

}
