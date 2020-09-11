# pro.fessional.mirana

POM(.xml), 月女，她有一只神箭，她有一只大猫。  
对guava, commons-lang, commons-io的补充。

![mirana](./mirana_full.png)

## Naming 命名约定

* Null 等效于null的默认值。
* None 等效于empty的默认值。
* Sugar 表示静态线程安全的工具类，可做kotlin语法糖
* Help 表示和业务关联的或有生命周期的辅助类
* Util 表示静态线程安全的工具类

## `best/` 高质量代码

 * ArgsAssert 前置断言 - IllegalArgumentException
 * StateAssert 中间或后置断言 - IllegalStateException

## `bits/` 二进制，字节相关

 * Aes128 - jdk AES/CBC/PKCS5Padding，若AES/CBC/PKCS7Padding，用bouncycastle
 * Base64 - 默认使用 RFC4648_URLSAFE 和 UTF8。支持`+/`和`-_`
 * Bytes - Hex和unicode(`我`(25105)=>'\u6211')
 * Md5 - md5sum, md5check

## `cast/` 类型转换

 * BoxedCastUtil - 包装类和原始类型的转换
 * StringCastUtil - 字符串和其他类型的转换
 * TypedCastUtil - 类型参数，泛型的转换

## `code/` 编码转码

### Crc4Int

根据int数值，生成一个伪随机，可校验的，可解密出原值的int数字。
尽量提高人类可读性和伪随机性。

### Crc8Long, Crc8LongUtil

根据long数值，生成一个伪随机，可校验的，可解密出原值的long数字。
用户可以自定义bit位序列，系统默认提供三种，参考Crc8LongUtil。

适用场景，安全要求一般，暴露的数字ID信息。可以高效的双向解析和校验。

## Excel26Az

按Excel列命名方式进行双向解析（A:1,B:2,...,Z:26,AA:27）

### LeapCode

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

## `data/` 数据传递

 * Arr - 一些Array的操作
 * CodeEnum - 业务code枚举，如多国语，状态
 * DataResult - 携带data的DTO
 * Diff - diff2个集合，如数据集中判断插入，更新，删除
 * Null - 以`空`消除null是我们的目标。
 * PageUtil - 分页工具，使用`-1+1`算法，不是if-else
 * R - Result的场景类
 * Rank - 按多条件顺序来排序
 * U - 内部传递数据的tuple
 * Z - 第一个满足条件(如非null)的数据操作

## `fake/` 伪装数据

 * FakeDate - 生成指定偏移量附近的伪随机日期，保证结果等幂。

## `func/` function构造

 * Fn - distinct和duplicate

## `i18n/` 多国语

 * I18nAware - 标记型接口
 * I18nString - 支持 i18n的String
 * LocaleResolver - 支持 `-`和`_`
 * ZoneIdResolver - 不区分大小写，支持部分命名

## `id/` 主键

### LightId

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

## `io/` IO操作

 * InputStreams - 不使用commons的补位

## `jaxb/` xml

 * StringMapXmlWriter - 只把顶层元素变成key-value的map，用来做参数签名

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

## `pain/` 异常痛苦

 * BadArgsException - 多国语和枚举版IllegalArgumentException
 * BadStateException - 多国语和枚举版IllegalStateException
 * CodeException - 多国语和枚举版RuntimeException
 * ThrowableUtil - Throwable堆栈和cause工具
 * TimeoutRuntimeException - Runtime版TimeoutException

## `text/` 全半角，白字符，格式化工具

 * BuilderHelper - null友好碎片少的StringBuilder操作
 * CaseSwitcher - camel,snake,pascal,kebab命名转换
 * FormatUtil - printf的`%`和logbak的`{}`，截断填充
 * FullCharUtil - 全角字符工具
 * HalfCharUtil - 半角字符工具
 * WhiteUtil - 弥补java trim的不足，更多Whitespace处理

## `time/` 时间日期

 * DateFormatter - 线程安全的，比正常formatter要快
 * DateLocaling - LocalDateTime和时区的故事
 * DateNumber - 日期转化成数字的双向转化
 * DateParser - 更高效兼容的解析日期数字的字符串