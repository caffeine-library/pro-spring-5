package ThrowsAdvice.Demo;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Method;

public class SimpleThrowsAdvice implements ThrowsAdvice {
    public static void main(String[] args) {
        ErrorBean errorBean = new ErrorBean();
        ProxyFactory pf = new ProxyFactory();
        pf.setTarget(errorBean);
        SimpleThrowsAdvice simpleThrowsAdvice = new SimpleThrowsAdvice();
        pf.addAdvice(simpleThrowsAdvice);
        ErrorBean proxy = (ErrorBean) pf.getProxy();

        /** 직접 만든 ProxyFactory 시작 */
        MyProxyFactory mpf = new MyProxyFactory();
        mpf.addAdvice(simpleThrowsAdvice);
        /** 직접 만든 ProxyFactory 끝 */

        try{
            proxy.errorProneMethod();
        } catch (Exception ignored){
        }

        try{
            proxy.otherErrorProneMethod();
        } catch (Exception ignored){
        }

    }

    public void afterThrowing(Exception ex) throws Throwable {
        System.out.println("***");
        System.out.println("Generic Exception Capture");
        System.out.println("Caught: " + ex.getClass().getName());
        System.out.println("***\n");
    }

    public void afterThrowing(Method method, Object[] args, Object target, IllegalArgumentException ex) throws Throwable {
        System.out.println("***");
        System.out.println("IllegalArgumnetException Capture");
        System.out.println("Caught: " + ex.getClass().getName());
        System.out.println("Method: " + method.getName());
        System.out.println("***\n");
    }

    public void afterThrowing(Method method, Object[] args, Object target, MyException ex) throws Throwable {
        System.out.println("***");
        System.out.println("Customized Exception Capture");
        System.out.println("Caught: " + ex.getClass().getName());
        System.out.println("Method: " + method.getName());
        System.out.println("***\n");
    }
}
