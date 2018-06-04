package passwd;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
	  public static final String MD5="MD5";
	  public static final String UTF8="UTF-8";
	  /**
	     *  采用加密算法加密字符串数据 转成长度为32的字符串
	     * @param str
	     * @param algorithm 采用的加密算法
	     * @param charset 指定转化之后的字符串编码
	     * @return
	     */
	    public static String EncryptionStr32(String str, String algorithm,String charset) {
	        // 加密之后所得字节数组
	        byte[] bytes = EncryptionStrBytes(str,algorithm,charset);
	        return BytesConvertToHexString(bytes);
	    }
	    /**
	     * 采用加密算法加密字符串数据  转成长度为32的字符串
	     * @param str   需要加密的数据
	     * @param algorithm 采用的加密算法
	     * @return 字节数据
	     */
	    public static String EncryptionStr32(String str, String algorithm) {
	        return EncryptionStr32(str,algorithm,"");
	    }
	    /**
	     *  采用加密算法加密字符串数据  转成长度为16的字符串
	     * @param str
	     * @param algorithm 采用的加密算法
	     * @param charset 指定转化之后的字符串编码
	     * @return
	     */
	    public static String EncryptionStr16(String str, String algorithm,String charset) {
	        return EncryptionStr32(str,algorithm,charset).substring(8,24);
	    }

	    /**
	     * 采用加密算法加密字符串数据 转成长度为16的字符串
	     * @param str   需要加密的数据
	     * @param algorithm 采用的加密算法
	     * @return 字节数据
	     */
	    public static String EncryptionStr16(String str, String algorithm) {
	        return EncryptionStr32(str,algorithm,"").substring(8,24);
	    }

	    /**
	     * 采用加密算法加密字符串数据
	     * @param str   需要加密的数据
	     * @param algorithm 采用的加密算法
	     * @param charset 指定转化之后的字符串编码
	     * @return 字节数据
	     */
	    public static byte[] EncryptionStrBytes(String str, String algorithm, String charset) {
	        // 加密之后所得字节数组
	        byte[] bytes = null;
	        try {
	            // 获取MD5算法实例 得到一个md5的消息摘要
	            MessageDigest md = MessageDigest.getInstance(algorithm);
	            //添加要进行计算摘要的信息
	            if(null==charset||"".equals(charset)) {
	                md.update(str.getBytes());
	            }else{
	                md.update(str.getBytes(charset));
	            }
	            //得到该摘要
	            bytes = md.digest();
	        } catch (NoSuchAlgorithmException e) {
	            System.out.println("加密算法: "+ algorithm +" 不存在: ");
	        } catch (UnsupportedEncodingException e) {
	            System.out.println("数据加密指定的编码格式不支持: " + charset);
	        }
	        return null==bytes?null:bytes;
	    }
	    /**
	     * 把字节数组转化成字符串返回
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

	    //测试上述方法
	    public static void main(String[] args) {
	        String test1="01tSep";
	        String [] test={test1};
	        for (String s : test) {
	            String str=EncryptionStr32(s, MD5, UTF8);
	            System.out.println("数据：" + s+" 加密之后的结果为："+str+" 字符串长度为："+str.length());
	            
	        }
	    }
	}

