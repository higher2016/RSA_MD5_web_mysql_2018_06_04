package my;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA加密工具类
 *
 * @author kaiser·von·d
 * @version 2018/4/25
 */
public class RSA {

	public static final String RSA_ECB_PKCS1_PADDING = "RSA/ECB/PKCS1Padding";

	public static final int KEY_SIZE_1024 = 1024;
	public static final String PUBLIC_KEY = "publicKey";
	public static final String PRIVATE_KEY = "privateKey";
	public static final String MODULUS = "modulus";

	private RSA() {
	}

	private static final String ALGORITHM = "RSA";

	// 获取操作 钥匙对操作

	/**
	 * 生成密钥对
	 */
	public static Map<String, String> generateKeyPair() {
		return generateKeyPair(KEY_SIZE_1024);
	}

	/**
	 * 生成指定长度的密钥对。最低512位！
	 */
	public static Map<String, String> generateKeyPair(int keySize) {
		try {
			if (keySize < 512)
				throw new IllegalArgumentException("非法的参数！keySize :" + keySize);

			/** RSA算法要求有一个可信任的随机数源 */
			SecureRandom sr = new SecureRandom();
			/** 为RSA算法创建一个KeyPairGenerator对象 */
			System.out.println();
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
			kpg.initialize(keySize, sr);
			/** 生成密匙对 */
			KeyPair kp = kpg.generateKeyPair();
			/** 得到公钥 */
			String pub = getBase64PublicKey(kp.getPublic());
			/** 得到私钥 */
			String pri = getBase64PrivateKey(kp.getPrivate());
			Map<String, String> map = new HashMap<>();
			map.put(PUBLIC_KEY, pub);
			map.put(PRIVATE_KEY, pri);
			RSAPublicKey rsp = (RSAPublicKey) kp.getPublic();
			BigInteger bint = rsp.getModulus();
			byte[] b = bint.toByteArray();

			String retValue = Base64.encodeBase64String(b);
			map.put(MODULUS, retValue);
			return map;
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to generate key pair!", e);
		}
	}

	/**
	 * 对Base64加密过的公钥进行解码获取
	 *
	 * @param base64PublicKey
	 *            加密后的公钥
	 * @return 解密后的公钥
	 */
	public static PublicKey getPublicKey(String base64PublicKey) {
		try {
			X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(base64PublicKey));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			PublicKey publicKey = keyFactory.generatePublic(keySpec);
			return publicKey;
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to get public key!", e);
		}
	}

	/**
	 * 根据模数和指数获取公钥
	 *
	 * @param modulus
	 *            模数
	 * @param exponent
	 *            指数
	 * @return 未被Base64加密的公钥
	 */
	public static PublicKey getPublicKey(BigInteger modulus, BigInteger exponent) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(modulus, exponent);
			return keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to get public key!", e);
		}
	}

	/**
	 * 对公钥进行Base64加密
	 *
	 * @param publicKey
	 *            原始公钥
	 * @return Base64加密的公钥
	 */
	public static String getBase64PublicKey(PublicKey publicKey) {
		return Base64.encodeBase64String(publicKey.getEncoded());
	}

	/**
	 * 对Base64加密过的公钥进行解码获取
	 *
	 * @param base64PrivateKey
	 *            加密后的私钥
	 * @return 解密后的公钥
	 */
	public static PrivateKey getPrivateKey(String base64PrivateKey) {
		try {
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(base64PrivateKey));
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
			return privateKey;
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to get private key!", e);
		}
	}

	/**
	 * 根据模数和指数获取私钥
	 *
	 * @param modulus
	 *            模数
	 * @param exponent
	 *            指数
	 * @return 未被Base64加密的私钥
	 */
	public static PrivateKey getPrivateKey(BigInteger modulus, BigInteger exponent) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(modulus, exponent);
			return keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			throw new IllegalArgumentException("Failed to get private key!", e);
		}
	}

	/**
	 * 对私钥进行Base64加密
	 *
	 * @param privateKey
	 *            原始私钥
	 * @return Base64加密后的私钥
	 */
	public static String getBase64PrivateKey(PrivateKey privateKey) {
		return Base64.encodeBase64String(privateKey.getEncoded());
	}

	// 私钥解密，公钥加密

	/**
	 * 对数据进行RSA加密
	 *
	 * @param data
	 *            要加密的数据
	 * @param publicKey
	 *            原生公钥
	 * @return 加密后的byte数组
	 */
	public static byte[] encryptByPubAsArr(String data, PublicKey publicKey) {
		return encryptByPubAsArr(data.getBytes(), publicKey);
	}

	/**
	 * 对数据进行RSA加密
	 *
	 * @param data
	 *            要加密的数据
	 * @param publicKey
	 *            原生公钥
	 * @return 加密后的byte数组
	 */
	public static byte[] encryptByPubAsArr(byte[] data, PublicKey publicKey) {
		throwNullPointException(data);
		throwNullPointException(publicKey);
		try {
			Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);

			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new IllegalArgumentException("Encrypt failed!", e);
		}
	}

	/**
	 * 对数据进行RSA加密
	 *
	 * @param data
	 *            要加密的数据
	 * @param base64PublicKey
	 *            Base64加密后的公钥
	 * @return 加密后的byte数组
	 */
	public static byte[] encryptByPubAsArr(String data, String base64PublicKey) {
		return encryptByPubAsArr(data, getPublicKey(base64PublicKey));
	}

	/**
	 * 对数据进行RSA加密
	 *
	 * @param data
	 *            要加密的数据
	 * @param publicKey
	 *            原生公钥对象
	 * @return 被Base64加密后的内容
	 */
	public static String encryptByPubkeyStr(String data, PublicKey publicKey) {
		return Base64.encodeBase64String(encryptByPubAsArr(data, publicKey));
	}

	/**
	 * 对数据进行RSA加密
	 *
	 * @param data
	 *            要加密的数据
	 * @param base64PublicKey
	 *            Base64加密后的公钥
	 * @return 被Base64加密后的内容
	 */
	public static String encryptByPubkeyStr(String data, String base64PublicKey) {
		return Base64.encodeBase64String(encryptByPubAsArr(data, getPublicKey(base64PublicKey)));
	}

	/**
	 * 对数据进行RSA解密
	 *
	 * @param data
	 *            解密数据
	 * @param privateKey
	 *            原生私私钥
	 * @return 解密后的报文
	 */
	public static String decryptByPrivate(byte[] data, PrivateKey privateKey) {
		throwNullPointException(data);
		throwNullPointException(privateKey);
		return new String(decryptByPrivateArr(data, privateKey));
	}

	public static byte[] decryptByPrivateArr(byte[] data, PrivateKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return cipher.doFinal(data);
		} catch (Exception e) {
			throw new IllegalArgumentException("Decrypt failed!", e);
		}
	}

	/**
	 * 对数据进行RSA解密
	 *
	 * @param data
	 *            解密数据
	 * @param base64PrivateKey
	 *            经过Base64位加密的私钥
	 * @return 解密后的报文
	 */
	public static String decryptByPrivate(byte[] data, String base64PrivateKey) {
		return decryptByPrivate(data, getPrivateKey(base64PrivateKey));
	}

	/**
	 * 对数据进行RSA解密
	 *
	 * @param data
	 *            解密数据
	 * @param privateKey
	 *            原生私私钥
	 * @return 解密后的报文
	 */
	public static String decryptByPrivate(String data, PrivateKey privateKey) {
		return decryptByPrivate(Base64.decodeBase64(data), privateKey);
	}

	/**
	 * 对数据进行RSA解密
	 *
	 * @param data
	 *            base64加密过的数据
	 * @param base64PrivateKey
	 *            经过Base64位加密的私钥
	 * @return 解密后的报文
	 */
	public static String decryptByPrivate(String base64Data, String base64PrivateKey) {
		return decryptByPrivate(Base64.decodeBase64(base64Data), getPrivateKey(base64PrivateKey));
	}

	// 私钥加密，公钥解密

	/**
	 * 对数据进行RSA加密
	 *
	 * @param data
	 *            要加密的数据
	 * @param privateKey
	 *            原生私钥
	 * @return 加密后的byte数组
	 */
	public static byte[] encryptByPrivateAsArr(String data, PrivateKey privateKey) {
		throwNullPointException(data);
		throwNullPointException(privateKey);
		try {
			Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			return cipher.doFinal(data.getBytes());
		} catch (Exception e) {
			throw new IllegalArgumentException("Encrypt failed!", e);
		}
	}

	/**
	 * 对数据进行RSA加密
	 *
	 * @param data
	 *            要加密的数据
	 * @param base64PrivateKey
	 *            Base64加密后的私钥
	 * @return 加密后的byte数组
	 */
	public static byte[] encryptByPrivateAsArr(String data, String base64PrivateKey) {
		return encryptByPrivateAsArr(data, getPrivateKey(base64PrivateKey));
	}

	/**
	 * 对数据进行RSA加密
	 *
	 * @param data
	 *            要加密的数据
	 * @param privateKey
	 *            原生公钥对象
	 * @return 被Base64加密后的内容
	 */
	public static String encryptByPrivateAsStr(String data, PrivateKey privateKey) {
		return Base64.encodeBase64String(encryptByPrivateAsArr(data, privateKey));
	}

	/**
	 * 对数据进行RSA加密
	 *
	 * @param data
	 *            要加密的数据
	 * @param base64PrivateKey
	 *            Base64加密后的私钥
	 * @return 被Base64加密后的内容
	 */
	public static String encryptByPrivateAsStr(String data, String base64PrivateKey) {
		return Base64.encodeBase64String(encryptByPrivateAsArr(data, getPrivateKey(base64PrivateKey)));
	}

	/**
	 * 对数据进行RSA解密
	 *
	 * @param data
	 *            解密数据
	 * @param publicKey
	 *            原生公钥
	 * @return 解密后的报文
	 */
	public static String decryptByPublicKey(byte[] data, PublicKey publicKey) {
		throwNullPointException(data);
		throwNullPointException(publicKey);
		try {
			Cipher cipher = Cipher.getInstance(RSA_ECB_PKCS1_PADDING);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			return new String(cipher.doFinal(data));
		} catch (Exception e) {
			throw new IllegalArgumentException("Decrypt failed!", e);
		}
	}

	/**
	 * 对数据进行RSA解密
	 *
	 * @param data
	 *            解密数据
	 * @param base64PublicKey
	 *            经过Base64位加密的公钥
	 * @return 解密后的报文
	 */
	public static String decryptByPublicKey(byte[] data, String base64PublicKey) {
		return decryptByPublicKey(data, getPublicKey(base64PublicKey));
	}

	/**
	 * 对数据进行RSA解密
	 *
	 * @param data
	 *            解密数据
	 * @param publicKey
	 *            原生公钥
	 * @return 解密后的报文
	 */
	public static String decryptByPublicKey(String data, PublicKey publicKey) {
		return decryptByPublicKey(Base64.decodeBase64(data), publicKey);
	}

	/**
	 * 对数据进行RSA解密
	 *
	 * @param data
	 *            解密数据
	 * @param base64PublicKey
	 *            经过Base64位加密的公钥
	 * @return 解密后的报文
	 */
	public static String decryptByPublicKey(String data, String base64PublicKey) {
		return decryptByPublicKey(Base64.decodeBase64(data), getPublicKey(base64PublicKey));
	}

	private static void throwNullPointException(Object obj) {
		if (null == obj) {
			throw new NullPointerException();
		}
	}

	public static void main(String[] args) throws Exception {
		String aa = "aaa";
		System.out.println("aa " + aa);
		Map<String, String> keyPair = RSA.generateKeyPair();
		String cipherData = RSA.encryptByPrivateAsStr(aa, keyPair.get(PRIVATE_KEY));
		System.out.println(cipherData);
		String original = RSA.decryptByPublicKey(cipherData, keyPair.get(PUBLIC_KEY));
		System.out.println("source : " + original);
	}

	public static final String jiami(String data) {
		String cipherData = RSA.encryptByPrivateAsStr(data, RSA.generateKeyPair().get(PRIVATE_KEY));
		return cipherData;
	}
}
