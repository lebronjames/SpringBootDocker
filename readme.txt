SpringBoot项目使用Docker构建、部署、运行
1). pom.xml增加docker-maven-plugin插件
<!-- 插件是用于构建 Maven 的 Docker Image  -->
<plugin>
    <groupId>com.spotify</groupId>
    <artifactId>docker-maven-plugin</artifactId>
    <version>0.4.13</version>
    <configuration>
    	<!-- 指定了镜像的名字，本例为 springio/SpringBootDocker -->
        <imageName>${docker.image.prefix}/${project.artifactId}</imageName>
        <!-- dockerDirectory指定 Dockerfile 的位置 -->
        <dockerDirectory>src/main/docker</dockerDirectory>
        <!-- resources是指那些需要和 Dockerfile 放在一起，在构建镜像时使用的文件，一般应用 jar 包需要纳入。 -->
        <resources>
            <resource>
                <targetPath>/</targetPath>
                <directory>${project.build.directory}</directory>
                <include>${project.build.finalName}.jar</include>
            </resource>
        </resources>
    </configuration>
</plugin>
2). 在src/main文件夹下，创建docker文件夹，再创建Dockerfile文件
FROM java:8
VOLUME /tmp
ADD SpringBootDocker-1.0.0.jar app.jar
RUN bash -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
3). 使用mvn package -Dmaven.test.skip=true，打包SpringBootDocker-1.0.0.jar

4). 将Dockerfile、SpringBootDocker-1.0.0.jar包，上传到Linux的/opt/migu/docker目录下

5). cd /opt/migu/docker,执行构建Image(docker build -t migu/springboot .)
Sending build context to Docker daemon 
Step 0 : FROM java:8
 ---> d11c3799fa6a
Step 1 : VOLUME /tmp
 ---> Running in 7c1932547369
 ---> e5a15abd4ed1
Removing intermediate container 7c1932547369
Step 2 : ADD SpringBootDocker-1.0.0.jar app.jar
 ---> 8287f6c4aa8f
Removing intermediate container c47a63913c02
Step 3 : RUN bash -c 'touch /app.jar'
 ---> Running in 65e1a6289a7d
 ---> 6c453183b50d
Removing intermediate container 65e1a6289a7d
Step 4 : ENV JAVA_OPTS ""
 ---> Running in 45f13b9e2579
 ---> dda206430412
Removing intermediate container 45f13b9e2579
Step 5 : ENTRYPOINT sh -c java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar
 ---> Running in 1ef4e52c8292
 ---> c3b6488257d9
Removing intermediate container 1ef4e52c8292
Successfully built c3b6488257d9

6). 运行Docker image(docker run -p 8091:8091 migu/springboot)

7). 常用命令
docker ps
docker kill b54b05cc210d
docker rmi -f migu/springboot
service docker restart
docker run --net=host test3 #docker使用的网络实际上和宿主机一样

8). 查看容器进程
docker top可以查看运行容器中运行的进程 
docker ps查看一下运行的容器 

9). 查看容器信息
docker inspect 容器名/容器ID

10). 进入容器执行命令
docker exec -it 容器名 /bin/bash

