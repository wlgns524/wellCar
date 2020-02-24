package com.rightcode.wellcar.RxJava;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Event {
    Class<?> value();
}

