package cn.hotpot.webdav.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "milton")
public class MiltonProperties {
    private List<String> excludePaths = new ArrayList<>();

    private String filesystemRoot;

    private String username;

    private String password;

    public String getFilesystemRoot() {
        return filesystemRoot;
    }

    public void setFilesystemRoot(String filesystemRoot) {
        this.filesystemRoot = filesystemRoot;
    }

    public List<String> getExcludePaths() {
        return excludePaths;
    }

    public void setExcludePaths(List<String> excludePaths) {
        this.excludePaths = excludePaths;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
