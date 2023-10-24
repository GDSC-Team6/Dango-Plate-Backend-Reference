FROM openjdk:11

RUN mkdir backend
COPY . /backend
WORKDIR /backend

ENTRYPOINT ["sh", "fastrun.sh"]
