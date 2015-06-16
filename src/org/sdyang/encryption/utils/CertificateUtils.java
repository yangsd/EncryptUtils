package org.sdyang.encryption.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;

/**
 * 证书工具类
 * 
 * @author sdyang
 * @date 2015年4月16日 下午4:41:10
 */
public class CertificateUtils {

	public static final String PKCS12 = "PKCS12";
	public static final String JKS = "JKS";

	public static final String JKS_KEYSTORE_FILE = "src/demo.keystore";
	//公钥证书
	public static final String PUBLIC_KEY_FILE_NAME = "src/public_key.cer";
	//私钥证书
	public static final String PFX_KEYSTORE_FILE = "src/private_key.pfx";
	//密钥库的密码
	public static final String KEYSTORE_PASSWORD = "123456";
	//私钥的密码
	public static final String PRIVATE_KEY_PASSWORD = "654321";

	public void execCommand(String[] arstringCommand) {
		for (int i = 0; i < arstringCommand.length; i++) {
			System.out.print(arstringCommand[i] + " ");
		}
		try {
			Runtime.getRuntime().exec(arstringCommand);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void execCommand(String arstringCommand) {
		try {
			Runtime.getRuntime().exec(arstringCommand);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * 生成密钥
	 * 
	 * @author sdyang
	 * @date   2015年4月26日 下午8:33:49
	 */
	public  void genkey() {
		String[] arstringCommand = new String[] {

		"cmd ", "/k",
				"start", // cmd Shell命令

				"keytool",
				"-genkey", // -genkey表示生成密钥
				"-validity", // -validity指定证书有效期(单位：天)，这里是365天
				"365",
				"-keysize",// 指定密钥长度
				"1024",
				"-alias", // -alias指定别名，这里是certificate
				"certificate",
				"-keyalg", // -keyalg 指定密钥的算法 (如 RSA DSA（如果不指定默认采用DSA）)
				"RSA",
				"-keystore", // -keystore指定存储位置，这里是src:/demo.keystore
				JKS_KEYSTORE_FILE,
				"-dname",// CN=(名字与姓氏), OU=(组织单位名称), O=(组织名称), L=(城市或区域名称),
							// ST=(州或省份名称), C=(单位的两字母国家代码)"
				"CN=(YSD), OU=(TCL), O=(TCL), L=(GD), ST=(HZ), C=(CN)",
				"-storepass", // 指定密钥库的密码(获取keystore信息所需的密码)
				KEYSTORE_PASSWORD, "-keypass",// 指定别名条目的密码(私钥的密码)
				PRIVATE_KEY_PASSWORD, "-v"// -v 显示密钥库中的证书详细信息
		};
		execCommand(arstringCommand);
	}

	/**
	 * 导出公钥证书
	 * 
	 * @author sdyang
	 * @date   2015年4月26日 下午8:33:23
	 */
	public void export() {

		String[] arstringCommand = new String[] {

		"cmd ", "/k", "start", // cmd Shell命令

				"keytool", "-export", // - export指定为导出操作
				"-keystore", // -keystore指定keystore文件，这里是d:/demo.keystore
				JKS_KEYSTORE_FILE, "-alias", // -alias指定别名，这里是certificate
				"certificate", "-file",// -file指向导出路径
				PUBLIC_KEY_FILE_NAME, "-storepass",// 指定密钥库的密码
				KEYSTORE_PASSWORD

		};
		execCommand(arstringCommand);

	}

	/**
	 * 
	 * 
	 * @author sdyang
	 * @date   2015年4月26日 下午8:33:11
	 */
	public void coverTokeyStore() {
		try {
			KeyStore inputKeyStore = KeyStore.getInstance("PKCS12");
			FileInputStream fis = new FileInputStream(PFX_KEYSTORE_FILE);
			char[] nPassword = null;

			if ((KEYSTORE_PASSWORD == null)
					|| KEYSTORE_PASSWORD.trim().equals("")) {
				nPassword = null;
			} else {
				nPassword = KEYSTORE_PASSWORD.toCharArray();
			}

			inputKeyStore.load(fis, nPassword);
			fis.close();

			KeyStore outputKeyStore = KeyStore.getInstance("JKS");

			outputKeyStore.load(null, KEYSTORE_PASSWORD.toCharArray());

			Enumeration enums = inputKeyStore.aliases();

			while (enums.hasMoreElements()) { 

				String keyAlias = (String) enums.nextElement();

				System.out.println("alias=[" + keyAlias + "]");

				if (inputKeyStore.isKeyEntry(keyAlias)) {
					Key key = inputKeyStore.getKey(keyAlias, nPassword);
					Certificate[] certChain = inputKeyStore
							.getCertificateChain(keyAlias);

					outputKeyStore.setKeyEntry(keyAlias, key,
							KEYSTORE_PASSWORD.toCharArray(), certChain);
				}
			}

			FileOutputStream out = new FileOutputStream(JKS_KEYSTORE_FILE);

			outputKeyStore.store(out, nPassword);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 从密钥库取出私钥，并生成私钥证书
	 * 
	 * @author sdyang
	 * @date   2015年4月26日 下午8:32:09
	 */
	public void coverToPfx() {
		try {
			KeyStore inputKeyStore = KeyStore.getInstance("JKS");
			FileInputStream fis = new FileInputStream(JKS_KEYSTORE_FILE);
			char[] keyStorePassword = null;
			char[] privatKeyPassword = null;
			
			//密钥库密码
			if ((KEYSTORE_PASSWORD == null)
					|| KEYSTORE_PASSWORD.trim().equals("")) {
				keyStorePassword = null;
				System.out.println("密钥库密码为空！");
			} else {
				keyStorePassword = KEYSTORE_PASSWORD.toCharArray();
			}
			
			//私钥密码
			if(PRIVATE_KEY_PASSWORD != null || PRIVATE_KEY_PASSWORD.trim().equals("")){
				privatKeyPassword = PRIVATE_KEY_PASSWORD.toCharArray();
			}else{
				System.out.println("私钥密码为空！");
			}

			inputKeyStore.load(fis, keyStorePassword);
			fis.close();

			KeyStore outputKeyStore = KeyStore.getInstance("PKCS12");

			outputKeyStore.load(null, keyStorePassword);

			Enumeration enums = inputKeyStore.aliases();

			while (enums.hasMoreElements()) { 

				String keyAlias = (String) enums.nextElement();

				System.out.println("alias=[" + keyAlias + "]");

				if (inputKeyStore.isKeyEntry(keyAlias)) {
					Key key = inputKeyStore.getKey(keyAlias, privatKeyPassword);
					Certificate[] certChain = inputKeyStore
							.getCertificateChain(keyAlias);

					outputKeyStore.setKeyEntry(keyAlias, key,
							privatKeyPassword, certChain);
				}
			}

			FileOutputStream out = new FileOutputStream(PFX_KEYSTORE_FILE);

			outputKeyStore.store(out, privatKeyPassword);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
