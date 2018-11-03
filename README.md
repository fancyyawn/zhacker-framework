# zhacker-framework

>个人微服务实践公共类库，参照spring-boot管理所有的maven依赖，提供了常用的starter。 具体子项目功能如下：

## zhacker-core

> 包含各核心基类，如实体基类、事件基类、校验基类、通用业务异常、应用层公共请求基类和响应类等。

## zhacker-boot

> 各通用功能的实现

* aop: 异常兜底、日志等切面
* event: 领域事件发布、存储、转发、通知、事件流等
* mybatis：常用TypeHandler等，如json存储等
* validate: 封装常用ConstraintValidator，如列表元素不重复(@ListDistinct）等校验。
* ribbon: 权重和标签路由策略实现
* cache: 多级缓存封装（redis+caffeine）

##  zhacker-boot-dependencies

> 参照spring-boot的maven依赖管理

## zhacker-boot-parent

> 各项目依赖的parent，取代spring-boot-parent。

## zhacker-boot-starters

> 包装zhacker-boot中通用功能的starter，以下只罗列了部分

* zhacker-boot-starter: starter的公共依赖
* zhacker-boot-starter-event-mybatis: DDD中事件相关基础设施
* zhacker-boot-starter-ribbon: 封装了权重和标签路由策略，使应用服务能支持按权重路由，按标签路由，从而可以进一步实现实例级别的限流、降级、灰度等需求。
* zhacker-boot-starter-aop: 一些日志打印、异常处理等通用切面
* zhacker-boot-starter-cache: 一个简单的缓存starter
* zhacker-boot-starter-swagger: 快速配置swagger
* zhacker-boot-starter-job: 快速引入elastic-job-lite


## 如何使用

1. 下载源码
``` 
git clone https://github.com/fancyyawn/zhacker-framework.git
```

2. maven编译

``` 
mvn clean install -Dmaven.test.skip
```

3. 在maven项目中引用starter

``` 
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

    <parent>
        <artifactId>zhacker-boot-parent</artifactId>
        <groupId>top.zhacker</groupId>
        <version>2.0-SNAPSHOT</version>
    </parent>

    <artifactId>zhacker-sample-spu</artifactId>
    <modelVersion>4.0.0</modelVersion>

    <dependencies>

        <dependency>
            <groupId>top.zhacker</groupId>
            <artifactId>zhacker-boot-starter-event-mybatis</artifactId>
        </dependency>
        <dependency>
            <groupId>top.zhacker</groupId>
            <artifactId>zhacker-boot-starter-aop</artifactId>
        </dependency>
        <dependency>
            <groupId>top.zhacker</groupId>
            <artifactId>zhacker-boot-starter-swagger</artifactId>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>repackage</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>

</project>
```