
### start a mariadb instance as sql-server
```bash
# start a server container daemon
docker run --rm --name my-tommy-test-db -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=PassW0rd.1 mariadb
```
> 
*NOTE*
- the _--name_ flag names this instance. You won't be able to start another container with this name. check ```docker ps```
- the _-p 3306:3306_ makes it public (outside docker networks) accessible. This is not necessarely required.
- there is no persistency this way. attach a volume to keep your data after terminating this container.
>

### apply our sql 
```bash
# connecto to this db with the cli client from same image
docker run -it --rm --link my-tommy-test-db:THIS mariadb sh -c 'exec mysql -h"$THIS_PORT_3306_TCP_ADDR" -P"$THIS_PORT_3306_TCP_PORT" -uroot -p"$THIS_ENV_MYSQL_ROOT_PASSWORD"'
```
>
*NOTE*
- there seems to be a bug in docker preventing pipelineing docker with native pipes like stdIn/Out/Err. Unfortunately, copy paste the sql code.
- see the _--link xxx:THIS_ argument, and inspect the environment variable magic with THIS_-named variables ```docker run -it --rm --link my-tommy-test-db:THIS mariadb bash```
>