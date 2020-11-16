package common.util;

import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author 寒洲
 * @description 文件/图片工具类
 * @date 2020/10/23
 */
public class FileUtil {

	/**
	 * 获取文件数据流
	 * @param filePath
	 * @return
	 */
	public static byte[] getFileStream(String filePath) {
		//字节输入流
		InputStream inputStream = null;
		//字节缓冲流数组
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			inputStream = new FileInputStream(filePath);
			byte[] b = new byte[1024];
			int len = -1;
			//读取
			while ((len = inputStream.read(b)) != -1) {
				outputStream.write(b, 0, len);
			}
			return outputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将数据以Base64转码返回
	 * @param data
	 * @return Base64转码后的数据流
	 */
	public static String getImgByBase64(byte[] data) {
		//Base64转码
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(data);
	}

	public static Process runProcess(String esPath) throws IOException {
		/*
		使用Runtime去运行命令行 CreateProcess error=193, %1 不是有效的 Win32 应用程序。
		错误代码193是运行了一个不信任的程序. 当你充分信任你的程序时候你可以使用cmd作为跳板
		解决方案：运行的命令行前面添加：“cmd \c ionic -v”
		 */
		return Runtime.getRuntime().exec("cmd /c " + esPath);
	}
}
