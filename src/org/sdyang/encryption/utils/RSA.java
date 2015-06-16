package org.sdyang.encryption.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.util.Enumeration;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import sun.misc.BASE64Decoder;

/**
 * RSA�ǶԳƼ���
 * 
 * @date   2014-11-1 ����2:25:58
 */
public class RSA {

	/**
	 * ǩ���㷨
	 */
	public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	// RSA��Կ����
	public static String encodeWithBase64(String keyFilePath, String rawData) {
		try {
			PublicKey pubKey = getPublicKeyFromX509(keyFilePath);
			Cipher c = Cipher.getInstance(pubKey.getAlgorithm());
			c.init(Cipher.ENCRYPT_MODE, pubKey);
			return Base64.encode(c.doFinal(rawData.getBytes("GBK")));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	// ˽ԿRSA����
	public static String decodeWithBase64(String keyFilePath, String password,
			String encodeData) {
		try {
			PrivateKey key = getPrivateKey(keyFilePath, password);
			Cipher c = Cipher.getInstance(key.getAlgorithm());
			c.init(Cipher.DECRYPT_MODE, key);
			return new String(c.doFinal(Base64.decode(encodeData)));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	// ʹ��˽Կǩ��
	public static String sign(String keyFilePath, String password,
			String rawData) {
		try {
			PrivateKey privateK = getPrivateKey(keyFilePath, password);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initSign(privateK);
			signature.update(rawData.getBytes("GBK"));
			return Base64.encode(signature.sign());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	// ʹ�ù�Կ��ǩ
	public static boolean verify(String keyFilePath, String rawData, String sign) {
		try {
			PublicKey publicK = getPublicKeyFromX509(keyFilePath);
			Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
			signature.initVerify(publicK);
			signature.update(rawData.getBytes("GBK"));
			return signature.verify(Base64.decode(sign));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return false;

	}

	public static String keyToString(String keyFilePath) {
		try {
			PublicKey pk = getPublicKeyFromX509(keyFilePath);
			return Base64.encode(pk.getEncoded());
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}
	public static String encodeByStringKey(String keyStr,String rawData){
		PublicKey pubKey = getPubKey(keyStr);
		try {
			Cipher c = Cipher.getInstance(pubKey.getAlgorithm());
			c.init(Cipher.ENCRYPT_MODE, pubKey);
			return Base64.encode(c.doFinal(rawData.getBytes()));
		} catch (InvalidKeyException e) {
			System.out.println("��Ч��key");
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
		} catch (NoSuchPaddingException e) {
			System.out.println(e.toString());
		} catch (IllegalBlockSizeException e) {
			System.out.println(e.toString());
		} catch (BadPaddingException e) {
			System.out.println(e.toString());
		}
		return null;
	}
	private static PublicKey getPubKey(String pubKey) {
		PublicKey publicKey = null;
		try {
			java.security.spec.X509EncodedKeySpec bobPubKeySpec = new java.security.spec.X509EncodedKeySpec(new BASE64Decoder().decodeBuffer(pubKey));
			// RSA�ԳƼ����㷨
			java.security.KeyFactory keyFactory;
			keyFactory = java.security.KeyFactory.getInstance("RSA");
			// ȡ��Կ�׶���
			publicKey = keyFactory.generatePublic(bobPubKeySpec);
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.toString());
		} catch (InvalidKeySpecException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return publicKey;
	 }
	private static PublicKey getPublicKeyFromX509(String keyFilePath)
			throws Exception {
		InputStream fin = getInputStream(keyFilePath);
		CertificateFactory f = CertificateFactory.getInstance("X.509");
		X509Certificate certificate = (X509Certificate) f
				.generateCertificate(fin);
		return certificate.getPublicKey();
	}

	private static InputStream getInputStream(String keyFilePath) {
		
		if(keyFilePath==null||keyFilePath.isEmpty()){
			System.out.println("֤��·��Ϊ�գ�");
		}
		try {
			return new FileInputStream(new File(keyFilePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("�Ҳ���֤���ļ�", e);
		}
	}

	private static PrivateKey getPrivateKey(String keyFilePath, String password)
			throws Exception {
		KeyStore store = KeyStore.getInstance("pkcs12");
		InputStream is = getInputStream(keyFilePath);
		store.load(is, password.toCharArray());
		Enumeration<String> e = store.aliases();
		if (e.hasMoreElements()) {
			String alias = e.nextElement();
			return (PrivateKey) store.getKey(alias, password.toCharArray());
		} else
			return null;

	}	
}

