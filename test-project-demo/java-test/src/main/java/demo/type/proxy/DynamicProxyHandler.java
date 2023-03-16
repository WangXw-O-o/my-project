package demo.type.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 动态代理处理器
 */
public class DynamicProxyHandler implements InvocationHandler {

    //被代理的对象
    private final Object proxied;

    public DynamicProxyHandler(Object proxied) {
        this.proxied = proxied;
    }

    /**
     * 代理过程
     * @param proxy  代理对象
     * @param method 方法
     * @param args   参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("DynamicProxyHandler.invoke[**** proxy: " + proxy.getClass().getName()
                + "  method: " + method.getName()
                + " args: " + Arrays.toString(args) + "]");
        //请求转发到被代理的对象上，即执行被代理对象上的对应方法
        return method.invoke(proxied, args);
    }

}
