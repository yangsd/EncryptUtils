package org.sdyang.encryption.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Enumeration;

/**
 * ֤�鹤����
 * 
 * @author sdyang
 * @date 2015��4��16�� ����4:41:10
 */
public class CertificateUtils {

	public static final String PKCS12 = "PKCS12";
	public static final String JKS = "JKS";

	public static final String JKS_KEYSTORE_FILE = "src/demo.keystore";
	//��Կ֤��
	public static final String PUBLIC_KEY_FILE_NAME = "src/public_key.cer";
	//˽Կ֤��
	public static final String PFX_KEYSTORE_FILE = "src/private_key.pfx";
	//��Կ�������
	public static final String KEYSTORE_PASSWORD = "123456";
	//˽Կ������
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
	 * ������Կ
	 * 
	 * @author sdyang
	 * @date   2015��4��26�� ����8:33:49
	 */
	public  void genkey() {
		String[] arstringCommand = new String[] {

		"cmd ", "/k",
				"start", // cmd Shell����

				"keytool",
				"-genkey", // -genkey��ʾ������Կ
				"-validity", // -validityָ��֤����Ч��(��λ����)��������365��
				"365",
				"-keysize",// ָ����Կ����
				"1024",
				"-alias", // -aliasָ��������������certificate
				"certificate",
				"-keyalg", // -keyalg ָ����Կ���㷨 (�� RSA DSA�������ָ��Ĭ�ϲ���DSA��)
				"RSA",
				"-keystore", // -keystoreָ���洢λ�ã�������src:/demo.keystore
				JKS_KEYSTORE_FILE,
				"-dname",// CN=(����������), OU=(��֯��λ����), O=(��֯����), L=(���л���������),
							// ST=(�ݻ�ʡ������), C=(��λ������ĸ���Ҵ���)"
				"CN=(YSD), OU=(TCL), O=(TCL), L=(GD), ST=(HZ), C=(CN)",
				"-storepass", // ָ����Կ�������(��ȡkeystore��Ϣ���������)
				KEYSTORE_PASSWORD, "-keypass",// ָ��������Ŀ������(˽Կ������)
				PRIVATE_KEY_PASSWORD, "-v"// -v ��ʾ��Կ���е�֤����ϸ��Ϣ
		};
		execCommand(arstringCommand);
	}

	/**
	 * ������Կ֤��
	 * 
	 * @author sdyang
	 * @date   2015��4��26�� ����8:33:23
	 */
	public void export() {

		String[] arstringCommand = new String[] {

		"cmd ", "/k", "start", // cmd Shell����

				"keytool", "-export", // - exportָ��Ϊ��������
				"-keystore", // -keystoreָ��keystore�ļ���������d:/demo.keystore
				JKS_KEYSTORE_FILE, "-alias", // -aliasָ��������������certificate
				"certificate", "-file",// -fileָ�򵼳�·��
				PUBLIC_KEY_FILE_NAME, "-storepass",// ָ����Կ�������
				KEYSTORE_PASSWORD

		};
		execCommand(arstringCommand);

	}

	/**
	 * 
	 * 
	 * @author sdyang
	 * @date   2015��4��26�� ����8:33:11
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
	 * ����Կ��ȡ��˽Կ��������˽Կ֤��
	 * 
	 * @author sdyang
	 * @date   2015��4��26�� ����8:32:09
	 */
	public void coverToPfx() {
		try {
			KeyStore inputKeyStore = KeyStore.getInstance("JKS");
			FileInputStream fis = new FileInputStream(JKS_KEYSTORE_FILE);
			char[] keyStorePassword = null;
			char[] privatKeyPassword = null;
			
			//��Կ������
			if ((KEYSTORE_PASSWORD == null)
					|| KEYSTORE_PASSWORD.trim().equals("")) {
				keyStorePassword = null;
				System.out.println("��Կ������Ϊ�գ�");
			} else {
				keyStorePassword = KEYSTORE_PASSWORD.toCharArray();
			}
			
			//˽Կ����
			if(PRIVATE_KEY_PASSWORD != null || PRIVATE_KEY_PASSWORD.trim().equals("")){
				privatKeyPassword = PRIVATE_KEY_PASSWORD.toCharArray();
			}else{
				System.out.println("˽Կ����Ϊ�գ�");
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
