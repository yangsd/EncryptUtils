package org.sdyang.encryption.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5消息摘要算法
 * 
 * @author sdyang
 * @date 2014-11-1 下午2:22:30
 */
public class MD5 {
	/**
	 * 散列值为128位
	 * 
	 * @param rawData
	 * @return
	 * @author sdyang
	 * @date 2015年4月26日 下午11:23:05
	 */
	public static String sign(String rawData) {
		return DigestUtils.md5Hex(rawData).toLowerCase();

	}
}
