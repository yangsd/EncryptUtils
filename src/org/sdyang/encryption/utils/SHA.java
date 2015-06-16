package org.sdyang.encryption.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * SHA��ȫɢ���㷨
 * 
 * @author sdyang
 * @date 2015��4��26�� ����11:05:57
 */
public class SHA {

	/**
	 * ��ϣֵΪ160λ
	 * 
	 * @param rawData
	 * @return
	 * @author sdyang
	 * @date 2015��4��26�� ����11:21:38
	 */
	public static String sha1(String rawData) {
		return DigestUtils.sha1Hex(rawData).toLowerCase();
	}

	/**
	 * ��ϣֵΪ256λ
	 * 
	 * @param rawData
	 * @return
	 * @author sdyang
	 * @date 2015��4��26�� ����11:21:13
	 */
	public static String sha256(String rawData) {
		return DigestUtils.sha256Hex(rawData).toLowerCase();
	}

	/**
	 * ��ϣֵΪ512λ
	 * 
	 * @param rawData
	 * @return
	 * @author sdyang
	 * @date 2015��4��26�� ����11:21:25
	 */
	public static String sha512(String rawData) {
		return DigestUtils.sha512Hex(rawData).toLowerCase();
	}

}
