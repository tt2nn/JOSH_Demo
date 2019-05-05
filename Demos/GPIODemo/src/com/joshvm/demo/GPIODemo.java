package com.joshvm.demo;

import java.io.IOException;

import org.joshvm.j2me.dio.DeviceManager;
import org.joshvm.j2me.dio.DeviceNotFoundException;
import org.joshvm.j2me.dio.InvalidDeviceConfigException;
import org.joshvm.j2me.dio.UnavailableDeviceException;
import org.joshvm.j2me.dio.UnsupportedAccessModeException;
import org.joshvm.j2me.dio.UnsupportedDeviceTypeException;
import org.joshvm.j2me.dio.gpio.GPIOPin;
import org.joshvm.j2me.dio.gpio.GPIOPinConfig;
import org.joshvm.j2me.dio.gpio.PinEvent;
import org.joshvm.j2me.dio.gpio.PinListener;

/**
 * 提供GPIO相关功能Demo
 * <p>
 * 以2G开发板为例：GPIO_1=4,GPIO_2=27,GPIO_3=29
 * <p>
 * 请按照相关设备的硬件文档，进行相关参数配置
 * 
 * @author JOSH
 *
 */
public class GPIODemo {

	public static void main(String[] args) {

		try {

			/* 按键等输入设备 */
			// 设定为输入设备，支持上拉输入模式
			// 2G开发板的触发模式为： TRIGGER_LOW_LEVEL、TRIGGER_HIGH_LEVEL
			// 4G开发板的触发模式为：TRIGGER_BOTH_LEVELS
			// 2G、4G电平高低都会响应监听
			// InitValue不处理，可以随意填写
			GPIOPinConfig cfgInput = new GPIOPinConfig(GPIOPinConfig.UNASSIGNED, 4, GPIOPinConfig.DIR_INPUT_ONLY,
					GPIOPinConfig.MODE_INPUT_PULL_UP, GPIOPinConfig.TRIGGER_LOW_LEVEL, true);
			GPIOPin inputGpio = (GPIOPin) DeviceManager.open(cfgInput, DeviceManager.EXCLUSIVE);
			// 设置监听
			inputGpio.setInputListener(new PinListener() {
				public void valueChanged(PinEvent event) {

					System.out.println(event.getValue());

					if (event.getValue()) {
						// 拉高
					} else {
						// 拉低
					}
				}
			});

			/* 按键等输入设备 */
			// 设定为输出设备，支持开漏模式，默认拉低
			// 触发模式为：TRIGGER_NONE 无触发
			GPIOPinConfig cfgOutput = new GPIOPinConfig(GPIOPinConfig.UNASSIGNED, 27, GPIOPinConfig.DIR_OUTPUT_ONLY,
					GPIOPinConfig.MODE_OUTPUT_OPEN_DRAIN, GPIOPinConfig.TRIGGER_NONE, false);
			GPIOPin outputGpio = (GPIOPin) DeviceManager.open(cfgOutput, DeviceManager.EXCLUSIVE);

			// 拉高
			outputGpio.setValue(true);
			// 拉低
			outputGpio.setValue(false);

		} catch (InvalidDeviceConfigException e) {
			e.printStackTrace();
		} catch (UnsupportedDeviceTypeException e) {
			e.printStackTrace();
		} catch (DeviceNotFoundException e) {
			e.printStackTrace();
		} catch (UnavailableDeviceException e) {
			e.printStackTrace();
		} catch (UnsupportedAccessModeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
