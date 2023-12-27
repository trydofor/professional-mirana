# pro.fessional.mirana 

![Maven Central](https://img.shields.io/maven-central/v/pro.fessional/mirana?color=00DD00)
![Sonatype Snapshots](https://img.shields.io/nexus/s/pro.fessional/mirana?server=https%3A%2F%2Foss.sonatype.org)
[![Coverage Status](https://coveralls.io/repos/github/trydofor/pro.fessional.mirana/badge.svg)](https://coveralls.io/github/trydofor/pro.fessional.mirana)

> English 🇺🇸 | [中文 🇨🇳](readme-zh.md)

`POM(.xml)`, the moon princess, she has a sacred arrow and a big cat.  
java8, 0-dependency, is an addition to guava, `commons-*`.

![mirana](./mirana_full.png)

## How to use

① DIY `clone` and `install` is powerful.

② Using Maven Central is stable.

``` xml
<dependency>
    <groupId>pro.fessional</groupId>
    <artifactId>mirana</artifactId>
    <version>${mirana.version}</version>
</dependency>
```

③ Using SNAPSHOT is the latest.

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

## Naming convention

* Null - The default value to replace null `null`
* None - the default value of empty
* Sugar - static thread-safe tool can be used as kotlin Syntax-Sugar
* Help - business-related or lifecycle helper class
* Util - static thread-safe tool class

## Detailed Documents

* <https://wings.fessional.pro/a-mirana/>
* <https://github.com/fessionalpro/wings-doc/tree/main/src/a-mirana/>
* <https://gitee.com/fessionalpro/wings-doc/tree/main/src/a-mirana/>
