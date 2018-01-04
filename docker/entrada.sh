#!/bin/bash

/opt/solr/bin/solr start

sleep 10 #Evitar erro 503 em virtude da inicialização dos cores


cd $EXPERIMENTO_DIR && mvn exec:java -DskipTests=true -Dexec.args="todas"

/bin/bash



