# reference https://github.com/openzipkin/zipkin

if [ ! -e zipkin.jar ]
then
    echo 'zipkin.jar not exist , start downloading....'
    curl -sSL https://zipkin.io/quickstart.sh | bash -s
fi

java -jar zipkin.jar

# STORAGE_TYPE=elasticsearch ES_HOSTS=http://myhost:9200 java -jar zipkin.jar
