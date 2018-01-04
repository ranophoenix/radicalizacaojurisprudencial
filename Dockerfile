FROM ubuntu:17.10
MAINTAINER Robert Anderson <ranomail@gmail.com>

RUN apt-get update && apt-get upgrade -y
RUN apt-get install -q -y openjdk-8-jdk curl lsof unzip 

ARG MAVEN_VERSION=3.5.2
ARG USER_HOME_DIR="/root"
ARG SHA_MAVEN=707b1f6e390a65bde4af4cdaf2a24d45fc19a6ded00fff02e91626e3e42ceaff
ARG BASE_URL=https://apache.osuosl.org/maven/maven-3/${MAVEN_VERSION}/binaries

ARG SOLR_VERSION=6.2.1
ARG SOLR_URL=https://archive.apache.org/dist/lucene/solr/${SOLR_VERSION}/solr-${SOLR_VERSION}.tgz
ARG SOLR_SHA=413c7fd12561029532f5b10d1d3a51f3a641be88

ENV MAVEN_HOME /opt/maven 
ENV	MAVEN_CONFIG "$USER_HOME_DIR/.m2" 
ENV	EXPERIMENTO_DIR /opt/experimentoradicalizacao 
ENV	PATH="/opt/scripts/:$PATH"

RUN mkdir -p /opt/scripts \
 $EXPERIMENTO_DIR/feeders \
 $EXPERIMENTO_DIR/queries \ 
 $EXPERIMENTO_DIR/trec
 
RUN mkdir -p /opt/maven /opt/maven/ref \
  && curl -fsSL -o /tmp/apache-maven.tar.gz ${BASE_URL}/apache-maven-${MAVEN_VERSION}-bin.tar.gz \
  && echo "${SHA_MAVEN}  /tmp/apache-maven.tar.gz" | sha256sum -c - \
  && tar -xzf /tmp/apache-maven.tar.gz -C /opt/maven --strip-components=1 \
  && rm -f /tmp/apache-maven.tar.gz \
  && ln -s /opt/maven/bin/mvn /usr/bin/mvn
  
RUN mkdir -p /opt/solr \
  && curl -fsSL -o /tmp/solr.tar.gz ${SOLR_URL} \
  && echo "${SOLR_SHA}  /tmp/solr.tar.gz" | sha1sum -c - \
  && tar -xzf /tmp/solr.tar.gz -C /opt/solr --strip-components=1 \
  && rm -f /tmp/solr.tar.gz     
  
ARG FEEDER_TESTES_URL=https://osf.io/u697p/download
ARG FEEDER_ASG_URL=https://osf.io/qbm7z/download
ARG FEEDER_DSG_URL=https://osf.io/pj3dw/download
ARG FEEDER_ATR_URL=https://osf.io/rxsuj/download
ARG FEEDER_DTR_URL=https://osf.io/3sqwy/download

RUN curl --tlsv1 -fsSL -o /tmp/feeder.zip ${FEEDER_TESTES_URL} \
	&& unzip /tmp/feeder.zip -d $EXPERIMENTO_DIR/feeders \
	&& rm -f /tmp/feeder.zip
	
RUN curl --tlsv1 -fsSL -o /tmp/feeder.zip ${FEEDER_ASG_URL} \
	&& unzip /tmp/feeder.zip -d $EXPERIMENTO_DIR/feeders \
	&& rm -f /tmp/feeder.zip

RUN curl --tlsv1 -fsSL -o /tmp/feeder.zip ${FEEDER_DSG_URL} \
	&& unzip /tmp/feeder.zip -d $EXPERIMENTO_DIR/feeders \
	&& rm -f /tmp/feeder.zip

RUN curl --tlsv1 -fsSL -o /tmp/feeder.zip ${FEEDER_ATR_URL} \
	&& unzip /tmp/feeder.zip -d $EXPERIMENTO_DIR/feeders \
	&& rm -f /tmp/feeder.zip

RUN curl --tlsv1 -fsSL -o /tmp/feeder.zip ${FEEDER_DTR_URL} \
	&& unzip /tmp/feeder.zip -d $EXPERIMENTO_DIR/feeders \
	&& rm -f /tmp/feeder.zip	

COPY ./aplicacao /opt/experimentoradicalizacao/
COPY ./queries /opt/experimentoradicalizacao/queries
COPY ./docker /opt/scripts/
COPY ./solr_cores /opt/solr/server/solr/
	
RUN cd $EXPERIMENTO_DIR && mvn package -DskipTests=true

EXPOSE 8983
WORKDIR /opt/scripts/
ENTRYPOINT ["entrada.sh"]
		