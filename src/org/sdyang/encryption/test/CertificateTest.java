package org.sdyang.encryption.test;

import org.sdyang.encryption.utils.CertificateUtils;

public class CertificateTest {

	public static void main(String[] args) {

		CertificateUtils  util = new CertificateUtils();
		
		util.genkey();
		util.export();
		util.coverToPfx();
		

	}

}
