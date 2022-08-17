package com.konecta.middlewareweb.negocio.util;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncriptadoUtil {
	
	private static final String KEY = "Wapi2020Provider"; // 128 bit key
	private static final String INIT_VECTOR = "WapiPro2020Keyse";// 16 bytes IV
	private static final String ALGORITHM = "AES";
	private static final Logger LOG = LoggerFactory.getLogger(EncriptadoUtil.class);
	
	private EncriptadoUtil() {
		throw new UnsupportedOperationException("This class is an utility class");
	}
	
	
	public static String encrypt(String value) {

		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());

			return Base64.encodeBase64String(encrypted);
		} catch (Exception e) {
			LOG.error("Se ha producido un error al encriptar la contraseña. ", e);
		}

		return null;

	}
	
	public static String decrypt(String encrypted) {

		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception e) {
			LOG.error("Se ha producido un error al desencriptar la contraseña. ", e);
		}

		return null;
	}

}
