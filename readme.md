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

## Naming 命名约定

* Null 等效于null的默认值。
* None 等效于empty的默认值。
* Sugar 表示静态线程安全的工具类，可做kotlin语法糖
* Help 表示和业务关联的或有生命周期的辅助类
* Util 表示静态线程安全的工具类

## root2 版本号

版本号为开根号+三段法，如√2对应的`1.4.0`, `1.4.1`, `1.4.14`，
若版本不够且不想跳版本，则无限的写下去`1.4.14213562373095`

* √1 = `1.0.0`
* √2 = `1.4.0`, `1.4.1`, `1.4.14`
* √3 = `1.7.0`, `1.7.3`, `1.7.32`
* √4 = `2.0.0`
* √5 = `2.2.0`, `2.2.3`, `2.2.36`
* √6 = `2.4.0`, `2.4.4`, `2.4.49`
* √7 = `2.6.0`, `2.6.4`, `2.6.46`
* √8 = `2.8.0`, `2.8.2`, `2.8.28`
* √9 = `3.0.0`

## `anti/` 反工程化

* G - 反模式，跨层传值
* L - 反模式，跨层收集信息

## `best/` 高质量代码

 * ArgsAssert 前置断言 - IllegalArgumentException
 * StateAssert 中间或后置断言 - IllegalStateException

## `bits/` 二进制，字节相关

 * Aes128 - jdk AES/CBC/PKCS5Padding，若AES/CBC/PKCS7Padding，用bouncycastle
 * Base64 - 默认使用 RFC4648_URLSAFE 和 UTF8。支持`+/`和`-_`
 * Bytes - Hex和unicode(`我`(25105)=>'\u6211')
 * HmacHelp - MessageAuthenticationCode HmacMD5, HmacSHA1, HmacSHA256
 * Md5 - md5sum, md5check
 * HdHelp - MessageDigest MD5, SHA1, SHA256

## `cast/` 类型转换

 * BiConvertor - 双向converter
 * BoxedCastUtil - 包装类和原始类型的转换
 * BoxedTypeUtil - 包装类兼容的instanceOf，isAssignable
 * EnumConvertor - 支持enum全路径模糊序列化和具名精确序列化
 * StringCastUtil - 字符串和其他类型的转换
 * TypedCastUtil - 类型参数，泛型的转换

## `code/` 编码转码

### Crc4Int - 带crc的int32

根据int数值，生成一个伪随机，可校验的，可解密出原值的int数字。
尽量提高人类可读性和伪随机性。

### Crc8Long, Crc8LongUtil - 带crc的int64

根据long数值，生成一个伪随机，可校验的，可解密出原值的long数字。
用户可以自定义bit位序列，系统默认提供三种，参考Crc8LongUtil。

适用场景，安全要求一般，暴露的数字ID信息。可以高效的双向解析和校验。

## Excel26Az - excel的26进制索引

按Excel列命名方式进行双向解析（A:1,B:2,...,Z:26,AA:27）

### LeapCode - 伪随机高可读code

提供26字母和10数字（去掉01OI易混淆）的构成的32位字母数字编码。
编码后的字符串看起来比较随机，可解密出原值，可填充到固定长度。
用户可以自定义数字字典，以实现比较安全的效果。

适用场景，伪随机验证码，安全要求一般，高效双向解析的编码。

### 其他编码

 * Mod10Code - usps 的校验算法
 * RandCode - 基于Random的一些人类可读性更好的随机数
 * SlotCode - 固定仓位的随机分配，比如取件码

## `cond/` 条件断言

 * Contains - 包含
 * StaticFlag - 全局Flag

## `data/` 数据传递

 * Arr - 一些Array的操作
 * CodeEnum - 业务code枚举，如多国语，状态
 * DataResult - 携带data的DTO
 * Diff - diff2个集合，如数据集中判断插入，更新，删除
 * Null - 以`空`消除null是我们的目标。
 * Q - 单参数Query类
 * R - Result的场景类
 * Rank - 按多条件顺序来排序
 * U - 内部传递数据的Tuple,Either
 * Z - 第一个满足条件(如非null)的数据操作

## `dync/` 动态编译

 * Java - 动态编译和创建java
 * Js - 使用java的ScriptEngine执行js代码

## `fake/` 伪装数据

 * FakeDate - 生成指定偏移量附近的伪随机日期，保证结果等幂。
 * FakeName - 生成随机中文姓名

## `flow/` 流程控制

在高层架构设计，高价函数调用，流处理中，需要使用异常参与流程中断。
类似spring security体系，scala的break语法，kotlin的`return@`。
以下为低消耗的无栈异常，中断流程的场景，属反模式，若非必须不建议使用。

 * FlowBreak - 静态工具类
 * FlowBreakException  - 用Enum类的异常参与控制流程。
 * FlowReturnException - 具有返回值
 * LoopControl - 循环控制enum
 * ReturnOrException - 是破例返回还是异常

## `func/` function构造

 * Dcl - DCL of Runnable
 * Fn - distinct和duplicate

## `i18n/` 多国语

 * I18nAware - 标记型接口
 * I18nString - 支持 i18n的String
 * LocaleResolver - 支持 `-`和`_`
 * ZoneIdResolver - 不区分大小写，支持部分命名

## `id/` 主键

### LightId - 轻量级分布式主键

轻量级分布式主键，采用双缓冲机制，使用sequence高效生成64bit数字主键。
ID能保证严格的`单调递增`(升序)，但不保证连续，其long型的64bit构成如下。

 * long = `1-bit:0固定`+ `8-bit:验证`+ `10-bit:区块`+`45-bit:序号`
 * `0固定`，保证ID为非负数。
 * `验证`，默认为0填充，使用时，用Crc8Long生成，可构造伪随机数。
 * `区块`，block(10bit=1023)，用来区分不同的主键生产中心。
 * `序号`，sequence(45bit=35184372088831)，`区块内`唯一递增序号。

因为有效位只有55bit，所以存在以下特点。

 * 生产中心，最多1024个。通常一个数据中心，有一个生产中心。
 * 若每秒消费5万ID，能连续22年，(2^45 -1)/(365x24x3600x50000)=22.3
 * sequence和时间无关，所以并发上限和使用年限，只根ID消费者能力有关。
 * sequence和进程无关，所以能以key-value形式，产生不同类别的ID。
 

系统提供默认的双缓冲实现，在`Loader`保证唯一升序的情况下，能够。

 * 保证全局block-name生成唯一id。
 * 线程内id升序，不同线程无法保证升序。
 * 当id剩余不足某值（80%）时，异步补充id，无锁（非读写锁）
 * 切换id段时，保证最小同步段，控制保护资源的范围。
 * 根据id的每秒消耗数动态调整请求数量，预留60秒的使用量。
 * 当缓冲id完全耗尽时，保证只有一个加载，其他等候成功或超时。
 * 支持手动进行预加载(preload)所有可用id。
 * 支持手动或定时清除错误。
 * 支持手动调整运行时参数。

### LightIdBufferedProvider - 高性能，轻量级锁，双缓冲
 
轻量级锁，高性能，双缓冲 light-id 提供者。

实测性能，高于busy-wait+读写锁或大粒度的组合锁或同步块。
效能瓶颈在loader的io上，需要根据消耗量，优化maxCount。

共存在以下3类线程，且读线程会升级为写线程，甚至加载线程。
同一时刻，有多个读线程，但只有唯一写线程，唯一的加载线程。

 * 读线程，正常的light-id调用者
 * 写线程，读线程升级或加载线程，为buffer追加片段(segment)
 * 加载线程，异步线程或读线程升级，通过loader加载segment

双缓冲的运行机制如下，会跟进id的使用量，自动控制预加载量，但不超过maxCount。

 * 当Id余量低于20%时，唯一异步预加载`60s内最大使用量`或`maxCount`
 * 当Id余量用尽时，读线程升级为写线程，其他读线程等待，直到被唤醒或超时
 * 当读线程升级写线程时，存在loader，此读线程自旋忙等后，切换buffer。

### LightIdUtil - 对lightId特征long操作

对 lightId，long和sequence进行验证，转换的工具类。

## `img/` 主键

 * ImageIoFix - problem-using-imageio-write-jpg-file
 * StreamJpg - BufferedImage 写入
 * Watermark - 水印
 * ZoomRotateCrop - 缩放旋转剪切

## `io/` IO及fs操作

 * CircleInputStream - 可循环读取的流
 * CompatibleObjectStream - 当serialVersionUID不兼容时，采用本地Class反序列化
 * DirHasher 本地文件系统不可保存太多文件
 * Exec - 单线程同步执行，高级功能用Apache Commons Exec
 * InputStreams - 不使用commons的补位
 * Zipper 递归zip/unzip

## `jaxb/` xml

 * StringMapXmlWriter - 只把顶层元素变成key-value的map，用来做参数签名

## `lock/` 锁

 * GlobalLock - 全局锁接口
 * JvmStaticGlobalLock - 基于WeakReference的Jvm全局锁 

## `math/` 行业中的数学算法

 * AnyIntegerUtil - int,long,Number,String间的恩怨
 * AverageDecimal - 平均数 20/6=`[3.33, 3.33, 3.34, 3.33, 3.33, 3.34]`
 * BalanceDecimal - 平衡数， 按权重分割数值
 * BigDecimalUtil - 处理null的Decimal运算工具
 * ComparableUtil - Null 不参与比较的比较器
 * RatioNumber - 比例数，物品消耗的换算表示法。

## `netx/` 网络通讯

 * SslTrustAll - 信任所以证书，使爬虫不报错
 * SslVersion - jdk-8-will-use-tls-12-as-default

## `page/` 分页功能

 * PageQuery - 分页查询
 * PageResult - 分页结果
 * PageUtil - 分页工具，使用`-1+1`算法，不是if-else

## `pain/` 异常痛苦

 * BadArgsException - 多国语和枚举版IllegalArgumentException
 * BadStateException - 多国语和枚举版IllegalStateException
 * CodeException - 多国语和枚举版RuntimeException
 * IllegalRequestException - 不合法的请求
 * IllegalResponseException - 因状态问题无法正常响应
 * IORuntimeException - Runtime版IOException
 * NoStackRuntimeException - 无需填充堆栈的异常，用于性能优先场景，堆栈无用的场景
 * ThrowableUtil - Throwable堆栈和cause工具
 * TimeoutRuntimeException - Runtime版TimeoutException

## `stat/` 统计与监控

* GitStat - 对git提交按日期作者统计，或在mysql建表保存
* JvmStat - 返回当前jvm的Cpu，Mem，Thread信息
* LogStat - 对日志增长，关键词进行收集

## `text/` 全半角，白字符，格式化工具

 * BuilderHelper - null友好碎片少的StringBuilder操作
 * BuilderHolder - 减少碎片的StringBuilder
 * CaseSwitcher - camel,snake,pascal,kebab命名转换
 * FormatUtil - printf的`%`;logbak的`{}`;message的`{0}`，参数填充
 * FullCharUtil - 全角字符工具
 * HalfCharUtil - 半角字符工具
 * StringTemplate - 字符串模板，免替换尴尬，可读性好，性能优
 * WhiteUtil - 弥补java trim的不足，更多Whitespace处理
 * Wildcard - 快速的通配符匹配，仅支持`?`和`*`

## `time/` 时间日期

 * DateFormatter - 线程安全的，比正常formatter要快
 * DateLocaling - LocalDateTime和时区的故事
 * DateNumber - 日期转化成数字的双向转化
 * DateParser - 更高效兼容的解析日期数字的字符串
 * SlideDate - 包装了OffsetClock的会计日期工具

## `tk` token和ticket

用于私钥凭证，需要中心控制又去中心的凭证，在无意义session和庞大jwt体系之间的场景。
session的replication和sticky在水平扩展上十分稳定成熟，如redis和Hazelcast。
JsonWeb系列体系强大，多在数据交换且安全要求较高的场景，凭证领域并非其强项。

场景的应用场景是RememberMe或读取异步任务结果的凭证。

在RememberMe中，biz-data可包括uid，而sig-data中，需要把用户密码和盐追加进行验签。
当用户再次登录，密码修改，或凭证过期时，都可以对ticket的有效性进行判断。


### Ticket - 有中心权力的去中心凭证

特点是短小，可过期，可踢人，可验签，有一定业务意义，代替无意义的随机token，格式大概如下。

`pub-part` + `.` + `biz-part` + `.` + `sig-part`，biz-part为可选项。

* 公开部分(pub-part) - 必选，以`-`分隔的字母数字，含以下字段
  - mod - `az09`，约定模式，对加密，签名，biz结构的约定
  - exp - long，过期时点，从1970-01-01起的秒数
  - seq - int，签发序号，递增不连续，一般小于10有特别定义。
* 业务部分(biz-part) - `az09-_`，可选，不超过1k，在mod中定义
  - base64为url-safe，no pad格式
* 签名部分(sig-part) - `az09-_`，可选，一般50字符内，在mod中定义
  - base64同上

以下字段约定，用途分别解释，使用时需要存在以下约定数据。

* salt - 加密或签名秘钥，如`加盐`，对称秘钥，非对称私钥。
* biz-data - 加密前的业务数据，加密后为biz-part
* sig-data - 签名信息，`pub-part` + (`.` + `biz-part`)?。

则，公开部分对应的业务场景和说明如下，

* `mod` 服务端或同用户协商制定，包括加密和签名算法，数据格式，用户字段等，如内定。
  - any = 仅原文解析和合并各个字段。
  - am0 = aes128(biz-data, salt) + md5(sig-data) 无盐Md5
  - am1 = aes128(biz-data, salt) + md5(sig-data + salt) 加盐Md5
  - ah1 = aes128(biz-data, salt) + HmacMd5(sig-data, salt) Hmac验签

* `exp` 凭证过期的秒数，从1970-01-01起的`秒数`，不是毫秒。
  - 到期凭证，client需要到服务器续签。
  - 服务器端自行确定行为，如续签，拒绝，重发等。

* `seq` 签发序号，生成凭证时递增，续签时不变，seq不同时，须重新获得凭证。
  - 当用户登录时，生成凭证，其他端再次登录时，seq增加，则可踢出其他端凭证。
  - 当凭证过期时，未发生再次登录，可以同seq续签新凭证。
  - 0 表示无需验证序号，一般用于弱权限场合，如临时凭证
  
凭证，可以在http的header，session和url-string中传递。
 * 无私钥者，无法验签和解密，仅可以使用pub-part信息。
 * 有私钥者，通过验签和解密，判断凭证有效性，获取业务内容。
 * 无私钥者，且需要验签的情况，可不加盐，不用hmac签名

### 默认的基本实现和工具类

 * `AnyTicket` - 可以解析任何mod的ticket
 * `TicketHelp` - 提供了ticket解析，验签的基本工具类
