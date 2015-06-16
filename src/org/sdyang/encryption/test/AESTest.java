package org.sdyang.encryption.test;

import java.io.UnsupportedEncodingException;

import org.sdyang.encryption.utils.AES;
import org.sdyang.encryption.utils.HexUtils;

public class AESTest {

	public static void main(String[] args) {

		String key = "123456";
		String data = "20150426��˼��abc";

		try {
			byte[] encodeData = AES.encryptAES(data, key);

			String encodeStr = HexUtils.parseByte2HexStr(encodeData);
			System.out.println("���ܺ�����ݣ�" + encodeStr);

			byte[] decryptByte = HexUtils.parseHexStr2Byte(encodeStr);
			byte[] decodeData = AES.decryptAES(decryptByte, key);

			System.out.println("���ܺ�����ݣ�" + new String(decodeData, "utf-8"));
		} catch (UnsupportedEncodingException e) {

			e.printStackTrace();
		}

	}

}
