
### start a mariadb instance as sql-server
```bash
# start a server container
docker run --rm --name jdbc-test-server -e MYSQL_ROOT_PASSWORD=PassW0rd.1 -d mariadb
```
> 
*NOTE*
- The server is named and does not export it's 3306 onto external network. Add _-p 3306:3306_ to make it public accessible.
>
### create database
We're just simply using the same mariadb image as mysql-client to deploy the tables. 
```bash
# connect to this server
docker run -it --link jdbc-test-server:mysql --rm mariadb sh -c 'exec mysql -h"$MYSQL_PORT_3306_TCP_ADDR" -P"$MYSQL_PORT_3306_TCP_PORT" -uroot -p"$MYSQL_ENV_MYSQL_ROOT_PASSWORD"'
```
```sql
DROP DATABASE DbTest;
CREATE DATABASE DbTest;
USE DbTest;
CREATE TABLE `TblTest` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text CHARACTER SET latin1 COLLATE latin1_german1_ci NOT NULL,
  `note` text CHARACTER SET latin1 COLLATE latin1_german1_ci,
  PRIMARY KEY (`id`)
);
INSERT INTO `TblTest` VALUES (1,'hans','isst ne wurst'),(2,'fritz','liebt fisch'),(3,'peter','\'\''),(4,'that guy','\'\'');
SELECT * FROM `TblTest`;
EXIT;
```
>
*NOTE*
- The _--link_ feature enables environment variables of a different container can be 'sourced' into this one.
- launch into bash instead of mysql, explore the environment variables.
>

### run generic tomcat image
```bash
docker run -it --rm -p 8080:8080 \
           -v ${PWD}/webapps:/usr/local/tomcat/webapps/ \
           -v ${PWD}/server.xml:/usr/local/tomcat/conf/server.xml \
           -v ${PWD}/context.xml:/usr/local/tomcat/conf/context.xml \
           -v ${PWD}/lib/mysql-connector-java-5.1.46-bin.jar:/usr/local/tomcat/lib/myslq.jar \
           --link jdbc-test-server:MYDB \
           -e CATALINA_OPTS="-Ddb.name=DbTest -Ddb.host=\$MYSQL_PORT_3306_TCP_ADDR:\$MYSQL_PORT_3306_TCP_PORT -Ddb.user=root -Ddb.pass=\$MYSQL_ENV_MYSQL_ROOT_PASSWORD" \
           tomcat
```

### rim custom tomcat image
```bash
docker run --rm -it -p 8080:8080 --link jdbc-test-server:mysql \
    -e DBHOST=$MYSQL_PORT_3306_TCP_ADDR -e DBPASS=PassW0rd.1 \
    jdbc_test
```

