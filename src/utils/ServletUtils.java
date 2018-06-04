package utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author zianglei
 */
public class ServletUtils {

    /**
     * 返回Json响应
     * @param response
     * @param jsonString
     */
    public static void resJsonString(HttpServletResponse response, String jsonString) throws IOException{
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        OutputStream out = response.getOutputStream();
        out.write(jsonString.getBytes("utf-8"));
        out.flush();
    }

    /**
     * 返回提示错误的Json
     * @param response
     * @param errorCode
     * @param errorInfo
     * @throws IOException
     */
    public static <T> void resErrorJson(HttpServletResponse resp, T response, int errorCode, String errorInfo) throws IOException{
        if (response instanceof Response) {
            ((Response)response).setCode(500);
            ((Response)response).setErrorInfo(errorInfo);
            resJsonString(resp,JSON.toJSONString(response, SerializerFeature.WriteNullListAsEmpty));
        }
    }

    public static <T> void setRespError(T resp, int code,String errorInfo){
        if(resp instanceof Response){
            ((Response) resp).setCode(code);
            ((Response) resp).setErrorInfo(errorInfo);
        }
    }
}
