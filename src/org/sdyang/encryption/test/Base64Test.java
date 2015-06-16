package org.sdyang.encryption.test;

import java.io.UnsupportedEncodingException;

import org.sdyang.encryption.utils.Base64;

public class Base64Test {

	public static void main(String[] args) {

		String data = "杨思丹";
		String encodestr = "";
		String decodestr = "";
		Base64 base64 = new Base64();
		try {
			encodestr = base64.encode(data.getBytes("UTF-8"));

			decodestr = new String(base64.decode(encodestr),"UTF-8");

			System.out.println("原数据："+data);
			System.out.println("编码后的数据：" + encodestr);
			System.out.println("解码后的数据：" + decodestr);

		} catch (UnsupportedEncodingException e) {
			System.out.println("Base64编码失败：" + e.toString());
			e.printStackTrace();
		}

	}

}
