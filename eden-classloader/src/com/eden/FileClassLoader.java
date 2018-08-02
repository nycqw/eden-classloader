package com.eden;

import java.lang.reflect.Method;

/**
 * 文件类加载器
 * 加载指定根路径下的class文件
 * @author chenqw
 */
public class FileClassLoader extends ClassLoader {
	private String rootDir;
	private static FileClassLoader INSTANCE;
	private static Object lock = new Object();

	private FileClassLoader(String rootDir) {
		if (INSTANCE != null) {
			throw new UnsupportedOperationException("实例已存在，无法初始化！");
		} else {
			this.rootDir = rootDir;
		}
	}

	public static FileClassLoader getInstance(String rootDir) {
		if (INSTANCE == null) {
			synchronized (lock) {
				if (INSTANCE == null) {
					INSTANCE = new FileClassLoader(rootDir);
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

	/*
	 * 重写文件查找逻辑
	 */
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String path = classNameToPath(name);
		byte[] classData = ClassLoaderUtil.getClassData(path);
		if (classData == null) {
			throw new ClassNotFoundException();
		} else {
			return defineClass(name, classData, 0, classData.length);
		}
	}

	private String classNameToPath(String className) {
		String path = rootDir.replace("\\", "/");
		if (path.lastIndexOf("/") != path.length() - 1) {
			path = path + "/";
		}
		return path + className.replace(".", "/") + ".class";
	}

}
