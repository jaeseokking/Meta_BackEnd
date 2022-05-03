package com.real.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

public class AES256 {
	
	// DEFAULTS
		private static final Charset UTF8 = Charset.forName("UTF-8");
		private static final String DEFAULT_CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
		private static byte[] DEFAULT_IV = new byte[16];
		static {
			Arrays.fill(DEFAULT_IV, (byte) 0);
		}

		// Cipher Init Parameters
		String cipherAlgorithm = null;
		String secretKey;
		byte[] iv;

		// Cipher
		SecretKeySpec secretSpec = null;
		IvParameterSpec ivSpec = null;

		public String getSecretKey() {
			return secretKey;
		}

		public void setSecretKey(String secretKey) {
			this.secretKey = secretKey;
		}

		public AES256() {
			this(DEFAULT_CIPHER_ALGORITHM, "", DEFAULT_IV);
		}

		public AES256(String secretKey) {
			this(DEFAULT_CIPHER_ALGORITHM, secretKey, DEFAULT_IV);
		}

		public AES256(String cipherAlgorithm, String secretKey, byte[] iv) {
			this.secretKey = secretKey;
			this.cipherAlgorithm = cipherAlgorithm;
			this.iv = iv;
		}

		public Cipher getEncryptChiper() throws Exception {
			try {
				// Init Secret
				this.secretSpec = new SecretKeySpec(this.secretKey.getBytes(),
						"AES");
				// Init IV
				this.ivSpec = new IvParameterSpec(this.iv);
				Cipher cipher = Cipher.getInstance(this.cipherAlgorithm);
				cipher.init(Cipher.ENCRYPT_MODE, this.secretSpec, this.ivSpec);
				return cipher;
			} catch (Exception ex) {
				throw ex;
			}
		}

		public Cipher getDecryptChiper() throws Exception {
			try {
				// Init Secret
				this.secretSpec = new SecretKeySpec(this.secretKey.getBytes(),
						"AES");
				// Init IV
				this.ivSpec = new IvParameterSpec(this.iv);
				Cipher cipher = Cipher.getInstance(this.cipherAlgorithm);
				cipher.init(Cipher.DECRYPT_MODE, this.secretSpec, this.ivSpec);
				return cipher;
			} catch (Exception ex) {
				throw ex;
			}
		}

		public byte[] encryptString(String plainString) {
			return encryptString(plainString, UTF8);
		}

		public byte[] encryptString(String plainString, Charset charset) {

			byte[] cipherBytes = null;
			try {
				Cipher cipher = getEncryptChiper();
				byte[] plainBytes = plainString.getBytes(UTF8);
				cipherBytes = cipher.doFinal(plainBytes);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return cipherBytes; 
		}

		public String encryptStringToBase64(String plainString) {
			return encryptStringToBase64(plainString, UTF8);
		}

		public String encryptStringToBase64(String plainString, Charset charset) {
			byte[] cipherBytes = encryptString(plainString, charset);
			return Base64Utils.base64Encode(cipherBytes);
		}

		public String decryptBytes(byte[] chiperBytes) {
			return decryptBytes(chiperBytes, UTF8);
		}

		public String decryptBytes(byte[] chiperBytes, Charset charset) {
			if (chiperBytes == null || chiperBytes.length == 0) {
				return null;
			}
			String plainString = null;
			try {
				Cipher cipher = getDecryptChiper();
				byte[] plainBytes = cipher.doFinal(chiperBytes);
				plainString = new String(plainBytes);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return plainString;
		}

		public String decryptBase64String(String cipherBase64String) {
			// if(StringUtil.isNull(cipherBase64String)) {
			if (StringUtil.isNull(cipherBase64String)) {
				return null;
			}
			byte[] cipherBytes = Base64Utils.base64Decode(cipherBase64String);
			String plainString = decryptBytes(cipherBytes);
			plainString = plainString.trim();
			return plainString;
		}

		public static String decrypt(String key, String data) throws Exception {
			AES256 crypto = new AES256();
			crypto.setSecretKey(key);
			return crypto.decryptBase64String(data);
		}

		public static String encrypt(String key, String data) throws Exception {
			AES256 crypto = new AES256();
			crypto.setSecretKey(key);
			return crypto.encryptStringToBase64(data);
		}

		public static void main(String[] args) throws Exception {
		}
	
	
}
