FROM openjdk:11

RUN mkdir backend
WORKDIR /backend

ENTRYPOINT ["sh", "fastrun.sh"]
