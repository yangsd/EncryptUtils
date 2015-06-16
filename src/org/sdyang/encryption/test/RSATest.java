package org.sdyang.encryption.test;

import org.sdyang.encryption.utils.RSA;

public class RSATest {
//	public static String PRIVATE_KEY_PATH = "src/00010001020005.pfx";
//	public static String PUBLIC_KEY_PATH = "src/00010001020005.cer";
	public static String PRIVATE_KEY_PATH = "src/private_key.pfx";
	public static String PUBLIC_KEY_PATH = "src/public_key.cer";
	
	public static String PRIVATE_PSWD = "654321";
	
	public static void main(String[] args) {
		RSA rsa = new RSA();
		String data  = "杨思丹";
		String encodeData = rsa.encodeWithBase64(PUBLIC_KEY_PATH, data);
		System.out.println("加密后的数据："+encodeData);
		String decodeData = rsa.decodeWithBase64(PRIVATE_KEY_PATH, PRIVATE_PSWD, encodeData);
		System.out.println("解密后的数据："+decodeData);
	}

}
