package demo.collection;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MapDemo {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> cMap = new ConcurrentHashMap<>();
        cMap.put("a", "111");
        String a = cMap.get("a");
        Set<Map.Entry<String, String>> entries = cMap.entrySet();
    }

}
