package com.joshvm.sensor.manager;

import com.joshvm.sensor.constant.Constant;
import com.joshvm.sensor.utils.Utils;

public class AccManager extends SensorManager implements SensorCallback {
	private SensorInterface sensorInterface;
	private static byte[] dataBuffer = new byte[32];
	private static int dataPoi = 0;
	private static int dataLen = 0;
	
	public AccManager(SensorInterface sensorInterface) {
		this.sensorInterface = sensorInterface;
		setSensorCallback(this);
	}
	
	/**
	 * 单次查询发送指令
	 */
	public void sendHand() {

		uartStream.send(Constant.SENSOR_API_ACC);
	}

	/**
	 * 自动查询发送指令
	 * 
	 * @param rate
	 *            相应的频率
	 */
	public void sendAuto(int rate) {

		uartStream.send(Constant.SENSOR_API_AUTO_ACC);

		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (rate == 50) {
			uartStream.send(Constant.SENSOR_API_SET_RATE_50);
		} else if (rate == 100) {
			uartStream.send(Constant.SENSOR_API_SET_RATE_100);
		} else if (rate == 200) {
			uartStream.send(Constant.SENSOR_API_SET_RATE_200);
		}
	}
	public void onProcess(byte[] b, int len) {
		// TODO Auto-generated method stub
		for (int i = 0; i < len; i++) {

			// 判断第一个头
			if (dataPoi == 0 && b[i] == (byte) 0x5A) {

				dataBuffer[dataPoi] = b[i];
				dataPoi++;
				continue;
			}

			// 判断第二个头
			if (dataPoi == 1) {
				if (b[i] == (byte) 0x5A) {

					dataBuffer[dataPoi] = b[i];
					dataPoi++;
				} else {
					dataPoi = 0;
				}

				continue;
			}

			// 装功能码
			if (dataPoi == 2) {
				dataBuffer[dataPoi] = b[i];
				dataPoi++;
				continue;
			}

			// 判断长度
			if (dataPoi == 3) {

				if (b[i] > 5 && b[i] < 9) {

					dataBuffer[dataPoi] = b[i];
					dataLen = b[i];
					dataPoi++;

				} else {

					dataPoi = 0;
				}

				continue;
			}

			// 装有效数据
			if (dataPoi > 3 && dataPoi < dataLen + 4) {
				dataBuffer[dataPoi] = b[i];
				dataPoi++;
				continue;
			}

			// 判断校验码
			if (dataPoi == dataLen + 4) {
				if (b[i] == Utils.sumByteArray(dataBuffer, 0, dataPoi)) {

					dataPoi = 0;
					dataLen = 0;

					// 欧拉角计算,加速度计算，陀螺仪计算。
					 if (dataBuffer[2] == (byte) 0x15) {
						 
						short x = (short) ((((short) dataBuffer[4]) << 8) & 0xff00 | (dataBuffer[5] & 0x00ff));
						short y = (short) ((((short) dataBuffer[6]) << 8) & 0xff00 | (dataBuffer[7] & 0x00ff));
						short z = (short) ((((short) dataBuffer[8]) << 8) & 0xff00 | (dataBuffer[9] & 0x00ff));

						sensorInterface.onReceive(Constant.SENSOR_ACC, x, y, z);
					} 

				} else {

					dataPoi = 0;
					dataLen = 0;
				}

				continue;
			}

		}

		
	}
	public void onConnected() {
		// TODO Auto-generated method stub
		sensorInterface.onConnected();
	}

}
