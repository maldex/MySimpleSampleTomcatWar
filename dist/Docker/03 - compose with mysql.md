# Compose together

Install docker-compose with `sudo pip install docker-compose`.

see the [compose-file](docker-compose.yml)
```bash
mkdir wp_composed; cd wp_composed
vi docker-compose.yml
version: '3.3'

services:
   db:
     image: mysql:5.7
     volumes:
       - my-tommy-test-volume:/var/lib/mysql
     restart: always
     command: --init-file ../DbTest.sql
     environment:
       MYSQL_ROOT_PASSWORD: somePassWord

   wordpress:
     depends_on:
       - db
     image: my_sample_tommy
     ports:
       - "8080:8080"
     restart: never
     environment:
       DBHOST: db:3306
       DBPASS: somePassWord
volumes:
    my-tommy-test-volume:
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

