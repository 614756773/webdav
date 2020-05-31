package cn.hotpot.webdav.config;

import org.apache.mina.util.ConcurrentHashSet;

/**
 * @author hotpot
 * @since 2020-05-31 22:18:28
 */
public class IpWhitelist {

    public static volatile ConcurrentHashSet<String> cache = new ConcurrentHashSet<>();

}
