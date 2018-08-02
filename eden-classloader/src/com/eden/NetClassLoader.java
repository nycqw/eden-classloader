package com.eden;

public class NetClassLoader extends ClassLoader{
	private String url;
	private static NetClassLoader INSTANCE;
	private static Object lock = new Object();

	private NetClassLoader(String url) {
		if (INSTANCE != null) {
			throw new UnsupportedOperationException("实例已存在，无法初始化！");
		} else {
			this.url = url;
		}
	}

	public static NetClassLoader getInstance(String url) {
		if (INSTANCE == null) {
			synchronized (lock) {
				if (INSTANCE == null) {
					INSTANCE = new NetClassLoader(url);
				}
			}
		}
		return INSTANCE;
	}
	
	@Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = ClassLoaderUtil.getClassDataFromNet(url);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return defineClass(name, classData, 0, classData.length);
        }
    }

}
