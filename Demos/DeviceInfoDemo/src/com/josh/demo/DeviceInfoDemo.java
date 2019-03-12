package com.josh.demo;

import java.io.IOException;

import org.joshvm.j2me.cellular.CellInfo;
import org.joshvm.j2me.cellular.CellularDeviceInfo;
import org.joshvm.j2me.cellular.NetworkInfo;

/**
 * DeviceInfoDemo
 * <p>
 * 获取设备信息、获取SIM卡信息
 * 
 * @author Josh
 */
public class DeviceInfoDemo {

	// 定义一个存储设备信息的变量devices，类型为CellularDeviceInfo[]的数组
	private static CellularDeviceInfo[] devices;

	// 定义设备信息的各个参数
	private static String imei; // 硬件序列号
	private static String imsi; // 卡的序列号
	private static String iccid; // 制卡商序列号
	private static int mnc; // MNC，联通：1，移动：0
	private static int mcc; // MCC，中国：460
	private static int lac; // 基站信息
	private static int cid; // cellid，基站定位信息,API暂无。
	private static int netWorkSignalLevel; // 获取信号强度

	public static void main(String[] args) {

		System.out.println(" ====  Start Demo, Please wait 15s...");

		try {
			Thread.sleep(15 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// 初始化设备信息
		try {
			devices = CellularDeviceInfo.listCellularDevices();

			// 检查设备是否存在
			if (devices != null && devices.length > 0) {

				System.out.println(" ====  Detection device!");

				imei = devices[0].getIMEI();
				imsi = devices[0].getIMSI();
				iccid = devices[0].getICCID();
				netWorkSignalLevel = devices[0].getNetworkSignalLevel();

				NetworkInfo networkInfo = devices[0].getNetworkInfo();
				if (networkInfo != null) {

					mnc = networkInfo.getMNC();
					mcc = networkInfo.getMCC();
				}

				CellInfo cellInfo = devices[0].getCellInfo();
				if (cellInfo != null) {

					lac = cellInfo.getLAC();
					// cid = cellInfo.getCellID(); //device没有加对应API，后期补充。
				}

				System.out.println(" ====  Device information:");
				System.out.println(" ====  IMEI:" + imei);
				System.out.println(" ====  IMSI:" + imsi);
				System.out.println(" ====  ICCID:" + iccid);
				System.out.println(" ====  MNC:" + mnc);
				System.out.println(" ====  MCC:" + mcc);
				System.out.println(" ====  LAC:" + lac);
				// System.out.println(" ==== Cellid:"+cid);
				System.out.println(" ====  NetWorkSignalLevel:" + netWorkSignalLevel);

			} else {

				System.out.println(" ====  Undetected device!");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
