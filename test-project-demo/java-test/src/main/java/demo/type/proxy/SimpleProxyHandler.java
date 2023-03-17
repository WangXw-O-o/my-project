package demo.type.proxy;

import demo.type.proxy.inter.OneInterface;
import demo.type.proxy.inter.impl.OneInterfaceImpl;
import demo.type.proxy.inter.impl.OneInterfaceImpl2;

import java.lang.reflect.Proxy;

public class SimpleProxyHandler {

    public static void main(String[] args) {
        OneInterfaceImpl impl1 = new OneInterfaceImpl();
        OneInterfaceImpl2 impl2 = new OneInterfaceImpl2();
        /*
        创建一个代理，需要三个参数：
            类加载器：直接取要代理接口的类加载器
            接口列表：直接给 接口.class 就可以
            动态代理处理器实现：需要自己实现的一个动态处理器
        */
        OneInterface proxy = (OneInterface) Proxy.newProxyInstance(
                OneInterface.class.getClassLoader(),
                new Class[]{OneInterface.class},
                new DynamicProxyHandler(impl1));
        //执行时，将请求转发的处理器上
        proxy.doSomething();
    }
}
