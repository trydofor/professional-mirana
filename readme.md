# pro.fessional.mirana

![Maven Central](https://img.shields.io/maven-central/v/pro.fessional/mirana?color=00DD00)
![Sonatype Snapshots](https://img.shields.io/nexus/s/pro.fessional/mirana?server=https%3A%2F%2Foss.sonatype.org)
[![Coverage Status](https://coveralls.io/repos/github/trydofor/pro.fessional.mirana/badge.svg)](https://coveralls.io/github/trydofor/pro.fessional.mirana)

POM(.xml), 月女，她有一只神箭，她有一只大猫。  
java8, 0依赖，是guava,commons-*的补充。

![mirana](./mirana_full.png)

## 如何使用

① 自己`clone`和`install`最豪横。

② 使用 maven central 比较稳妥。
``` xml
<dependency>
    <groupId>pro.fessional</groupId>
    <artifactId>mirana</artifactId>
    <version>2.0.0</version>
</dependency>
```

③ 使用 SNAPSHOT 与时俱进。
``` xml
<!-- 2.0.0-SNAPSHOT -->
<repository>
    <id>oss-sonatype</id>
    <name>oss-sonatype</name>
    <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
    <snapshots>
        <enabled>true</enabled>
    </snapshots>
</repository>
```

## 命名约定

* Null 等效于null的默认值。
* None 等效于empty的默认值。
* Sugar 表示静态线程安全的工具类，可做kotlin语法糖
* Help 表示和业务关联的或有生命周期的辅助类
* Util 表示静态线程安全的工具类

## 开个根号

版本号为开根号+三段法，如√2对应的`1.4.0`, `1.4.1`, `1.4.14`，
若版本不够且不想跳版本，则无限的写下去`1.4.14213562373095`

* √1 = `1.0.0`
* √2 = `1.4.0`, `1.4.1`, `1.4.14`
* √3 = `1.7.0`, `1.7.3`, `1.7.32`
* √4 = `2.0.0`
* √5 = `2.2.0`, `2.2.3`, `2.2.4`
* √6 = `2.4.0`, `2.4.4`, `2.4.5`
* √7 = `2.6.0`, `2.6.4`, `2.6.5`
* √8 = `2.8.0`, `2.8.2`, `2.8.3`
* √9 = `3.0.0`

## 详细文档

* <https://wings.fessional.pro/a-mirana/>
* <https://github.com/fessionalpro/wings-doc/tree/main/docs/a-mirana/>
* <https://gitee.com/fessionalpro/wings-doc/tree/main/docs/a-mirana/>
