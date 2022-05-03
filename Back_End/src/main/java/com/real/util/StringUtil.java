package  com.real.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil  {
	
		
	public static String get2str(String str) {
		String buff = "";
		if(str.length()==1)
			buff = "0" + str;
		return buff;
	}

	public static String get2str(int str) {
		String buff = "";
		if(str<10)
			buff = "0" + str;
		else
			buff = "" + str;
		return buff;
	}


	public static String chkNull(String str) {
		return chkNull(str,"");
	}
	
	public static boolean isNull(String str){
		try {
			if(str.equals("") || str == null){
				return true;
			}
		} catch (Exception e) {
			return true;
		}
		return false;
	}
	
	public static String chkNull(String str, String rev) {
		if(str==null)
			return rev;
		else
			return str;
	}

	public static String chkNull(Object str) {
		if(str==null)
			return "";
		else
			return str.toString();
	}

	public static String Null2Zero(String str) {
		if(str==null||str.equals(""))
			return "0";
		else
			return str;
	}


	private static String arr2Str(String[] arr) {
		String buff="";
		if(arr!=null)
		{
		for(int i=0;i<arr.length;i++)
		{
			buff+="'" + arr[i] + "', ";
		}
		buff = buff.substring(0,buff.length()-2);
		}
		else
			buff = "";
		return buff;
	}


	public static String replaceAll(String originalString, String from, String to) {
		while ( originalString.indexOf(from) >= 0 ) {
			int i = originalString.indexOf(from);

			originalString = originalString.substring(0,i) + to + originalString.substring(i + from.length());
		}

		return originalString;
	}

	public static String dateAdd(String date, String type, int gap) {
		long addMillis = 0;
		if(type.equals("d"))
			addMillis = 1000 * 3600 * 24 * gap;
		else if (type.equals("h"))
			addMillis = 1000 * 3600 * gap;
		else if (type.equals("n"))
			addMillis = 1000 * 60 * gap;
		else if (type.equals("s"))
			addMillis = 1000 * gap;
		String str = "";
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(date.substring(0,4))
				,Integer.parseInt(date.substring(4,6))-1
				,Integer.parseInt(date.substring(6,8))
				);
		cal.setTimeInMillis(cal.getTimeInMillis()+addMillis);
		str += cal.get(Calendar.YEAR) + "-";
		str += get2str(cal.get(Calendar.MONTH)+1) + "-";
		str += get2str(cal.get(Calendar.DATE));
		return str;
	}

	public static String getYYYYMMDDHHNNSS() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		String now = sdf.format(date);
		return now;
	}

	public static String getYYYYMMDDHHNNSS(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		String now = sdf.format(date);
		return now;
	}

	public static String modText(String msg, int totalLen) {
		String a=msg; 	int b = totalLen;
		StringBuffer sb = new StringBuffer(msg);
		for(int i=(sb.length()); i<b; i++)			
			sb.append(" ");
		return sb.toString();
	}
	
	public static String modText(String msg, int len,String ex) {
		String rtn = msg; 	
		int msgLen =msg.length();
		for(int i=msgLen; i<len; i++)			
			rtn = ex + rtn;
		return rtn;
	}
	
	public static String[] parseStringByBytes(String raw, int len) {
		return parseStringByBytes(raw, len,"euc-kr");
	}
	
	public static String[] parseStringByBytes(String raw, int strLen,String CharSet) {        
        if (raw == null) return null;
        String[] ary = null;
        int len = strLen;
        try {
            byte[] rawBytes = raw.getBytes();
            int rawLength = rawBytes.length;
            
            int index = 0;
            int minus_byte_num = 0;
            int offset = 0;            
            int charCheck = 2;
            if(CharSet.toUpperCase().equals("UTF-8"))
            	charCheck = 3;
            if(rawLength > len) {
            	len = len-2;
                int aryLength = (rawLength / len) + (rawLength % len != 0 ? 1 : 0);
                ary = new String[aryLength];

                for(int i=0; i<aryLength; i++){
                    minus_byte_num = 0;
                    offset = len;
                    if(index + offset > rawBytes.length){
                        offset = rawBytes.length - index;
                    }
                    for(int j=0; j<offset; j++){                        
                        if((rawBytes[index + j] & 0x80) != 0){
                            minus_byte_num ++;
                        }
                    }                    
                    if(minus_byte_num % charCheck != 0){
                        offset -= minus_byte_num % charCheck;
                    }                    
                    ary[i] = getOstring(i) + new String(rawBytes, index, offset);                    
                    index += offset ;
                    
                }                
            } else {
                ary = new String[]{raw};
            }                
        } catch(Exception e) {
            
        }                    
        return ary;
    }
	
	public static String getOstring(int idx) {
		String[] oStr = "��,��,��,��,��,��,��,��,��,��,��,��".split(",");
		
		return oStr[idx];
	}
	
	public static boolean validator(String pattern, String str) {
		boolean check = true;
		
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);
		
		check = m.matches();
		
		return check;
	}
	
	public static String getFilePath(String userId, String baseDir) {
		String dirPath = baseDir;
		
		if ( userId != null ) {
			StringBuffer sb = new StringBuffer();
			sb.append(dirPath);
			if ( userId.length() <= 2 ) {
				userId += "_ultari";
			}
			
			if (userId.length() > 2) {
				sb.append(File.separator);
				sb.append(userId.substring(0, 2));
			}
			if (userId.length() > 4) {
				sb.append(File.separator);
				sb.append(userId.substring(2, 4));
			}
			if (userId.length() > 6) {
				sb.append(File.separator);
				sb.append(userId.substring(4, 6));
			}
			if (userId.length() > 8) {
				sb.append(File.separator);
				sb.append(userId.substring(6, 8));
			}
			
			sb.append(File.separator);
			sb.append(userId);
			
			dirPath = sb.toString();
			
			//File f = new File(dirPath);
			//f.mkdirs();
		} 
		
		return dirPath;
	}
	
	public static void main(String[] args) {
		String pattern = "^[a-z0-9]{6,8}$";
		String str = "test123";
		
		System.out.println("[INFO] " + StringUtil.validator(pattern, str));
	}
}
