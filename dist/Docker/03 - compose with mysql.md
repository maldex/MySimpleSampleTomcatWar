### clean-up
let's just start over again, shall we?
```bash
cd ~/MySimpleSampleTomcatWar/dist/Docker/

# kill any known instances ...
docker-compose down --volumes  # works in this directory b/c docker-compose.yaml and removes persistent storage
docker kill my-tommy-test-db my_sample_tommy docker_tommytest_1 docker_db_1

# remove images
docker image rm tomcat:latest tomcat:8.5.33-jre8 mariadb:latest mysql:5.7 my_sample_tommy:latest

# reset working directory and loose your own changes  
#git reset --hard

# rebuild our own image
docker build -t my_sample_tommy .
```

# Compose together
Install docker-compose with `sudo pip install docker-compose`.

See the [compose-file](docker-compose.yaml).

### deal with composition
```bash
# bring current composition
docker-compose up -d       # -d for backgrounded/deamonized

# see startup
docker-compose logs -f

# check composition
docker ps

# and take it down again, also delete volumes (--delete)
docker-compose down --volumes
```

>
*NOTE*
- access via _http://<IP>:8080/MySimpleSampleTomcat/_ or just _http://<IP>:8080/_ depends on your _webapps/_ directoy upon build.
>

