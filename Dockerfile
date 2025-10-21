FROM ubuntu:latest
LABEL authors="lguerra"

ENTRYPOINT ["top", "-b"]