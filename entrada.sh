#!/bin/bash

set -e

#Tempo para SOLR subir
sleep 10

EXPERIMENTO_DIR=/opt/experimentoradicalizacao

FEEDER_TESTES_URL=https://osf.io/u697p/download
FEEDER_ASG_URL=https://osf.io/qbm7z/download
FEEDER_DSG_URL=https://osf.io/pj3dw/download
FEEDER_ATR_URL=https://osf.io/rxsuj/download
FEEDER_DTR_URL=https://osf.io/3sqwy/download

if [  "$COLECAO_EXPERIMENTO" == "testes" -a ! -d "$EXPERIMENTO_DIR/feeders/testes" ]; then
    curl --tlsv1 -fsSL -o /tmp/feeder.zip ${FEEDER_TESTES_URL} \
	    && unzip /tmp/feeder.zip -d $EXPERIMENTO_DIR/feeders \
	    && rm -f /tmp/feeder.zip
fi

if [ "$COLECAO_EXPERIMENTO" == "asg" -a ! -d "$EXPERIMENTO_DIR/feeders/asg" ]; then
    curl --tlsv1 -fsSL -o /tmp/feeder.zip ${FEEDER_ASG_URL} \
	    && unzip /tmp/feeder.zip -d $EXPERIMENTO_DIR/feeders \
	    && rm -f /tmp/feeder.zip
fi

if [ "$COLECAO_EXPERIMENTO" == "dsg" -a ! -d "$EXPERIMENTO_DIR/feeders/dsg" ]; then
    curl --tlsv1 -fsSL -o /tmp/feeder.zip ${FEEDER_DSG_URL} \
	    && unzip /tmp/feeder.zip -d $EXPERIMENTO_DIR/feeders \
	    && rm -f /tmp/feeder.zip
fi

if [ "$COLECAO_EXPERIMENTO" == "atr" -a ! -d "$EXPERIMENTO_DIR/feeders/atr" ]; then
    curl --tlsv1 -fsSL -o /tmp/feeder.zip ${FEEDER_ATR_URL} \
	    && unzip /tmp/feeder.zip -d $EXPERIMENTO_DIR/feeders \
	    && rm -f /tmp/feeder.zip
fi

if [ "$COLECAO_EXPERIMENTO" == "dtr" -a ! -d "$EXPERIMENTO_DIR/feeders/dtr" ]; then
    curl --tlsv1 -fsSL -o /tmp/feeder.zip ${FEEDER_DTR_URL} \
	    && unzip /tmp/feeder.zip -d $EXPERIMENTO_DIR/feeders \
	    && rm -f /tmp/feeder.zip
fi

cd ./aplicacao && mvn install exec:java -DskipTests=true -Dexec.args="$FASE_EXPERIMENTO $COLECAO_EXPERIMENTO"



