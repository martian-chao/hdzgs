package com.cars.util.string;


import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用户字符串操作，这里面包括字符串的decode、encode、substrac等等操作
 *
 * @author 孔垂云
 * @date 2017-05-23
 */
public class StringUtil {

    /**
     * 把前台传过来的含中文的url字符串转换成标准中文，比如%25E5%258C%2597%25E4%25BA%25AC转换成北京
     *
     * @param url url字符串
     * @return string
     */
    public static String decodeUrl(String url) {
        if (url == null)
            return "";
        String str = "";
        try {
            str = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 把比如北京转换成%25E5%258C%2597%25E4%25BA%25AC
     *
     * @param url url字符串
     * @return string
     */
    public static String encodeUrl(String url) {
        if (url == null)
            return "";
        String str = "";
        try {
            str = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 取字符除最后一位的子串，比如 aaa,bbb, 返回aaa,bbb，一般用在多个字段进行拼接，要去除最后一位
     *
     * @param str 字符串
     * @return string
     */
    public static String subTract(String str) {
        if (str.length() == 0)
            return "";
        else
            return str.substring(0, str.length() - 1);
    }

    /**
     * 判断字符串是null或空，null或""都返回true
     *
     * @param str 字符串
     * @return boolean
     */
    public static boolean isNullOrEmpty(String str) {
        if (str == null || str.equals(""))
            return true;
        else
            return false;
    }


    /**
     * 判断字符串不是null或空
     *
     * @param str 字符串
     * @return
     */
    public static boolean isNotNullOrEmpty(String str) {
        if (str != null && !str.equals(""))
            return true;
        else
            return false;
    }


    /**
     * 判断是否是ajax请求，用于进行权限控制或异常处理时，得判断是否是ajax请求
     *
     * @param request
     * @return
     */
    public static boolean checkAjaxRequest(HttpServletRequest request) {
        String requestType = request.getHeader("X-Requested-With");
        if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
            return true;
        } else
            return false;
    }


    /**
     * 获取客户端请求的ip地址，可以跳过代理等直接获取
     *
     * @param request
     * @return
     */
    public static String getIp(HttpServletRequest request) {
        String szClientIP = request.getHeader("x-forwarded-for");
        if (szClientIP == null || szClientIP.length() == 0 || "unknown".equalsIgnoreCase(szClientIP))
            szClientIP = request.getHeader("Proxy-Client-IP");
        if (szClientIP == null || szClientIP.length() == 0 || "unknown".equalsIgnoreCase(szClientIP))
            szClientIP = request.getHeader("WL-Proxy-Client-IP");
        if (szClientIP == null || szClientIP.length() == 0 || "unknown".equalsIgnoreCase(szClientIP))
            szClientIP = request.getRemoteAddr();
        return "0:0:0:0:0:0:0:1".equals(szClientIP) ? "127.0.0.1" : szClientIP;
    }


    /**
     * 过滤表情，在移动开发中，有些字符是表情等特殊字符，数据库不识别，需要过滤掉， 替换为*
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {
        if (StringUtil.isNotNullOrEmpty(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
        } else {
            return source;
        }
    }

    /**
     * 获取url地址，整个请求地址，不包含?后面的参数信息，如果最后一位后缀为#，去掉
     *
     * @param request 请求
     * @return url地址
     */
    public static String getUrlPath(HttpServletRequest request) {
        String url;
        if (request.getServerPort() == 80) {
            url = request.getScheme() + "://" + request.getServerName() + request.getRequestURI();
        } else {
            url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getRequestURI();
        }
        if (url.contains("#")) {
            url = url.substring(0, url.indexOf("#"));
        }
        return url;
    }

    /**
     * 判断是否是手机号
     *
     * @param mobile
     * @return
     */
    public static boolean isMobile(String mobile) {
        Pattern p = Pattern.compile("^1[3-9]\\d{9}$");
        Matcher m = p.matcher(mobile);
        boolean b = m.matches();
        return b;
    }

    /**
     * 获取Request的参数，并将其"Key=Value&Key=Value"的格式返回
     *
     * @param request 请求
     * @return "Key=Value&Key=Value"格式的字符串
     */
    public static String getOperaParams(HttpServletRequest request) {
        String parameters = "";// 定义所有参数值
        Map<String, String[]> map = request.getParameterMap();
        // /取得所有参数值，用&号组装起来
        Object[] obj = null;
        obj = map.keySet().toArray();
        for (int i = 0; i < obj.length; i++) {
            parameters += obj[i].toString() + "=" + request.getParameter(obj[i].toString()) + "&";
        }
        return parameters;
    }

    /**
     * 去掉小数点后面的0，导入数据的时候用到
     *
     * @param val
     * @return
     */
    public static String formatNumber(String val) {
        val = val.replaceAll("0+?$", "");// 去掉后面无用的零
        val = val.replaceAll("[.]$", "");// 如小数点后面全是零则去掉小数点
        return val;
    }

    /**
     * 格式化数字，如果小于10，返回0+数字，其他直接返回
     *
     * @param val
     * @return
     */
    public static String formatNumber2(int val) {
        if (val < 10) {
            return "0" + val;
        } else {
            return String.valueOf(val);
        }
    }

    /**
     * sql注入删除特殊字符
     */
	public static String delSqlInjection(String sql) {
		if (sql == null) return "";
		return sql.replaceAll(".*([';]+|(--)+).*", " ");
	}

    /**
     * 去掉字符串包含的所有空格
     *
     * @param value
     * @return
     */
    public static String string2AllTrim(String value) {
        if (isNullOrEmpty(value)) {
            return "";
        }
        return value.trim().replace(" ", "");
    }

    /**
     * 根据从request中获取body的参数值
     *
     * @param br
     * @return
     */
    public static String getBodyString(BufferedReader br) {
        String inputLine;
        StringBuilder str = new StringBuilder();
        try {
            while ((inputLine = br.readLine()) != null) {
                str.append(inputLine);
            }
            br.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return str.toString();
    }

    public static Map<String, String> getRequestParamMap(HttpServletRequest request) {
        Map<String, String> map = new HashMap<>(10);
        String requestParams = getOperaParams(request);
        String[] paramKVs = requestParams.split("&");
        for (String kv : paramKVs) {
            if (isNotNullOrEmpty(kv)) {
                int index = kv.indexOf("=");
                map.put(kv.substring(0, index), kv.substring(index + 1));
            }
        }
        return map;
    }
}
