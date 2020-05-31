# webdav
使用milton + spring boot实现webdav协议

# 启动方式
- 本地启动
  - 使用maven编译后执行`webdava-0.0.1-SNAPSHOT.jar`
- docker启动
  - docker pull 614756773/hotpot-webdav
  - docker run -it -v /myown/docker/webdav_data:/app/data -p 8080:8080 -d --name hotpot-webdav hotpot-webdav:latest -Xmx40m -Xms40m
  > `/myown/docker/webdav_data`是挂载数据的路径，你需要替换成你本地电脑的路径
# 最后
- 在application.yml中配置username和password，username默认为admin，password默认为123456
- 启动后访问localhost:8080/auth进行登录
- 然后访问localhost:8080


