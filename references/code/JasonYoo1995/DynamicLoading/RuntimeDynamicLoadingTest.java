package DynamicLoading;

/**
 * cmd 명령
 * 컴파일 : C:\Projects\Java Projects\Spring Study\src\main\java>javac DynamicLoading/RuntimeDynamicLoadingTest.java -encoding UTF-8
 * 실행 : C:\Projects\Java Projects\Spring Study\src\main\java>java -verbose:class DynamicLoading.RuntimeDynamicLoadingTest A
 *       또는 C:\Projects\Java Projects\Spring Study\src\main\java>java -verbose:class DynamicLoading.RuntimeDynamicLoadingTest B
 */

public class RuntimeDynamicLoadingTest {
    static void sleep(int time) throws Exception{
        for (int i = 1; i <= time; i++) {
            System.out.println("SLEEP " + i + "sec");
            Thread.sleep(1000);
        }
    }

    public static void main(String[] args) throws Exception {
        sleep(3);

        Class.forName("DynamicLoading." + args[0]);

        sleep(3);

//        System.out.println("Before 1st A created");
//        new A();
//        System.out.println("After 1st A created");
//
//        sleep(3);
//
//        System.out.println("Before B created");
//        new B();
//        System.out.println("After B created");
//
//        sleep(3);
//
//        System.out.println("Before 2nd A created");
//        new A();
//        System.out.println("After 2nd A created");
    }
}
