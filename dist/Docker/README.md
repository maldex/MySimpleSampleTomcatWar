This directory contains stuff required for running or building a docker container with your custom war file.


# Tomcat .WAR deployment PoC - get your app into a container  

### pre-test
run the simple sample from apache.org
```bash
# download
wget -O sample.war https://tomcat.apache.org/tomcat-7.0-doc/appdev/sample/sample.war

# map this .war via volumes into the container
docker run -it --rm -p 8080:8080 -v ${PWD}/sample.war:/usr/local/tomcat/webapps/sample.war tomcat:8.5.33-jre8
```
>
*NOTE*
- access _http://<ip>:8080/_
- tomcat starts in a quite virgin state, manager is active but not granted, etc.
- inspect the _-v_ switch and compare to your experience with the _webapp/_ directory.
- adding _/bin/bash_ the the docker-command above will take you into a shell, not starting tomcat.
> 

### download
download your application, dependencies and configurations.
```bash
# create work-directory
rm -rf jdbc_test/; mkdir jdbc_test; cd jdbc_test

# download the app and tomcat config
wget https://awef/Pub/dws/raw/master/PoC/JDBC-Test.war
wget https://awef/Pub/dws/raw/master/PoC/JDBC-Test/conf/server.xml
wget https://awef/Pub/dws/raw/master/PoC/JDBC-Test/conf/context.xml

# download JDBC Drivers
mkdir lib; pushd lib> /dev/null
wget https://awef/Pub/dws/raw/master/PoC/JDBC-Test/3rd-lib/jdbc/mariadb-java-client-1.2.3.jar
wget https://awef/Pub/dws/raw/master/PoC/JDBC-Test/3rd-lib/jdbc/mysql-connector-java-5.1.46-bin.jar
wget https://awef/Pub/dws/raw/master/PoC/JDBC-Test/3rd-lib/jdbc/ojdbc7.jar
wget https://awef/Pub/dws/raw/master/PoC/JDBC-Test/3rd-lib/jdbc/postgresql-42.2.2.jar
wget https://awef/Pub/dws/raw/master/PoC/JDBC-Test/3rd-lib/jdbc/sqlite-jdbc-3.23.1.jar
wget https://awef/Pub/dws/raw/master/PoC/JDBC-Test/3rd-lib/jdbc/sqljdbc42.jar
popd > /dev/null


# make default app, over-map the webapps
rm -rf webapps; mkdir webapps
unzip JDBC-Test.war -d webapps/ROOT
```
>
*NOTE*
- _server.xml_ and _context.xml_ are customized and expect _-Ddb.name_ JVM parameters.
- we've created a _webapps/_ directory and exploded our application into it's _ROOT_ subdir. 
- this increases the overall startup time and can be used to over-map the default applications.
>

### run original container
map the various bits and pieces into the container. Also provide database parameters via catalina's environment variable.
```bash
docker run -it --rm -p 8080:8080 \
           -v ${PWD}/webapps:/usr/local/tomcat/webapps/ \
           -v ${PWD}/server.xml:/usr/local/tomcat/conf/server.xml \
           -v ${PWD}/context.xml:/usr/local/tomcat/conf/context.xml \
           -v ${PWD}/lib/mysql-connector-java-5.1.46-bin.jar:/usr/local/tomcat/lib/myslq.jar \
           -e CATALINA_OPTS="-Ddb.name=DbTest -Ddb.host=someserver:3306 -Ddb.user=root -Ddb.pass=unsure" \
           tomcat
```
>
*NOTE*
- the _CATALINA_OPTS_ inherits into the eventually launched JVM, allowing for _${something}_ values inside xml configuration files.
- Container will come up, having our application at ROOT. 
>


