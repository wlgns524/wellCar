package com.rightcode.wellcar.RxJava;


import com.rightcode.wellcar.Util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {
    private static RxBus instance;

    private Subject<Object, Object> bus = new SerializedSubject<>(PublishSubject.create());
    private ConcurrentHashMap<Object, ArrayList<Method>> handlers = new ConcurrentHashMap<>(0);

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }

        return instance;
    }

    private RxBus() {
        bus.subscribe(object -> {
            for (Map.Entry<Object, ArrayList<Method>> entrySet : handlers.entrySet()) {
                for (Method m : entrySet.getValue()) {
                    if (m.getAnnotation(Event.class).value().equals(object.getClass())) {
                        try {
                            if (m.getParameterTypes().length > 1) {
                                throw new InvalidEventHandler("Handler method must be with a single param of type " + object.getClass().getCanonicalName());
                            }
                            m.invoke(entrySet.getKey(), object);
                        } catch (IllegalAccessException | InvocationTargetException | InvalidEventHandler e) {
                            Log.e(e);
                        }
                    }
                }
            }
        });
    }

    /**
     * Sending an event to the Rx Event Bus.
     *
     * @param event
     */
    public static void send(Object event) {
        getInstance().bus.onNext(event);
    }

    /**
     * Register the given subscriber object to receive events.
     * Must call {@link #unregister(Object)} once it doesn't want to receive events.
     *
     * @param subscriber Object that will receive events once it's registered
     */
    public static void register(Object subscriber) {
        ArrayList<Method> handlerMethods = new ArrayList<>(0);
        for (Method m : subscriber.getClass().getMethods()) {
            if (m.isAnnotationPresent(Event.class)) {
                handlerMethods.add(m);
            }
        }
        getInstance().handlers.put(subscriber, handlerMethods);
        Log.d("Registered: " + subscriber.getClass().getSimpleName() + ", Subscribed objects: " + getInstance().handlers.size() + " with " + getInstance().handlers.values().size() + " handlers");
    }

    /**
     * Unregister a subscriber from the Rx Bus.
     * Once it unregistered, it won't receive events anymore.
     *
     * @param subscriber Object that won't receive events anymore once it's unregistered
     */
    public static void unregister(Object subscriber) {
        getInstance().handlers.remove(subscriber);
        Log.d("Unregistered: " + subscriber.getClass().getSimpleName() + ", Subscribed objects: " + getInstance().handlers.size() + " with " + getInstance().handlers.values().size() + " handlers");
    }
}
