# pro.fessional.mirana 

![Maven Central](https://img.shields.io/maven-central/v/pro.fessional/mirana?color=00DD00)
![Sonatype Snapshots](https://img.shields.io/nexus/s/pro.fessional/mirana?server=https%3A%2F%2Foss.sonatype.org)
[![Coverage Status](https://coveralls.io/repos/github/trydofor/pro.fessional.mirana/badge.svg)](https://coveralls.io/github/trydofor/pro.fessional.mirana)

> English 🇺🇸 | [中文 🇨🇳](readme.md)

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
    <version>2.0.0</version>
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

## Square Root (sqrt)

The version number is `sqrt` + `3-part version` pattern, e.g. √2 are `1.4.0`, `1.4.1`, `1.4.14`.
If the version is not enough, then write infinitely `1.4.14213562373095`

* √1 = `1.0.0`
* √2 = `1.4.0`, `1.4.1`, `1.4.14`
* √3 = `1.7.0`, `1.7.3`, `1.7.32`
* √4 = `2.0.0`
* √5 = `2.2.0`, `2.2.3`, `2.2.4`
* √6 = `2.4.0`, `2.4.4`, `2.4.5`
* √7 = `2.6.0`, `2.6.4`, `2.6.5`
* √8 = `2.8.0`, `2.8.2`, `2.8.3`
* √9 = `3.0.0`

## Detailed Documents

* <https://wings.fessional.pro/en/a-mirana/>
* <https://github.com/fessionalpro/wings-doc/tree/main/docs/en/a-mirana/>
* <https://gitee.com/fessionalpro/wings-doc/tree/main/docs/en/a-mirana/>
