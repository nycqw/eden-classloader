package com.eden;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ClassLoaderUtil {
	
	/**
	 * 读取字节码文件
	 * 
	 * @param className
	 * @return
	 */
	public static byte[] getClassData(String path) {
		try {
			InputStream in = new FileInputStream(path);
			return parseStream2ByteArray(in);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
     * 从网络获取class文件
     * @param className
     * @return
     */
    public static byte[] getClassDataFromNet(String path) {
        try {
            URL url = new URL(path);
            return parseStream2ByteArray(url.openStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	private static byte[] parseStream2ByteArray(InputStream ins) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int bufferSize = 4096;
		byte[] buffer = new byte[bufferSize];
		int bytesNumRead = 0;
		while ((bytesNumRead = ins.read(buffer)) != -1) {
		    baos.write(buffer, 0, bytesNumRead);
		}
		return baos.toByteArray();
	}

}
