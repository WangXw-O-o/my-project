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
     * 代理过程：接口的调用请求都被转发到此方法中
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
        //做一些其他的处理
        //可以什么都不做，或者全部都只做一种事
        //但是此处理器中，在构造代理对象时，绑定了一个实现类（非必须的）
        //那么能使用 method.invoke(proxied, args) 来执行实现类中方法
        return method.invoke(proxied, args);
    }

}
