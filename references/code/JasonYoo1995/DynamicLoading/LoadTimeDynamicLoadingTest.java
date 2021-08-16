package DynamicLoading;

/**
 * cmd 명령
 * 컴파일 : C:\Projects\Java Projects\Spring Study\src\main\java>javac DynamicLoading/LoadTimeDynamicLoadingTest.java -encoding UTF-8
 * 실행 : C:\Projects\Java Projects\Spring Study\src\main\java>java -verbose:class DynamicLoading.LoadTimeDynamicLoadingTest
 */

public class LoadTimeDynamicLoadingTest {
    static B b;
    static void sleep(int time) throws Exception{
        for (int i = 1; i <= time; i++) {
            System.out.println("SLEEP " + i + "sec");
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("My String");

        sleep(3);

        b = new B();
    }
}
