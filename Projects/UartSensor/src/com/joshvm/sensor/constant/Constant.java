package com.josh.sensor.constant;

public class Constant {
	
	//命令指令
	public static final byte[] SENSOR_API_SELF_CHECKING = { (byte) 0xA5, (byte) 0x57, (byte) 0xfc };//校准及自检传感器
	public static final byte[] SENSOR_API_CLEAR_ZERO = { (byte) 0xA5, (byte) 0x58, (byte) 0xfd };//清 YAW 为零，但不保存偏差（掉电后将丢失）
	public static final byte[] SENSOR_API_RESET = { (byte) 0xA5, (byte) 0x59, (byte) 0xfe };//恢复出厂设置（需重启生效）
	public static final byte[] SENSOR_API_SET_RATE_50 = { (byte) 0xA5, (byte) 0xa4, (byte) 0x49 };//数据输出速率 50hz（掉电保存）
	public static final byte[] SENSOR_API_SET_RATE_100 = { (byte) 0xA5, (byte) 0xa5, (byte) 0x4a };//数据输出速率 100hz（掉电保存）
	public static final byte[] SENSOR_API_SET_RATE_200 = { (byte) 0xA5, (byte) 0xa6, (byte) 0x4b };//数据输出速率 200hz（掉电保存）
	
	//自动输出
	public static final byte[] SENSOR_API_AUTO_ACC = { (byte) 0xA5, (byte) 0x15, (byte) 0xBA };//ACC data 
	public static final byte[] SENSOR_API_AUTO_GYRO = { (byte) 0xA5, (byte) 0x25, (byte) 0xCA };//GYRO data 
	public static final byte[] SENSOR_API_AUTO_OLA = { (byte) 0xA5, (byte) 0x45, (byte) 0xEA };//欧拉角
	public static final byte[] SENSOR_API_AUTO_QUATE = { (byte) 0xA5, (byte) 0x65, (byte) 0x0A };//四元数 
	
	//查询输出
	public static final byte[] SENSOR_API_OLA = { (byte) 0xA5, (byte) 0x95, (byte) 0x3A };//欧拉角
	public static final byte[] SENSOR_API_QUATE = { (byte) 0xA5, (byte) 0xb5, (byte) 0x5A };//四元数 
	public static final byte[] SENSOR_API_ACC = { (byte) 0xA5, (byte) 0xc5, (byte) 0x6A };//ACC data 
	public static final byte[] SENSOR_API_GYRO = { (byte) 0xA5, (byte) 0xd5, (byte) 0x7A };//GYRO data 
	
	//特殊指令
	public static final byte[] SENSOR_API_CLOSE_SEND_SAVE = { (byte) 0xA5, (byte) 0xCC, (byte) 0x71 };//该指令为关闭所有数据输出，掉电保存
	public static final byte[] SENSOR_API_CLOSE_SEND_NOSAVE = { (byte) 0xA5, (byte) 0x75, (byte) 0x1A };//该指令为关闭所有数据输出，掉电不保存
	public static final byte[] SENSOR_API_SAVE_SEND_SET = { (byte) 0xA5, (byte) 0xAA, (byte) 0x4F };//该指令为保存当前输出数据设置，掉电保存,重新上电后，将输出相应数据
	
	/*串口波特率设置指令，设置后重启生效 */
	public static final byte[] SENSOR_API_BAUD_115200 = { (byte) 0xA5, (byte) 0xaf, (byte) 0x54 };
	public static final byte[] SENSOR_API_BAUD_9600 = { (byte) 0xA5, (byte) 0xae, (byte) 0x53 };
	
	public static final String  SENSOR_OLA = "欧拉角";
	public static final String SENSOR_QUATE = "四元数";
	public static final String SENSOR_ACC = "加速度";
	public static final String SENSOR_GYRO = "陀螺仪";
	
	
	
}
