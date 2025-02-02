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


@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
       MediaType mediaType = MediaType.get("application/json");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
               .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String[] split = string.split("&");
            String token =split[0].split("=")[1];
            //System.out.println(string);
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
                .header("Authorization", "token " + accessToken)
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
