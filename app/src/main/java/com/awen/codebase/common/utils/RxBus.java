package com.awen.codebase.common.utils;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * RxBus事件分发工具类
 * 使用方式：
 *  发送： RxBus.getDefault().post(new XXXEvent());
 */
public class RxBus {

    private final FlowableProcessor<Object> bus;

    private final CompositeDisposable compositeDisposable;

    private RxBus() {
        bus = PublishProcessor.create().toSerialized();
        compositeDisposable = new CompositeDisposable();
    }

    public static RxBus getDefault() {
        return RxBusHolder.INSTANCE;
    }

    private static class RxBusHolder {
        private static final RxBus INSTANCE = new RxBus();
    }

    /**
     * 提供了一个新的事件
     *
     * @param o Object
     */
    public void post(Object o){
        bus.onNext(o);
    }


    /**
     * 注册事件
     * @param disposable
     */
    public void register(Disposable disposable){
        synchronized (compositeDisposable){
            compositeDisposable.add(disposable);
        }
    }


    /**
     * 解注册事件
     * @param disposable
     */
    public void unregister(Disposable disposable){
        synchronized (compositeDisposable){
            compositeDisposable.remove(disposable);
        }
    }


    /**
     * 清理掉所有监听
     */
    public void clearAll(){
        compositeDisposable.clear();
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param eventType Event type
     * @param <T> 对应的Class类型
     * @return Flowable<T>
     */
    public <T> Flowable<T> toFlowable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}
