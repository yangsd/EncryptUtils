package org.sdyang.encryption.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * SHA安全散列算法
 * 
 * @author sdyang
 * @date 2015年4月26日 下午11:05:57
 */
public class SHA {

	/**
	 * 哈希值为160位
	 * 
	 * @param rawData
	 * @return
	 * @author sdyang
	 * @date 2015年4月26日 下午11:21:38
	 */
	public static String sha1(String rawData) {
		return DigestUtils.sha1Hex(rawData).toLowerCase();
	}

	/**
	 * 哈希值为256位
	 * 
	 * @param rawData
	 * @return
	 * @author sdyang
	 * @date 2015年4月26日 下午11:21:13
	 */
	public static String sha256(String rawData) {
		return DigestUtils.sha256Hex(rawData).toLowerCase();
	}

	/**
	 * 哈希值为512位
	 * 
	 * @param rawData
	 * @return
	 * @author sdyang
	 * @date 2015年4月26日 下午11:21:25
	 */
	public static String sha512(String rawData) {
		return DigestUtils.sha512Hex(rawData).toLowerCase();
	}

}
