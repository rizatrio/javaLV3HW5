import annotations.AfterSuite;
import annotations.BeforeSuite;
import annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestRunner {

    public static void start(String className) throws Exception {
        Class c = Class.forName(className);
        start(c);
    }

    public static void start(Class clazz) throws Exception {

        Object testObj = clazz.newInstance();
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> methodList = new ArrayList<>();
        Method beforeMethod = null;
        Method afterMethod = null;

        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (beforeMethod == null) {
                    beforeMethod = method;
                } else {
                    throw new RuntimeException("Больше одного метода с аннотацией BeforeSuite");
                }
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (afterMethod == null) {
                    afterMethod = method;
                } else {
                    throw new RuntimeException("Больше одного метода с аннотацией AfterSuite");
                }
            } else {
                methodList.add(method);
            }
        }

        methodList.sort(Comparator.comparingInt(o -> o.getAnnotation(Test.class).priority()));
        if (beforeMethod != null) {
            beforeMethod.invoke(testObj);
        }
        for (Method method : methodList) {
            method.invoke(testObj);
        }
        if (afterMethod != null) {
            afterMethod.invoke(testObj);
        }

    }
}
