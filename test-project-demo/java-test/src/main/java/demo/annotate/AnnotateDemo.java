package demo.annotate;

@TestAnnotate(name = "AnnotateDemo", value = ">>>>class<<<<")
public class AnnotateDemo {

    @TestAnnotate(name = "AnnotateDemo", value = ">>>>method<<<<")
    public void test() {
        System.out.println("---- AnnotateDemo.test -----");
    }

}
