package dit.crush.ditcrush.provider;


import dit.crush.ditcrush.dto.AccessTokenDTO;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.tomcat.util.http.parser.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
public class GitHubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
//        MediaType JSON = MediaType.get("application/json");
//        OkHttpClient client = new OkHttpClient();
//        RequestBody body = RequestBody.create(json, JSON);
//        Request request = new Request.Builder()
//                .url(url)
//                .post(body)
//                .build();
//        try (Response response = client.newCall(request).execute()) {
//            return response.body().string();
//        }
        return null;
    }

}
