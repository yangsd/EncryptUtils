package org.sdyang.encryption.test;

import java.io.UnsupportedEncodingException;

import org.sdyang.encryption.utils.Base64;

public class Base64Test {

	public static void main(String[] args) {

		String data = "��˼��";
		String encodestr = "";
		String decodestr = "";
		Base64 base64 = new Base64();
		try {
			encodestr = base64.encode(data.getBytes("UTF-8"));

			decodestr = new String(base64.decode(encodestr),"UTF-8");

			System.out.println("ԭ���ݣ�"+data);
			System.out.println("���������ݣ�" + encodestr);
			System.out.println("���������ݣ�" + decodestr);

		} catch (UnsupportedEncodingException e) {
			System.out.println("Base64����ʧ�ܣ�" + e.toString());
			e.printStackTrace();
		}

	}

}
