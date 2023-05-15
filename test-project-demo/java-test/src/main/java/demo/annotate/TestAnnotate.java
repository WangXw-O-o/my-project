package demo.annotate;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;

@Documented
@Target({TYPE,METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface TestAnnotate {

    String name();

    String value();

}
