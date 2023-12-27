# pro.fessional.mirana

![Maven Central](https://img.shields.io/maven-central/v/pro.fessional/mirana?color=00DD00)
![Sonatype Snapshots](https://img.shields.io/nexus/s/pro.fessional/mirana?server=https%3A%2F%2Foss.sonatype.org)
[![Coverage Status](https://coveralls.io/repos/github/trydofor/pro.fessional.mirana/badge.svg)](https://coveralls.io/github/trydofor/pro.fessional.mirana)

> 中文 🇨🇳 | [English 🇺🇸](readme.md)

`POM(.xml)`, 月女，她有一只神箭，她有一只大猫。  
java8, 0依赖，是guava, `commons-*`的补充。

![mirana](./mirana_full.png)

## 如何使用

① 自己`clone`和`install`最豪横。

② 使用 maven central 比较稳妥。

``` xml
<dependency>
    <groupId>pro.fessional</groupId>
    <artifactId>mirana</artifactId>
    <version>${mirana.version}</version>
</dependency>
```

③ 使用 SNAPSHOT 与时俱进。

``` xml
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

* Null - 等效于`null`的默认值。
* None - 等效于empty的默认值。
* Sugar - 表示静态线程安全的工具类，可做kotlin语法糖
* Help - 表示和业务关联的或有生命周期的辅助类
* Util - 表示静态线程安全的工具类

## 详细文档

* <https://wings.fessional.pro/zh/a-mirana/>
* <https://github.com/fessionalpro/wings-doc/tree/main/src/zh/a-mirana/>
* <https://gitee.com/fessionalpro/wings-doc/tree/main/src/zh/a-mirana/>
