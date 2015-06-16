package org.sdyang.encryption.test;

import org.sdyang.encryption.utils.SHA;

public class SHATest {

	public static void main(String[] args) {
		
		String data = "ÑîË¼µ¤";
		
		System.out.println("SHA1  :"+SHA.sha1(data));
		
		System.out.println("SHA256:"+SHA.sha256(data));
		
		System.out.println("SHA512:"+SHA.sha512(data));
	}

}
