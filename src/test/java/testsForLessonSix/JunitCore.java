package testsForLessonSix;

import org.junit.jupiter.api.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JunitCore {
    public static void main(String[] args) throws Exception {
        // lookup classes with annotation @Test
        // here we go with class SimpleTest.class

        Class clazz = SimpleTest.class;

        // run all methods with @Test
        for (Method method : clazz.getDeclaredMethods()) {
            BeforeEach methodAnnotationBeforeEach = method.getAnnotation(BeforeEach.class);
            Test methodAnnotationTest = method.getAnnotation(Test.class);
            if (methodAnnotationTest != null || methodAnnotationBeforeEach != null) {
                // run method with @Test
                try {
                    method.invoke(clazz.getConstructor().newInstance());
                } catch (InvocationTargetException e) {
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


        void runBeforeEach(){
            clazz.getMethod("BeforeEach", clazz));
                BeforeEach methodAnnotationBeforeEach = method.getAnnotation(BeforeEach.class);
                if (methodAnnotationBeforeEach != null) {
                    // run method with @BeforeEach
                    try {
                        method.invoke(clazz.getConstructor().newInstance());
                    } catch (InvocationTargetException e) {
                        System.out.println("Wrong setting: " + method.getName());
                        continue;
                    }
                }
        }

        // run all methods with @Test

        // print results
    }
}
