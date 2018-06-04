package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * @Author zianglei
 */
public class JsonUtils {

    public static Object parseJson(HttpServletRequest request, Class c) throws IOException, UnsupportedEncodingException {
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
        String line = null;
        String a=request.getQueryString();
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null){
            sb.append(line);
        }
        return JSON.parseObject(sb.toString(), c);
    }

    public static String toJsonString(Object o){
        return JSON.toJSONString(o);
    }

    public static String toJsonString(Object o, SerializerFeature... features){
        return JSON.toJSONString(o, features);
    }
}
