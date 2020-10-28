package common.util;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * @author 寒洲
 * @description MD5加密
 * @date 2020/8/31
 */
public class Md5Utils {
	private static final int RADIX = 32;


	/**
	 * 进行MD5加密
	 * @param source
	 * @return
	 */
	public static String md5Encode(String source) {


		String result = null;
		try {
			//获取MD5算法对象
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			//对明文进行加密操作
			md5.update(source.getBytes());
			result = new BigInteger(md5.digest(source.getBytes())).toString(RADIX);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
