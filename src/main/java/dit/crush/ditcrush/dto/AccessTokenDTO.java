package dit.crush.ditcrush.dto;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class AccessTokenDTO {
    private String client_id;
    private String client_secret;
    private String code;
    private String redirect_uri;
    private String state;

    // Getters and Setters
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClient_secret() {
        return client_secret;
    }

    public void setClient_secret(String client_secret) {
        this.client_secret = client_secret;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    // 转换为 x-www-form-urlencoded 格式
    public String toFormData() {
            StringBuilder formData = new StringBuilder();
            formData.append("client_id=").append(client_id);
            formData.append("&client_secret=").append(client_secret);
            formData.append("&code=").append(code);
            formData.append("&redirect_uri=").append(redirect_uri);

            // 如果有 state 参数，添加到请求体
            if (state != null && !state.isEmpty()) {
                formData.append("&state=").append(state);
            }
            return formData.toString();
    }
}
