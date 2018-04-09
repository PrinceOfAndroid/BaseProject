package com.taos.up.baseproject.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密工具类
 * @author wangfucai
 */
public class EncryptUtil {
	
	public static String getMD5(String str) {
		return encode(str, "MD5", "UTF-8").toLowerCase();
	}
	
	public static String getMD5(String str, String encode) {
		return encode(str, "MD5", encode).toLowerCase();
	}

	public static String getSHA1(String str, String encode) {
		return encode(str, "SHA-1", encode).toLowerCase();
	}

	private static String encode(String str, String type, String encode) {
		try {
			MessageDigest alga = MessageDigest.getInstance(type);
			alga.update(str.getBytes(encode));
			byte[] digesta = alga.digest();
			return byte2hex(digesta);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if ((b.length % 2) != 0)
			throw new IllegalArgumentException("长度不是偶数");
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[n / 2] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	/**
	 * 加密方法 3DES加密解密的工具类
	 * @param str 需要加密的字符串
	 * @return
	 */
	public static String get3DES(String str)
	{
		byte[] encryptResult=encryptMode(str,str.getBytes());
		return new String(encryptResult);
	}
	/**
	 * @param  str 需要加密的字符串
	 * @param src 源数据的字节数组
	 * @return
	 */
	public static byte[] encryptMode(String str, byte[] src) {
		try 
		{
			String Algorithm="DESede";
			SecretKey deskey = new SecretKeySpec(build3DesKey(str), Algorithm); // 生成密钥
			Cipher c1 = Cipher.getInstance(Algorithm); // 实例化负责加密/解密的Cipher工具类
			c1.init(Cipher.ENCRYPT_MODE, deskey); // 初始化为加密模式
			return c1.doFinal(src);
		} catch (java.security.NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		} catch (javax.crypto.NoSuchPaddingException e2) {
			e2.printStackTrace();
		} catch (Exception e3) {
			e3.printStackTrace();
		}
		return null;
	}
	
	/*
	 * 根据字符串生成密钥字节数组
	 * @param keyStr 密钥字符串
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] build3DesKey(String keyStr)
			throws UnsupportedEncodingException {
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
		byte[] temp = keyStr.getBytes("GB2312"); // 将字符串转成字节数组
		if (key.length > temp.length) {
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}
}
