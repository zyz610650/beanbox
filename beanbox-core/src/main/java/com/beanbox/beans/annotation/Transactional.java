package com.beanbox.beans.annotation;


import com.beanbox.enums.Isolation;
import com.beanbox.enums.Propagation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface  Transactional {
    /**
     * 事务隔离级别
     * @return
     */
    Isolation isolatiion() default Isolation.REPEATABLE_READ;

    /**
     * 事务传播机制
     * @return
     */
    Propagation propagation() default Propagation.PROPAGATION_REQUIRED;
}
