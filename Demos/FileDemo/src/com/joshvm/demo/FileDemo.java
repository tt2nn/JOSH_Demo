package com.joshvm.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

/**
 * 提供文件读写示例
 * <p>
 * 包括：文件创建、读写、删除等功能.
 * <p>
 * Demo中实现了API的使用.
 * 
 * @author Administrator
 *
 */
public class FileDemo {

	public static void main(String[] args) {

		String filePath = "";
		String fileName = "";

		try {

			// 创建FileConnection , 目前file///Phone/根目录是固定的
			FileConnection fileConnection = (FileConnection) Connector
					.open("file:///Phone/" + filePath + "/" + fileName);

			if (!fileConnection.exists()) {

				// 创建File
				fileConnection.create();
			}

			// 写入File
			OutputStream outputStream = fileConnection.openOutputStream();
			String message = "Hello Josh";
			outputStream.write(message.getBytes());

			// 读File
			InputStream inputStream = fileConnection.openInputStream();
			byte[] buffer = new byte[256];
			int readLen = 0;
			while ((readLen = inputStream.read(buffer)) != -1) {

				System.out.println(new String(buffer, 0, readLen));
			}

			// 删除文件
			if (fileConnection.exists()) {

				fileConnection.delete();
			}

			// 关闭连接
			outputStream.close();
			inputStream.close();
			fileConnection.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
