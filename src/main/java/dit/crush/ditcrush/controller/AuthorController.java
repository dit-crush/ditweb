package dit.crush.ditcrush.controller;


import com.alibaba.fastjson.JSON;
import dit.crush.ditcrush.dto.AccessTokenDTO;
import dit.crush.ditcrush.dto.GitHubUser;
import dit.crush.ditcrush.mapper.UserDataMapper;
import dit.crush.ditcrush.model.User;
import dit.crush.ditcrush.provider.GithubProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.UUID;

@Controller
public class AuthorController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserDataMapper userDataMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request) throws IOException {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        accessTokenDTO.setRedirect_uri(redirectUri);
        //System.out.println(JSON.toJSONString(accessTokenDTO));
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = GithubProvider.getUser(accessToken);
        //System.out.println(gitHubUser.getId());
        if (gitHubUser != null && gitHubUser.getName() != null) {
            User user = new User();
            user.setToken(UUID.randomUUID().toString());
            user.setName(gitHubUser.getName());
            user.setAccount_id(String.valueOf(gitHubUser.getId()));
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userDataMapper.insert(user);
            //登陆成功，写cookie和session
            request.getSession().setAttribute("user", gitHubUser);
            return "redirect:/";
        }
        return "/index";
    }

}
