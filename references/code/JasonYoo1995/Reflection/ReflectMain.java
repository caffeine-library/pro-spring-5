package Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

interface MyInterface {};

class MySuperClass {};

class MyClass extends MySuperClass implements MyInterface {
    String myField = "My Field";

    public MyClass() {
        System.out.println("Basic Constructor Invoked");
    }

    public MyClass(int constructorArg0, int constructorArg1) {
        System.out.println("constructorArg0 = " + constructorArg0);
        System.out.println("constructorArg1 = " + constructorArg1);
    }

    public int myMethod(int methodArg0, int methodArg1) {
        System.out.println(methodArg0 + " + " + methodArg1);
        return methodArg0 + methodArg1;
    }
};

public class ReflectMain {
    public static void main(String[] args) throws Exception {
        Class reflectClass = extractClassObject();

        printClassInformations(reflectClass);
        invokeMethod(reflectClass);
        createObject(reflectClass);
        writeField(reflectClass);
    }

    /** 클래스 정보를 담고 있는 클래스 객체 추출 */
    static Class extractClassObject(){
        // 1
        Class reflectClass = MyClass.class;
        // 2
//        Class reflectClass = Class.forName("com.company.MyClass");
        // 3
//        MyClass myClass = new MyClass();
//        Class reflectClass = myClass.getClass();
        return reflectClass;
    }

    /** 클래스 정보(메서드, 필드, 생성자, 인터페이스, 부모 클래스 등) 얻기 */
    static void printClassInformations(Class reflectClass){
        Field[] reflectField = reflectClass.getDeclaredFields();
        Method[] reflectMethod = reflectClass.getMethods();
        Constructor[] reflectConstructor = reflectClass.getConstructors();
        Class[] reflectInterface = reflectClass.getInterfaces();
        Class reflectSuperClass = reflectClass.getSuperclass();

        System.out.println("<Fields>");
        for(Field fields : reflectField){
            System.out.println(fields);
        }
        System.out.println("<Methods>");
        for(Method methods : reflectMethod){
            System.out.println(methods);
        }
        System.out.println("<Constructors>");
        for(Constructor constructors : reflectConstructor){
            System.out.println(constructors);
        }
        System.out.println("<Interfaces>");
        for(Class interfaces : reflectInterface){
            System.out.println(interfaces);
        }
        System.out.println("<SuperClass>");
        System.out.println(reflectSuperClass);
    }

    /** 메서드 호출 */
    static void invokeMethod(Class reflectClass) throws Exception {
        // 2개의 int를 매개변수로 갖는 myMethod라는 이름의 메서드 얻기
        String methodName = "myMethod";
        Class parameterType[] = new Class[2];
        parameterType[0] = Integer.TYPE;
        parameterType[1] = Integer.TYPE;
        Method reflectMethod = reflectClass.getMethod(methodName, parameterType);

        // 메서드 호출
        Object reflectObject = reflectClass.newInstance();
        Object argList[] = new Object[2];
        argList[0] = 10;
        argList[1] = 20;
        Object returnObject = reflectMethod.invoke(reflectObject, argList);

        // 호출 결과 출력
        Integer returnValue = (Integer) returnObject;
        System.out.println(returnValue.intValue());
    }

    /** 객체 생성 */
    static void createObject(Class reflectClass) throws Exception {
        // 2개의 int를 매개변수로 갖는 생성자 얻기
        Class parameterType[] = new Class[2];
        parameterType[0] = Integer.TYPE;
        parameterType[1] = Integer.TYPE;
        Constructor reflectConstructor = reflectClass.getConstructor(parameterType);

        // 생성자 호출 및 객체 생성
        Object argList[] = new Object[2];
        argList[0] = 30;
        argList[1] = 40;
        Object returnObject = reflectConstructor.newInstance(argList);

        // 생성된 객체의 필드 출력
        System.out.println( ((MyClass) returnObject).myField );
    }

    /** 필드값 변경 */
    static void writeField(Class reflectClass) throws Exception {
        // 필드 얻기
        String fieldName = "myField";
        Field reflectField = reflectClass.getDeclaredField(fieldName);
        
        // 변경 전
        MyClass myClass = new MyClass();
        System.out.println(myClass.myField);
        
        // 변경 후
        reflectField.set(myClass, "My New Field");
        System.out.println(myClass.myField);
    }
}
