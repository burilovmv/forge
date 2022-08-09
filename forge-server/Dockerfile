FROM openjdk:11

EXPOSE 8000/tcp
ARG VERSION

RUN apt-get update
RUN apt-get install -y dos2unix

ADD target/forge-server-${VERSION}-jar-with-dependencies.jar forge/forge-server.jar
ADD target/res res/
ADD entrypoint.sh /entrypoint.sh
RUN dos2unix /entrypoint.sh
RUN chmod +x /*.sh

ENTRYPOINT ["/bin/sh", "-c", "/entrypoint.sh"]
