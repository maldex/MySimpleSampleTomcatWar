
assuming you've followed the [README.md], you should be inside your repo in _dist/Docker_ with the filnal .war file at hand.

## just simply run the application
```bash
docker run -it --rm -p 8080:8080 \
        -v ${PWD}/webapps:/usr/local/tomcat/webapps/ \
        tomcat
```
>
*NOTE*
- access via _http://<IP>:8080/MySimpleSampleTomcat/_, due to naming of the WAR file.
- JDDBC will fail with an unbound context error b/c *missing* _server.xml_ and _context.xml_
>

## let's add configuration files
```bash
docker run -it --rm -p 8080:8080 \
           -v ${PWD}/webapps:/usr/local/tomcat/webapps/ \
           -v ${PWD}/server.xml:/usr/local/tomcat/conf/server.xml \
           -v ${PWD}/context.xml:/usr/local/tomcat/conf/context.xml \
           tomcat
```
>
*NOTE*
- JDDBC will fail with an *missing* _jdbc-driver-class_
>

## let's add the driver
```bash
docker run -it --rm -p 8080:8080 \
           -v ${PWD}/webapps:/usr/local/tomcat/webapps/ \
           -v ${PWD}/server.xml:/usr/local/tomcat/conf/server.xml \
           -v ${PWD}/context.xml:/usr/local/tomcat/conf/context.xml \
           -v ${PWD}/lib/mysql-connector-java-5.1.46-bin.jar:/usr/local/tomcat/lib/myslq.jar \
           tomcat
```
>
*NOTE*
- JDDBC will fail to create a connection pool (communications link failure/driver nothing received)
- inspect the _xml-configurations_, it expects jvm-parameters like _-Ddb.host=x.x.x.x_ to be set.
>

## full sample
```bash
docker run -it --rm -p 8080:8080 \
           -v ${PWD}/webapps:/usr/local/tomcat/webapps/ \
           -v ${PWD}/server.xml:/usr/local/tomcat/conf/server.xml \
           -v ${PWD}/context.xml:/usr/local/tomcat/conf/context.xml \
           -v ${PWD}/lib/mysql-connector-java-5.1.46-bin.jar:/usr/local/tomcat/lib/myslq.jar \
           -e CATALINA_OPTS="-Ddb.name=DbTest -Ddb.host=someserver:3306 -Ddb.user=root -Ddb.pass=unsure" \
           tomcat
```