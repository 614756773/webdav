package cn.hotpot.webdav;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "milton", ignoreUnknownFields = true)
public class MiltonProperties {
    private List<String> excludePaths = new ArrayList<>();


    private String filesystemRoot;

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

}
