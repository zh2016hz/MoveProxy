package com.xiaoniu.finance.moveproxy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        //创建一个与代理对象相关联的InvocationHandler
        InvocationHandler stuHandler = new MyInvocationHandler<Person>(stu);
        Class<?> stuProxyClass = Proxy.getProxyClass(Person.class.getClassLoader(), new Class<?>[] {Person.class});
        Constructor<?> constructor = PersonProxy.getConstructor(InvocationHandler.class);
        Person stuProxy = (Person) cons.newInstance(stuHandler);*/
        Student  stu  = new Student("张三");
        //创建一个与代理对象相关联的InvocationHandler
        InvocationHandler stuHandler = new StuInvocationHandler<Person>(stu);
        //创建一个代理对象stuProxy，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
        Person stuProxy = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(), new Class<?>[]{Person.class}, stuHandler);
        stuProxy.giveMoney();
    }

    private class StuInvocationHandler<T> implements InvocationHandler {
        //invocationHandler持有的被代理对象
        T target;

        private StuInvocationHandler(T target) {
            this.target = target;
        }

        /**
         * proxy:代表动态代理对象
         * method：代表正在执行的方法
         * args：代表调用目标方法时传入的实参
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("代理执行" + method.getName() + "方法");
            //代理过程中插入监测方法,计算该方法耗时
//            MonitorUtil.start();
            Log.e("XXXX", "invoke: 做你想做的事情" );
            Object result = method.invoke(target, args);
            Log.e("XXXX", "invoke: XXXXXXXX试完了" );
            return result;
        }
    }
}
