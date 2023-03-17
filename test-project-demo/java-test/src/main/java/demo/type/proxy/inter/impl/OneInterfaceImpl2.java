package demo.type.proxy.inter.impl;

import demo.type.proxy.inter.OneInterface;

public class OneInterfaceImpl2 implements OneInterface {

    @Override
    public void doSomething() {
        System.out.println("OneInterfaceImpl222222.doSomething......");
    }

    @Override
    public String returnStringMethod(String param) {
        System.out.println("OneInterfaceImpl222222.returnStringMethod......");
        return "OneInterfaceImpl222222.returnStringMethod()";
    }
}
