# Docker Container Workshop Script - ...
_[WordPress](http://www.wordpress.com)_ is a very popular blogging software which depends on MySQL.

TODO: add volume for WP-attachments

### by hand
prepare
```bash
docker volume create "MyDbVolume"

docker pull mysql:5.7
docker pull wordpress:4.8  # not latest
```

run the mysql containers
```bash
# SHELL1: run mysql
docker run -it --rm --name "MyMariaDbInstance" \
           -v MyDbVolume:/var/lib/mysql \
           -p 3306:3306 \
           -e MYSQL_ROOT_PASSWORD=PassW0rd.1 \
           -e MYSQL_DATABASE=wordpress \
           -e MYSQL_USER=wordpress \
           -e MYSQL_PASSWORD=wordpress \
           mysql:5.7

# SHELL2: run wordpress
docker run -it --rm --name "MyWordPressInstance" \
           -p 80:80 \
           -e WORDPRESS_DB_HOST=`hostname -f`:3306 \
           -e WORDPRESS_DB_USER=wordpress \
           -e WORDPRESS_DB_PASSWORD=wordpress \
           wordpress:latest
```
>
*NOTE*
- mysql service is exposed in this case as well. 
- see the _WORDPRESS_DB__* variables? these are really ugly to maintain
>

cleanup
```bash
docker kill MyWordPressInstance MyMariaDbInstance
docker rm MyWordPressInstance MyMariaDbInstance
docker volume rm MyDbVolume
docker image rm wordpress:latest mysql:5.7
```
 
https://docs.docker.com/compose/wordpress/

### with docker-compose
Install docker-compose with `sudo pip install docker-compose`.

```bash
mkdir wp_composed; cd wp_composed
vi docker-compose.yml
version: '3.3'

services:
   db:
     image: mysql:5.7
     volumes:
       - db_data:/var/lib/mysql
     restart: always
     environment:
       MYSQL_ROOT_PASSWORD: somewordpress
       MYSQL_DATABASE: wordpress
       MYSQL_USER: wordpress
       MYSQL_PASSWORD: wordpress

   wordpress:
     depends_on:
       - db
     image: wordpress:4.8
     ports:
       - "81:80"
     restart: always
     environment:
       WORDPRESS_DB_HOST: db:3306
       WORDPRESS_DB_USER: wordpress
       WORDPRESS_DB_PASSWORD: wordpress
volumes:
    db_data:
```
### deal with composition
```bash
# tell docker compose which composition by chdir to directory containing comose.yml 
cd wp_composed

# bring current composition
docker-compose up -d

# check
docker ps

# and take it down again, also delete volumes (--delete)
docker-compose down --volumes
```
>
*NOTE*
- try to update wordpress inside: it'll work once. Restart the container, reverted to old version.
- but, the DB schema might already be screwed
>

