#!/bin/bash

#declare CLI arguments
cmd=$1
db_username=$2
db_password=$3

#start docker if docker server is not running
sudo systemctl status docker || systemctl start docker

#check container status
docker container inspect jrvs-psql
container_status=$?

#switch case to handle create|stop|start operations
case $cmd in 
create)

#check if container is already created
if [ $container_status -eq 0 ]; then
	echo 'Container already exists'
	exit 1
fi

#check the number of CLI arguements
if [ $# -ne 3 ]; then
	echo 'Create requires username and password'
	exit 1
fi

#create a docker container
docker volume create pgdata
export PGPASSWORD='password'
docker run --name jrvs-psql -e POSTGRES_PASSWORD=$PGPASSWORD -d -v pgdata:/var/lib/postgresql/data -p 5432:5432 postgres:9.6-alpine
exit $?
;;

start|stop)
#check the if container has been created
if [ $container_status -eq 1 ]; then
	echo "Container has not been created"
	exit 1

fi

#command to start or stop the container
docker container $cmd jrvs-psql
exit $?
;;

*)
	echo 'Illegal command'
	echo 'Commands: start|stop|create'
	exit 1 
	;;
esac

