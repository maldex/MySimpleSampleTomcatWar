### build your own image
Based on the official tomcat container, include our application and dependencies into our own container. 
```bash
# create a custom environment loader
cat << EOF > load_env.sh
#! /bin/bash
if [ ! -z "\${MYDB_PORT_3306_TCP_ADDR}" ]; then DBHOST="\${MYDB_PORT_3306_TCP_ADDR}:\${MYDB_PORT_3306_TCP_PORT}"; fi
if [ ! -z "\${MYDB_ENV_MYSQL_ROOT_PASSWORD}" ]; then DBPASS="\${MYDB_ENV_MYSQL_ROOT_PASSWORD}"; fi

if [ -z "\${DBHOST}" ]; then 
    echo >&2 "ERROR: NO 'DBHOST' SET"
    echo >&2 " must either set --link:xxx:MYDDB, or specify DBHOST, DBNAME, DBPASS and DBUSER"
    exit 99
    fi
  
CATALINA_OPTS="\${CATALINA_OPTS} -Ddb.host=\${DBHOST}"
if [ ! -z "\${DBUSER}" ]; then CATALINA_OPTS="\${CATALINA_OPTS} -Ddb.user=\${DBUSER}"; fi
if [ ! -z "\${DBNAME}" ]; then CATALINA_OPTS="\${CATALINA_OPTS} -Ddb.name=\${DBNAME}"; fi
if [ ! -z "\${DBPASS}" ]; then CATALINA_OPTS="\${CATALINA_OPTS} -Ddb.pass=\${DBPASS}"; fi

export CATALINA_OPTS DBHOST DBNAME DBUSER
EOF

# create Docker build file
cat << EOF > Dockerfile
# lets start from 
FROM        tomcat:8.5.33-jre8

# provide default vaules for custom env variables
ENV         DBNAME          DbTest
ENV         DBUSER          root

# our custom environment loader to the root
COPY        load_env.sh     /

# patch catalina.sh to include our env
RUN         sed -i '2i### custom env loader' bin/catalina.sh
RUN         sed -i '3isource /load_env.sh' bin/catalina.sh
RUN         sed -i '4i###' bin/catalina.sh

# remove any previous applications
RUN         rm -rf          /usr/local/tomcat/webapps/

# copy server configurations
COPY        server.xml      /usr/local/tomcat/conf/server.xml
COPY        context.xml     /usr/local/tomcat/conf/context.xml 

# copy libraries
COPY        lib/*           /usr/local/tomcat/lib/

# copy our application
COPY        webapps/        /usr/local/tomcat/webapps/

# include this very file into the image
COPY        Dockerfile      /

# see https://hub.docker.com/_/tomcat/ -> Dockerfile for further detail and inherited values like cmd or port or such.
EOF

# build
docker build -t jdbc_test . 

# run and see default env-values
docker run --rm -it -p 8080:8080 jdbc_test sh -c 'set | grep ^DB'
```

### run custom image
assuming the db server is on the same host:3306, access via hostname
```bash
docker run --rm -it -p 8080:8080 -e DBHOST=`hostname` -e DBPASS=PassW0rd.1 jdbc_test
```

