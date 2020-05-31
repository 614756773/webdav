package cn.hotpot.webdav.controller;

import cn.hotpot.webdav.config.IpWhitelist;
import cn.hotpot.webdav.config.MiltonProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author hotpot
 * @since 2020-05-16 18:57:40
 */
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    MiltonProperties miltonProperties;

    @GetMapping
    public void indexPage(HttpServletResponse response) {
        String html = "<html>\n" +
                "<head>\n" +
                "<title>login</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <form method=\"post\" action=\"auth/login\">\n" +
                "  <p>username: <input type=\"text\" name=\"username\" size=\"20\"></p>\n" +
                "  <p>password: <input type=\"password\" name=\"password\" size=\"20\"></p>\n" +
                "  <p><input type=\"submit\" value=\"submit\">\n" +
                "     <input type=\"reset\" value=\"cancel\"></p>\n" +
                "  </form>\n" +
                "  </body>\n" +
                "</html>\n";
        try (ServletOutputStream os = response.getOutputStream()) {
            os.write(html.getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!miltonProperties.getUsername().equals(username) && !
                miltonProperties.getPassword().equals(password)) {
            error(response);
            return;
        }

        success(request, response);
    }

    private void success(HttpServletRequest request, HttpServletResponse response) {
        File file = new File("ip_whitelist");

        try (FileWriter fw = new FileWriter(file, true)) {
            String remoteAddr = request.getRemoteAddr();
            if (!IpWhitelist.cache.contains(remoteAddr)) {
                fw.append(remoteAddr + "\r\n");
                IpWhitelist.cache.add(remoteAddr);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            response.getWriter().write("login success");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void error(HttpServletResponse response) {
        try {
            response.getWriter().println("Incorrect account or password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
