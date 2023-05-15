package demo.annotate;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TestMain {

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        AnnotateDemo annotateDemo = new AnnotateDemo();
        //获取类上的注解
        TestAnnotate classTestAnnotate = annotateDemo.getClass().getAnnotation(TestAnnotate.class);
        String name = classTestAnnotate.name();
        String value = classTestAnnotate.value();
        System.out.println(name + " " + value);

        //获取方法上的注解
        Method test = annotateDemo.getClass().getMethod("test");
        TestAnnotate annotation = test.getAnnotation(TestAnnotate.class);
        name = annotation.name();
        value = annotation.value();
        System.out.println(name + " " + value);
    }

}
