package org.scalatra.jrebel;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;

public class ScalatraKernelReloader {
    private static Long lastReload = System.currentTimeMillis();
    private static Map<Object, Long> times = new WeakHashMap<Object, Long>();
    
    public static void register(Object o) {
        times.put(o, System.currentTimeMillis());
    }
    
    public static boolean needsReload(Object o) {
        return lastReload > times.get(o);
    }
    
    public static void classReloaded() {
        lastReload = System.currentTimeMillis();
    }
    
    public static void reload(Object o) throws Exception {
        times.put(o, System.currentTimeMillis());
        System.out.println("TODO Reload the routes here");
    }
}
