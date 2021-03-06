# Unify.clear
异常、检验、日志统一处理框架

该模块提供了AOP（Filter、Interceptor也可）的方式，去统一做 异常处理、入参校验、日志打印等基础的操作。

优点

1. 去除模式化代码，专注写正常业务逻辑。分离正常业务和异常处理，代码更清晰，可读性好
   1. 参数校验无需手动校验，使用Bean Validation（JSR 380），可读性好，专注业务逻辑
2. 统一打印日志，定义好打印格式，天然就是规范，无需手动打印，降低重复代码带来的错误
3. 统一处理异常，整个系统的错误码统一，避免手动编码带来的错误码不一致问题
4. 提供多种级别（方法、类、全局）的配置，可以方便的配置校验、日志、异常处理等开关
5. 提供扩展点和相应的工具类，方便扩展

---
##### 注册与默认实现

1. 日志部分
   1. MDC.put相关参数，自行打印
   2. 提供默认实现，全部打印
2. 流程
先初始化上线文，根据注册的信息新增组件替换or使用默认组件