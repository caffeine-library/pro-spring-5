package SpringStart;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericXmlApplicationContext;

public class DestructiveBean implements ApplicationContextAware {
    public void destroy(){
        System.out.println("destroy() invoked");
    }

    public static void main(String[] args) throws Exception{
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
        ctx.load("destroy_test.xml");
        ctx.refresh();

        DestructiveBean bean = (DestructiveBean) ctx.getBean("destructiveBean");

        System.out.println("shutdown hook is registered on ctx");
        ctx.registerShutdownHook();

        System.out.println("before ctx.destroy()");
        ctx.close();
        System.out.println("after ctx.destroy()");

        int sec = 0;
        while(sec<=5){
            Thread.sleep(1000);
            System.out.println(++sec);
            if(sec==3){
                System.exit(1); // 강제 종료
            }
        }

        System.out.println("End of the program");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}