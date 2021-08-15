FROM --platform=linux/amd64 python:3.8.10-buster
LABEL maintainer="Jongwoo Na <njw1204@naver.com>"
EXPOSE 5000

ENV OPENSSL_CONF=/dev/null

RUN wget https://bitbucket.org/ariya/phantomjs/downloads/phantomjs-2.1.1-linux-x86_64.tar.bz2 &&\
    tar -xvjf phantomjs-2.1.1-linux-x86_64.tar.bz2 &&\
    rm phantomjs-2.1.1-linux-x86_64.tar.bz2 &&\
    mv phantomjs-2.1.1-linux-x86_64 /usr/local/share/ &&\
    ln -sf /usr/local/share/phantomjs-2.1.1-linux-x86_64/bin/phantomjs /usr/local/bin/phantomjs

RUN mkdir /project
WORKDIR /project

COPY requirements.txt .
RUN pip install -r requirements.txt

COPY src/ src/

WORKDIR /project/src
ENTRYPOINT ["gunicorn", "app:app"]
CMD ["-b", "0.0.0.0:5000", "-w", "1", "-k", "gevent"]
