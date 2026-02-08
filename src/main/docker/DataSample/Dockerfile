FROM docker.io/library/mongo:8.0.9

COPY movies.json /movies.json

RUN mongod --fork --logpath /var/log/mongod.log    && mongoimport --db sample_mflix --collection movies --file /movies.json     && mongod --shutdown
