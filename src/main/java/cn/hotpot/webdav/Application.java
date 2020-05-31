package cn.hotpot.webdav;

import cn.hotpot.webdav.config.IpWhitelist;
import cn.hotpot.webdav.config.SpringMiltonFilterBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		initIpWhitelist();
	}

	private static void initIpWhitelist() {
		ensureFileExist();

		if (IpWhitelist.cache.isEmpty()) {
			synchronized (SpringMiltonFilterBean.class) {
				if (IpWhitelist.cache.isEmpty()) {
					File file = new File("ip_whitelist");
					try (BufferedReader br = new BufferedReader(new FileReader(file))) {
						br.lines().forEach(e -> IpWhitelist.cache.add(e));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	private static void ensureFileExist() {
		File file = new File("ip_whitelist");
		if (!file.exists()) {
			try (FileWriter fw = new FileWriter(file)) {
				fw.append("ip:\r\n");
				fw.flush();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}


}
