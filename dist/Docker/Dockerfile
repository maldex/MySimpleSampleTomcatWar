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