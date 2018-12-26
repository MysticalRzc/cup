package mystical.cup.utils;

import java.util.HashMap;
import java.util.Map;


public class ContextHoldler{

    private static ThreadLocal<Map<String, Map<String, Object>>> threadLocal = new ThreadLocal<Map<String, Map<String, Object>>>() {
        public Map<String, Map<String, Object>> initialValue() {
            return new HashMap<>();
        }
    };

    public static void putAll(String key, Map<String, Object> map) {
        Map<String, Map<String, Object>> container = threadLocal.get();
        container.put(key, map);
    }

    public static void put(String key, String hashKey, Object value) {
        Map<String, Map<String, Object>> container = threadLocal.get();
        if (container.containsKey(key)) {
            container.get(key).put(hashKey, value);
        } else {
            Map<String, Object> map = new HashMap<>();
            map.put(hashKey, value);
            container.put(key, map);
        }
    }

    public static Object get(String key, String hashKey) {
        Map<String, Map<String, Object>> container = threadLocal.get();
        if (!container.containsKey(key)) {
            return null;
        }
        Map<String, Object> map = container.get(key);
        return map.get(hashKey);
    }

    public static Integer delete(String key, String hashKey) {
        Map<String, Map<String, Object>> container = threadLocal.get();
        if (!container.containsKey(key)) {
            return 0;
        }
        Map<String, Object> map = container.get(key);
        if (!map.containsKey(hashKey)) {
            return 0;
        }
        map.remove(hashKey);
        return 1;
    }

    public static Integer delete(String key) {
        Map<String, Map<String, Object>> container = threadLocal.get();
        if (!container.containsKey(key)) {
            return 0;
        }
        container.remove(key);
        return 1;
    }

    public static Map<String, Object> entries(String key) {
        Map<String, Map<String, Object>> container = threadLocal.get();
        return container.get(key);
    }

    public static void clear() {
        threadLocal.remove();
    }
}
