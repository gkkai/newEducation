package com.meiliangzi.app.model.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HttpRequest {
    String[] arguments();

    Class<?> resultClass();

    String url();

    String refreshMethod();
}

