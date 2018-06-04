package encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

public class MD5 {
	public static final String MD5 = "MD5";
	public static final String UTF8 = "UTF-8";

	public static String EncryptionStr32(String str, String algorithm, String charset) {
		byte[] bytes = EncryptionStrBytes(str, algorithm, charset);
		return BytesConvertToHexString(bytes);
	}

	public static String EncryptionStr32(String str, String algorithm) {
		return EncryptionStr32(str, algorithm, "");
	}

	public static String EncryptionStr16(String str, String algorithm, String charset) {
		return EncryptionStr32(str, algorithm, charset).substring(8, 24);
	}

	public static String EncryptionStr16(String str, String algorithm) {
		return EncryptionStr32(str, algorithm, "").substring(8, 24);
	}

	public static byte[] EncryptionStrBytes(String str, String algorithm, String charset) {
		byte[] bytes = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			if (null == charset || "".equals(charset)) {
				md.update(str.getBytes());
			} else {
				md.update(str.getBytes(charset));
			}
			bytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return null == bytes ? null : bytes;
	}

	public static String BytesConvertToHexString(byte[] bytes) {
		StringBuffer sb = new StringBuffer();
		for (byte aByte : bytes) {
			String s = Integer.toHexString(0xff & aByte);
			if (s.length() == 1) {
				sb.append("0" + s);
			} else {
				sb.append(s);
			}
		}
		return sb.toString();
	}

	public static String createVerificationCodeByPassword(String password) {
		int fristIndex = avgRandom(0, password.length() - 7);
		String str = EncryptionStr32(password, MD5, UTF8);
		String ss = RSA.RSAEncryption(str);
		return ss.substring(fristIndex, fristIndex + 6);
	}

	public static int avgRandom(int min, int max) {
		if (min > max) {
			int temp = max;
			max = min;
			min = temp;
		}
		int rNum = ThreadLocalRandom.current().nextInt(max - min + 1);
		return rNum + min;
	}
}
