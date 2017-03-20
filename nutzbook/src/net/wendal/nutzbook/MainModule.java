package net.wendal.nutzbook;

import org.nutz.mvc.annotation.ChainBy;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

@ChainBy(args="mvc/nutzbook-mvc-chain.js")
@Localization(value="msg/", defaultLocalizationKey="zh-CN")
@SetupBy(value=MainSetup.class)
@IocBy(type=ComboIocProvider.class, args={"*js", "ioc/",
        "*anno", "net.wendal.nutzbook",
        "*tx", // 事务拦截 aop
        "*async"}) // 异步执行aop
@Modules(scanPackage=true)
public class MainModule {

}
