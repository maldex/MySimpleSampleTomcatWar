# Deploy an application building a custom image
assuming you've followed the [README.md](README.md), you should be inside your clone in _dist/Docker_ with the final application inside _webapps/_.

### about this Dockerfile
simply:
- use the official tomcat container as starting position.
- cleanup and remove unwanted stuff
- copy our custom stuff into it (configuration, application, other)
- apply patches and helpers

Please inspect the [Dockerfile] yourself, there is some magic around the jdbc connection, configuration xml and environment variables.

### build
```bash
# optional: explode war file. this increases startup-time and places our app at url / 
unzip webapps/MySimpleSampleTomcat.war -d webapps/ROOT
mv webapps/MySimpleSampleTomcat.war webapps/MySimpleSampleTomcat.war.org

# instruct docker to go through the Dockerfile, creating an :latest image
docker build -t my_sample_tommy .
```

### inspect
```bash
# about the images and cmd and exposed ports
docker inspect my_sample_tommy | less
docker inspect tomcat:8.5.33-jre8 | less

# what environment will i have inside the container?
docker run --rm -it   -e DBUSER=blablablabla    my_sample_tommy sh -c 'set | grep ^DB'
```

### full run
```bash
docker run --rm -it -p 8080:8080 -e DBHOST=`hostname -f` -e DBPASS=PassW0rd.1 my_sample_tommy
```

### full run using links
```bash
docker run --rm -it -p 8080:8080 --link my-tommy-test-db:MYDB \
    -e DBHOST=$MYSQL_PORT_3306_TCP_ADDR -e DBPASS=$MYDB_ENV_MYSQL_ROOT_PASSWORD \
    my_sample_tommy
```
