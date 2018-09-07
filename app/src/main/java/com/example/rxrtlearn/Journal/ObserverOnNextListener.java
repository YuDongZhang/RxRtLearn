package com.example.rxrtlearn.Journal;

/**
 * 针对重写方法的封装最好的方法就是使用接口+implements/继承的方式实现。
 仔细分析会发现，其实除了onNext涉及数据处理，其余三个方法都可以进行模板化处理，比如显示一个Toast，输出错误信息等；
 当然如果你的业务需要，也可以按照onNext特别处理。
 在之前代码的基础上。
 * @param <T>
 */

public interface ObserverOnNextListener<T> {
    void onNext(T t);
}