package passwd;


import javax.crypto.Cipher;

import java.security.*;

import java.util.Base64;


public class RSA {

	public static String data="hello world";  
	  
    public static void main(String[] args) throws Exception {  
        // TODO Auto-generated method stub 
          
        KeyPair keyPair=genKeyPair(1024);  
          
        //��ȡ��Կ������base64��ʽ��ӡ����  
        PublicKey publicKey=keyPair.getPublic();          
        System.out.println("��Կ��"+new String(Base64.getEncoder().encode(publicKey.getEncoded())));  
          
        //��ȡ˽Կ������base64��ʽ��ӡ����  
        PrivateKey privateKey=keyPair.getPrivate();       
        System.out.println("˽Կ��"+new String(Base64.getEncoder().encode(privateKey.getEncoded())));  
          
        //��Կ����  
        byte[] encryptedBytes=encrypt(data.getBytes(), publicKey);    
        System.out.println("���ܺ�"+new String(encryptedBytes));  
          
        //˽Կ����  
        byte[] decryptedBytes=decrypt(encryptedBytes, privateKey);        
        System.out.println("���ܺ�"+new String(decryptedBytes));  
    }  
      
    //������Կ��  
    public static KeyPair genKeyPair(int keyLength) throws Exception{  
        KeyPairGenerator keyPairGenerator=KeyPairGenerator.getInstance("RSA");  
        keyPairGenerator.initialize(1024);        
        return keyPairGenerator.generateKeyPair();  
    }  
      
    //��Կ����  
    public static byte[] encrypt(byte[] content, PublicKey publicKey) throws Exception{  
        Cipher cipher=Cipher.getInstance("RSA");//javaĬ��"RSA"="RSA/ECB/PKCS1Padding"  
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);  
        return cipher.doFinal(content);  
    }  
      
    //˽Կ����  
    public static byte[] decrypt(byte[] content, PrivateKey privateKey) throws Exception{  
        Cipher cipher=Cipher.getInstance("RSA");  
        cipher.init(Cipher.DECRYPT_MODE, privateKey);  
        return cipher.doFinal(content);  
    }  
  

    }

