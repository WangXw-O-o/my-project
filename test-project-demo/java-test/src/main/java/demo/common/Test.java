package demo.common;

public class Test {

    public static void main(String[] args) {
        int i = 1234445567;
        Long l = 1234445567L;
        if (l.equals(Long.valueOf(i))) {
            System.out.println("-------");
        }
    }

}
