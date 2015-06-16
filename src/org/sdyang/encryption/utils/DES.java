package org.sdyang.encryption.utils;

import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * DES对称加密
 *
 * @date   2014-11-1 下午2:20:21
 */

public class DES {
	private static final String Algorithm = "DESede"; // 定义 加密算法,可用
	private static String BASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private static int LENGTH = 24;

	public static String encodeWithBase64(String key, String rawData) { // 生成密钥
		try {
			SecretKey deskey = new SecretKeySpec(toKeyBytes(key), Algorithm);
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			return Base64.encode(c1.doFinal(rawData.getBytes("GBK")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	private static byte[] toKeyBytes(String key) {
		return key.getBytes();
	}

	public static String decodeWithBase64(String key, String encodeData) {
		try {
			// 生成密钥
			SecretKey deskey = new SecretKeySpec(toKeyBytes(key), Algorithm);// 解密
			Cipher c1 = Cipher.getInstance(Algorithm);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			return new String(c1.doFinal(Base64.decode(encodeData)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String randomKey() {
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < LENGTH; i++) {
			sb.append(BASE.charAt(random.nextInt(BASE.length())));
		}
		return sb.toString();
	}	
}
