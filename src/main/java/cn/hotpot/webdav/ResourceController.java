package cn.hotpot.webdav;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author hotpot
 * @since 2020-05-16 18:57:40
 */
@RestController
@RequestMapping("/resources")
public class ResourceController {
    @Autowired
    MiltonProperties miltonProperties;

    @GetMapping("/{fileName}")
    public void getResuorce(@PathVariable("fileName") String fileName, HttpServletResponse response) throws Exception {
        FileInputStream fis = new FileInputStream(new File(miltonProperties.getFilesystemRoot() + "/" + fileName));
        ServletOutputStream os = response.getOutputStream();
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        os.write(bytes);
        os.flush();
    }
}
