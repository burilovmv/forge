FROM openjdk:11

RUN apt-get update
RUN apt-get install -y dos2unix

ADD forge-server/target/forge-server-${VERSION}-jar-with-dependencies.jar forge/forge-server.jar
ADD forge-gui/res res/
ADD entrypoint.sh /entrypoint.sh
RUN dos2unix /entrypoint.sh
RUN chmod +x /*.sh

ENTRYPOINT ["/bin/sh", "-c", "/entrypoint.sh"]
