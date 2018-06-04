package base;

/**
 * @author tomstark
 */
public class Constants {
    //获取诊断结果地址
    public static final String Get_Register_Url = "/register";

    /**
     * 错误码
     */
    public enum errorCode {

        //json格式错误
        POST_JSON_ERROR(201),
        // 算法运行错误
        ALGO_ERROR(202);


        private int code;

        errorCode(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }
}
