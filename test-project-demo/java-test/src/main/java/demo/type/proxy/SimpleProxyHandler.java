package demo.type.proxy;

import demo.type.proxy.inter.OneInterface;
import demo.type.proxy.inter.impl.OneInterfaceImpl;

import java.lang.reflect.Proxy;

public class SimpleProxyHandler {

    /**
     * 每个方法执行时，实际上都是由 动态代理处理器 来调用 被代理对象 的对应方法
     * @param oneInterface
     */
    public static void consumer(OneInterface oneInterface) {
        oneInterface.doSomething();
        oneInterface.returnStringMethod("SimpleProxyHandler_returnStringMethod");
    }

    public static void main(String[] args) {
        OneInterfaceImpl impl = new OneInterfaceImpl();
        /*
        获取一个代理，需要三个参数：
            类加载器：直接取要代理接口的类加载器
            接口列表：
            动态代理处理器实现：需要自己实现的一个动态处理器
        */

        OneInterface proxy = (OneInterface) Proxy.newProxyInstance(
                OneInterface.class.getClassLoader(),
                new Class[]{OneInterface.class},
                new DynamicProxyHandler(impl));
        consumer(proxy);
    }
}
