if [ ! -e sentinel-dashboard.jar ]
then
    echo 'sentinel-dashboard.jar not exist , start downloading....'
    wget http://edas-public.oss-cn-hangzhou.aliyuncs.com/install_package/demo/sentinel-dashboard.jar
fi

java -jar -Dserver.port=15000 sentinel-dashboard.jar


