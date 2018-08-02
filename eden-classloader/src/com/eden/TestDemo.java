package com.eden;

public class TestDemo {
	
	private static String rootDir = "E:\\workspace\\eden\\freshman\\bin";
	
	public static void main(String[] args) {
		try {
			String result = (String) FileClassLoader.invokeMethod(rootDir, "cqw.classloader.ClassLoaderTestClass", "sayHello", String.class);
			String result1 = (String) FileUrlClassLoader.invokeMethod(rootDir, "cqw.classloader.ClassLoaderTestClass", "sayHello", String.class);
			System.out.print(result + "--" + result1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
