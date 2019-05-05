package com.josh.sensor.utils;

public class Utils {

	/**
	 * slip String
	 * 
	 * @param source
	 * @param slip
	 * @return
	 */
	public static String[] slipString(String source, String slip) {

		int slipLen = 1;
		int i = 0;

		while ((i = source.indexOf(slip, i)) != -1) {

			slipLen++;
			i++;
		}

		String[] slipArray = new String[slipLen];

		i = 0;
		int poi = 0;
		int y = 0;

		while ((i = source.indexOf(slip, i)) != -1) {

			slipArray[poi] = source.substring(y, i);
			i++;
			y = i;
			poi++;
		}

		slipArray[poi] = source.substring(y, source.length());

		return slipArray;
	}
	
	/**
	 * 
	 * @param res
	 * @param start
	 * @param len
	 * @return
	 */
	public static byte sumByteArray(byte[] res, int start, int len) {

		byte sum = res[start];

		for (int i = start + 1; i < start + len; i++) {

			sum += res[i];
		}

		return sum;
	}

}
