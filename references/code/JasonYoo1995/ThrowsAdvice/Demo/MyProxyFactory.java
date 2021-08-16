package ThrowsAdvice.Demo;

import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class MyProxyFactory {
    void addAdvice(ThrowsAdvice advice) {
        Method[] methods = advice.getClass().getMethods();
        for (Method m : methods) {
            if (m.getName().equals("afterThrowing")) {
                Parameter[] parameters = m.getParameters();
                Class exceptionType = parameters[parameters.length - 1].getType();
                if (exceptionType.equals(MyException.class)) {
                    System.out.println("MyException에 대한 핸들러 등록");
                } else if (exceptionType.equals(IllegalArgumentException.class)) {
                    System.out.println("IllegalArgumentException에 대한 핸들러 등록");
                } else {
                    System.out.println("Exception에 대한 핸들러 등록");
                }
            }
        }
    }
}
