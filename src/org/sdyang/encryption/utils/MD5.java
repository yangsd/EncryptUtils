package org.sdyang.encryption.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5��ϢժҪ�㷨
 * 
 * @author sdyang
 * @date 2014-11-1 ����2:22:30
 */
public class MD5 {
	/**
	 * ɢ��ֵΪ128λ
	 * 
	 * @param rawData
	 * @return
	 * @author sdyang
	 * @date 2015��4��26�� ����11:23:05
	 */
	public static String sign(String rawData) {
		return DigestUtils.md5Hex(rawData).toLowerCase();

	}
}
