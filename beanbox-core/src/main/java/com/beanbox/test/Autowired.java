package com.beanbox.test;

import java.lang.annotation.*;

/**
 * @author: @zyz
 */

@Documented
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.FIELD,ElementType.METHOD})
public @interface Autowired {
}
