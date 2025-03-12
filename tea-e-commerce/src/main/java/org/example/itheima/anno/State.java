package org.example.itheima.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.itheima.validation.StateValidation;

import java.lang.annotation.*;
import java.util.List;

@Documented//元注解
@Target({ElementType.FIELD})//元注解
@Retention(RetentionPolicy.RUNTIME)//元注解
@Constraint(validatedBy = {StateValidation.class})
public @interface State {
    //提供校验失败的信息
    String message() default "state的参数只能是 已发布 或者 草稿";
    //指定分组
    Class<?>[] groups() default {};
    //负载 获取到State注解的附加信息
    Class<? extends Payload>[] payload() default {};
}
