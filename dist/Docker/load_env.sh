#! /bin/bash
# assemble CATALINA parameters for inherition and expansion to xml configuration files based on env variables

if [ ! -z "${MYDB_PORT_3306_TCP_ADDR}" ]; then DBHOST="${MYDB_PORT_3306_TCP_ADDR}:${MYDB_PORT_3306_TCP_PORT}"; fi
if [ ! -z "${MYDB_ENV_MYSQL_ROOT_PASSWORD}" ]; then DBPASS="${MYDB_ENV_MYSQL_ROOT_PASSWORD}"; fi

if [ -z "${DBHOST}" ]; then 
    echo >&2 "ERROR: NO 'DBHOST' SET"
    echo >&2 " must either set --link:xxx:MYDDB, or specify DBHOST, DBNAME, DBPASS and DBUSER"
    exit 99
    fi

CATALINA_OPTS="${CATALINA_OPTS} -Ddb.host=${DBHOST}"
if [ ! -z "${DBUSER}" ]; then CATALINA_OPTS="${CATALINA_OPTS} -Ddb.user=${DBUSER}"; fi
if [ ! -z "${DBNAME}" ]; then CATALINA_OPTS="${CATALINA_OPTS} -Ddb.name=${DBNAME}"; fi
if [ ! -z "${DBPASS}" ]; then CATALINA_OPTS="${CATALINA_OPTS} -Ddb.pass=${DBPASS}"; fi

export CATALINA_OPTS DBHOST DBNAME DBUSER
