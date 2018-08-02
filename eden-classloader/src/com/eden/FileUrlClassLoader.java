package com.eden;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;

public class FileUrlClassLoader extends URLClassLoader {
	private static FileUrlClassLoader INSTANCE;
	private static Object lock = new Object();
	
	private FileUrlClassLoader(URL[] urls) {
		super(urls);
	}
	 
	public static FileUrlClassLoader getInstance(String rootDir) throws MalformedURLException {
		if (INSTANCE == null) {
			synchronized (lock) {
				if (INSTANCE == null) {
					File file = new File(rootDir);
					URI uri = file.toURI();
					URL[] urls = { uri.toURL() };
					INSTANCE = new FileUrlClassLoader(urls);
				}
			}
		}
		return INSTANCE;
	}

	/**
	 * 调用指定方法
	 * 
	 * @param rootDir 类文件根路径
	 * @param ClassName 类名（包括包路径）
	 * @param methodName 方法名
	 * @param parameterTypes 方法参数类型
	 * @return 方法返回值
	 * @throws Exception
	 */
	public static Object invokeMethod(String rootDir, String ClassName,
			String methodName, Class<?>... parameterTypes ) throws Exception{
		ClassLoader classLoader = FileClassLoader.getInstance(rootDir);
		Class<?> clazz = classLoader.loadClass(ClassName);
		Object instance = clazz.newInstance();
		Method method = clazz.getMethod(methodName, parameterTypes);
		return method.invoke(instance, methodName);
	}

}
