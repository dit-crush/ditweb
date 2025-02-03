package dit.crush.ditcrush.provider;


import com.alibaba.fastjson2.JSON;
import dit.crush.ditcrush.dto.AccessTokenDTO;
import dit.crush.ditcrush.dto.GitHubUser;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;


import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;


@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {

        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
        // 使用 'application/x-www-form-urlencoded' 作为媒体类型
        MediaType mediaType = MediaType.get("application/x-www-form-urlencoded");

        OkHttpClient client = new OkHttpClient.Builder()
                .proxy(proxy) // 设置代理
                .build(); // 使用 build() 方法构建 OkHttpClient 实例

        // 获取表单数据格式的请求体
        String formData = accessTokenDTO.toFormData();

        System.out.println(formData);

        RequestBody body = RequestBody.create(mediaType, formData);
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();
            System.out.println("GitHub Response: " + responseBody); // 输出整个响应内容
            // 解析 GitHub 响应的 token
            String[] split = responseBody.split("&");
            String token = split[0].split("=")[1];
            System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GitHubUser getUser(String accessToken){
        OkHttpClient client = new OkHttpClient();
        // 构建请求
        Request request = new Request.Builder()
                .url("https://api.github.com/user")
                .header("Authorization", "bearer " + accessToken)
                .header("Accept", "application/vnd.github.v3+json") // 设定 GitHub API 版本
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            //System.out.println(string);
            GitHubUser githubuser=JSON.parseObject(string,GitHubUser.class);
            return githubuser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
