package common.util;

import org.apache.log4j.Logger;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

/**
 * @author 寒洲
 * @description 文件/图片工具类
 * @date 2020/10/23
 */
public class FileUtil {

	private static Logger logger = Logger.getLogger(FileUtil.class);

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

	/**
	 * 通过图片文件路径 获取其Base64编码
	 * @param filePath
	 * @return
	 */
	public static String getBase64ByImgPath(String filePath){
		return getImgByBase64(getFileStream(filePath));
	}

	/**
	 * base64转图片，保存本地
	 * @param base64str base64码
	 * @param savePath 图片路径
	 */
	public static void generateImageByBase64(String base64str, String savePath) {
		//对字节数组字符串进行Base64解码并生成图片
		if ("".equals(savePath) || "".equals(base64str) || base64str == null || savePath == null) {
			return;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		OutputStream out = null;
		try {
			//Base64解码
			byte[] b = decoder.decodeBuffer(base64str);
			for (int i = 0; i < b.length; ++i) {
				//调整异常数据
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			//生成jpeg图片，保存图片
			out = new FileOutputStream(savePath);
			out.write(b);
			out.flush();
		} catch (Exception e) {
			logger.error("base64转图片保存本地 失败：" + e.getMessage());
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 启动文件
	 * @param path 文件路径
	 * @return
	 * @throws IOException
	 */
	public static Process runProcess(String path) throws IOException {
		/*
		使用Runtime去运行命令行 CreateProcess error=193, %1 不是有效的 Win32 应用程序。
		错误代码193是运行了一个不信任的程序. 当你充分信任你的程序时候你可以使用cmd作为跳板
		解决方案：运行的命令行前面添加：“cmd \c ionic -v”
		 */
		String command = "cmd.exe /C start " + path;
		return Runtime.getRuntime().exec(command);
	}


}
