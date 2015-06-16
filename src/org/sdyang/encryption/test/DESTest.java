package org.sdyang.encryption.test;

import org.sdyang.encryption.utils.DES;

public class DESTest {

	public static void main(String[] args) {
		
		String data = "杨思丹";
		DES des = new DES();
		String key = des.randomKey();
		System.out.println("密钥："+key);
		
		String encodeData = des.encodeWithBase64(key,data);
		System.out.println("加密后的数据："+encodeData);
		
		String decodeData = des.decodeWithBase64(key, encodeData);
		System.out.println("解密后的数据："+decodeData);

	}

}
