#!/bin/bash

if [ "$1" == "sync" ]; then
    export APP_CONTEXT=syncApp
    export DBC_PROTOCOL=jdbc
elif [ "$1" == "reactive" ]; then
    export APP_CONTEXT=reactiveApp
    export DBC_PROTOCOL=r2dbc
else
    echo "Invalid argument. Please use 'sync' or 'reactive'."
    exit 1
fi

docker-compose up --build