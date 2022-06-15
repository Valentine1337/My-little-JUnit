package core;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JunitCore {
    public static void main(String[] args) throws Exception {
        Class[] classesFromPackage = getClassesFromPackage("tests");
        for (Class clazz : classesFromPackage) {
            beforeAllAnnotation(clazz);
            beforeEachAnnotation(clazz);
            testAnnotation(clazz);
        }
    }

    private static Class[] getClassesFromPackage(String packageName) throws Exception {
        int numberOfClasses = 0;
        //Scan selected package and get classes
        try (ScanResult scanResult = new ClassGraph()
                .whitelistPackages(packageName)
                .scan()) {
            for (ClassInfo classInfo : scanResult.getAllClasses()) {
                numberOfClasses++;
            }
            String[] classesNames = new String[numberOfClasses];
            for (ClassInfo classInfo : scanResult.getAllClasses()) {
                classesNames[numberOfClasses - 1] = classInfo.getSimpleName();
                numberOfClasses--;
            }
            Class[] classes = new Class[classesNames.length];
            for (int i = 0; i < classesNames.length; i++) {
                classes[i] = Class.forName("tests." + classesNames[i]);
            }
            return classes;
        }
    }

    private static void beforeAllAnnotation(Class clazz) throws Exception {
        for (Method method : clazz.getDeclaredMethods()) {
            //Take all methods with this annotation in class
            BeforeAll methodAnnotation = method.getAnnotation(BeforeAll.class);
            if (methodAnnotation != null) {
                //Invoke method, if there were a mistakes print them
                try {
                    method.invoke(clazz.getConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof NumberFormatException) {
                        System.out.println("Неправильный тип параметра");
                    } else if (e.getCause() instanceof NullPointerException) {
                        System.out.println("Пустое значение параметра недопустимо");
                    } else {
                        System.out.println("Неопознанная ошибка, проверьте заданные параметры");
                    }
                }
            }
        }
    }

    private static void beforeEachAnnotation(Class clazz) throws Exception {
        for (Method method : clazz.getDeclaredMethods()) {
            //Take all methods with this annotation in class
            BeforeEach methodAnnotation = method.getAnnotation(BeforeEach.class);
            if (methodAnnotation != null) {
                //Invoke method, if there were a mistakes print them
                try {
                    method.invoke(clazz.getConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    if (e.getCause() instanceof NumberFormatException) {
                        System.out.println("Неправильный тип параметра в тесте " + method.getName());
                    } else if (e.getCause() instanceof NullPointerException) {
                        System.out.println("Пустое значение параметра в тесте " + method.getName());
                    } else {
                        System.out.println("Неопознанная ошибка, проверьте заданные параметры в тесте "
                                + method.getName());
                    }
                }
            }
        }
    }

    private static void testAnnotation(Class clazz) throws Exception {
        for (Method method : clazz.getDeclaredMethods()) {
            //Take all methods with this annotation in class
            Test methodAnnotationTest = method.getAnnotation(Test.class);
            if (methodAnnotationTest != null) {
                try {
                    method.invoke(clazz.getConstructor().newInstance());
                } catch (InvocationTargetException e) {
                    //print results
                    if (e.getCause() instanceof AssertionError) {
                        System.out.println("Test failed: " + method.getName());
                        continue;
                    } else {
                        System.out.println("Test broken: " + method.getName());
                        continue;
                    }
                }
                System.out.println("Test passed: " + method.getName());
            }
        }
    }
}
