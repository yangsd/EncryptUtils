package org.sdyang.encryption.test;

import org.sdyang.encryption.utils.DES;

public class DESTest {

	public static void main(String[] args) {
		
		String data = "��˼��";
		DES des = new DES();
		String key = des.randomKey();
		System.out.println("��Կ��"+key);
		
		String encodeData = des.encodeWithBase64(key,data);
		System.out.println("���ܺ�����ݣ�"+encodeData);
		
		String decodeData = des.decodeWithBase64(key, encodeData);
		System.out.println("���ܺ�����ݣ�"+decodeData);

	}

}
