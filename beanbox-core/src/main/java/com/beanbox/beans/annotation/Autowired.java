package com.beanbox.beans.annotation;

import java.lang.annotation.*;

/**
 * @author: @zyz
 */

@Documented
@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.FIELD,ElementType.METHOD,ElementType.CONSTRUCTOR})
public @interface Autowired {
}
