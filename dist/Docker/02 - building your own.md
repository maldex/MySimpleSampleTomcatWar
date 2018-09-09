### about the Dockerfile
simply:
- use the official tomcat container as starting position.
- cleanup and remove unwanted stuff
- copy our custom stuff into it (configuration, application, other)
- apply patching and helpers

Please inspect the [Dockerfile] yourself, there is some magic around the jdbc connection, configuration xml and environment variables.

### build
```bash
docker build -t MySimpleSampleTomcat . 
```

### inspect

```bash
# build
docker build -t jdbc_test . 

# run and see default env-values
docker run --rm -it -p 8080:8080 MySimpleSampleTomcat sh -c 'set | grep ^DB'
```

### run custom image
assuming the db server is on the same host:3306, access via hostname
```bash
docker run --rm -it -p 8080:8080 -e DBHOST=`hostname` -e DBPASS=PassW0rd.1 MySimpleSampleTomcat
```

