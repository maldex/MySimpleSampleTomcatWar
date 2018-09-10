# PoC for a WAR deployment into a container

This directory contains stuff required for running or building a docker container with your custom war file. 

### run the sample from apache.org
[Docker installation](README.docker-install.md) (>=v17) is assumed to be already done.
```bash
# download
wget -O sample.war https://tomcat.apache.org/tomcat-7.0-doc/appdev/sample/sample.war

# map this .war via volumes into the container
docker run -it --rm -p 8080:8080 -v ${PWD}/sample.war:/usr/local/tomcat/webapps/sample.war tomcat:8.5.33-jre8
```
>
*NOTE*
- access _http://ip:8080/_ and _http://ip:8080/sample_ 
- tomcat starts in a quite virgin state, manager is active but not granted, etc.
- inspect the _-v_ switch and compare to your experience with the _webapp/_ directory.
- adding _/bin/bash_ the the docker-command above will take you into a shell, not starting tomcat.
> 

### get started with this project
```bash
# clone this project
git clone git@github.com:maldex/MySimpleSampleTomcatWar.git
cd MySimpleSampleTomcatWar/

# uncompress libraries
unzip dist/lib/libs.zip -d dist/lib/
```

### prepare for docker
```
cd dist/Docker

# copy config and libs to here (our work- / build-directory)
cp -v ../*.xml ./
cp -Rv ../lib ./

# cleanup libs
rm -fv lib/__*.jar lib/*.zip

# download/aquire the actual .war file
mkdir webapps
wget -O webapps/MySimpleSampleTomcat.war https://github.com/maldex/MySimpleSampleTomcatWar/releases/download/pre-3/MySimpleSampleTomcat.war
```

### proceed 
Follow up the [XX - Something.md](ls_-lah_./*.md) markdown files in this directory.