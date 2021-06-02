package com.bayside.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ComMethodUtil {
	/**
	 * 
	 * <p>
	 * 方法名称：GetDomainName
	 * </p>
	 * <p>
	 * 方法描述：正则表达式获取域名
	 * </p>
	 * 
	 * @param url
	 * @return
	 * @author sql
	 * @since 2016年9月5日
	 *        <p>
	 *        history 2016年9月5日 sql 创建
	 *        <p>
	 */
	public static String getDomainName(String url) {
		if (url == null || "".equals(url)) {
			return "";
		}
		Pattern p = Pattern.compile("([\\w-]+\\.)+[\\w-]+(?<=/?)",
				Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(url);
		matcher.find();
		try{
			return matcher.group();
		}catch(Exception e){
			return "";
		}
	}
	public static String getDomain(String url,int level){
		String str="j|c|com|gr|dk|blue|gs|host|ga|fm|sydney|ch|kr|vc|tm|cx|gdn|it|travel|sh|sc|ma|tw|nl|hu|nu|accountant|webcam|fr|mobi|gg|gd|cd|party|my|la|sg|de|cm|in|so|us|eu|at|ws|jp|pw|gov|in|hk|ca|edu|int|mil|info|tv|pro|museum|coop|aero|cn|org|name|tv|me|asia|co|net|中国|公司|网络|pub|group|我爱你|集团|kim|cn|xin|xyz|shop|club|top|wang|win|site|vip|store|net|bid|cc|ltd|ren|lol|mom|online|tech|biz|red|website|space|link|news|date|com.cn|net.cn|org.cn|go.kr|or.kr|edu.cn|ac.kr|gov.cn";
		str=new StringBuffer(str).reverse().toString();
		String reg = "(/|^)"+"("+str+")+\\.(\\.?\\w*){"+level+"}";
		Pattern pattern = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);
		url=new StringBuffer(url).reverse().toString();
		Matcher matcher = pattern.matcher(url);
		
		if(matcher.find()){
				return new StringBuffer(matcher.group(0)).reverse().toString().replace("/", "");
		}else{
			return new StringBuffer(url).reverse().toString();
		}
	}
	/**
	 * 
	 * <p>方法名称：getDistance</p>
	 * <p>方法描述：相似度</p>
	 * @param str1
	 * @param str2
	 * @return
	 * @author Administrator
	 * @since  2016年11月29日
	 * <p> history 2016年11月29日 Administrator  创建   <p>
	 */
	public static int getDistance(String str1, String str2) {    
        int distance;    
        if (str1.length() != str2.length()) {    
            distance = -1;    
        } else {    
            distance = 0;    
            for (int i = 0; i < str1.length(); i++) {    
                if (str1.charAt(i) != str2.charAt(i)) {    
                    distance++;    
                }    
            }    
        }    
        return distance;    
    }  
}
