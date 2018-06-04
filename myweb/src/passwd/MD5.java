package passwd;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
	  public static final String MD5="MD5";
	  public static final String UTF8="UTF-8";
	  /**
	     *  ���ü����㷨�����ַ������� ת�ɳ���Ϊ32���ַ���
	     * @param str
	     * @param algorithm ���õļ����㷨
	     * @param charset ָ��ת��֮����ַ�������
	     * @return
	     */
	    public static String EncryptionStr32(String str, String algorithm,String charset) {
	        // ����֮�������ֽ�����
	        byte[] bytes = EncryptionStrBytes(str,algorithm,charset);
	        return BytesConvertToHexString(bytes);
	    }
	    /**
	     * ���ü����㷨�����ַ�������  ת�ɳ���Ϊ32���ַ���
	     * @param str   ��Ҫ���ܵ�����
	     * @param algorithm ���õļ����㷨
	     * @return �ֽ�����
	     */
	    public static String EncryptionStr32(String str, String algorithm) {
	        return EncryptionStr32(str,algorithm,"");
	    }
	    /**
	     *  ���ü����㷨�����ַ�������  ת�ɳ���Ϊ16���ַ���
	     * @param str
	     * @param algorithm ���õļ����㷨
	     * @param charset ָ��ת��֮����ַ�������
	     * @return
	     */
	    public static String EncryptionStr16(String str, String algorithm,String charset) {
	        return EncryptionStr32(str,algorithm,charset).substring(8,24);
	    }

	    /**
	     * ���ü����㷨�����ַ������� ת�ɳ���Ϊ16���ַ���
	     * @param str   ��Ҫ���ܵ�����
	     * @param algorithm ���õļ����㷨
	     * @return �ֽ�����
	     */
	    public static String EncryptionStr16(String str, String algorithm) {
	        return EncryptionStr32(str,algorithm,"").substring(8,24);
	    }

	    /**
	     * ���ü����㷨�����ַ�������
	     * @param str   ��Ҫ���ܵ�����
	     * @param algorithm ���õļ����㷨
	     * @param charset ָ��ת��֮����ַ�������
	     * @return �ֽ�����
	     */
	    public static byte[] EncryptionStrBytes(String str, String algorithm, String charset) {
	        // ����֮�������ֽ�����
	        byte[] bytes = null;
	        try {
	            // ��ȡMD5�㷨ʵ�� �õ�һ��md5����ϢժҪ
	            MessageDigest md = MessageDigest.getInstance(algorithm);
	            //���Ҫ���м���ժҪ����Ϣ
	            if(null==charset||"".equals(charset)) {
	                md.update(str.getBytes());
	            }else{
	                md.update(str.getBytes(charset));
	            }
	            //�õ���ժҪ
	            bytes = md.digest();
	        } catch (NoSuchAlgorithmException e) {
	            System.out.println("�����㷨: "+ algorithm +" ������: ");
	        } catch (UnsupportedEncodingException e) {
	            System.out.println("���ݼ���ָ���ı����ʽ��֧��: " + charset);
	        }
	        return null==bytes?null:bytes;
	    }
	    /**
	     * ���ֽ�����ת�����ַ�������
	     * @param bytes
	     * @return
	     */
	    public static String BytesConvertToHexString(byte [] bytes) {
	        StringBuffer sb = new StringBuffer();
	        for (byte aByte : bytes) {
	            String s=Integer.toHexString(0xff & aByte);
	            if(s.length()==1){
	                sb.append("0"+s);
	            }else{
	                sb.append(s);
	            }
	        }
	        return sb.toString();
	    }

	    //������������
	    public static void main(String[] args) {
	        String test1="01tSep";
	        String [] test={test1};
	        for (String s : test) {
	            String str=EncryptionStr32(s, MD5, UTF8);
	            System.out.println("���ݣ�" + s+" ����֮��Ľ��Ϊ��"+str+" �ַ�������Ϊ��"+str.length());
	            
	        }
	    }
	}

