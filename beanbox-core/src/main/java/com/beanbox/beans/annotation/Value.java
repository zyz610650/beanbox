package com.beanbox.beans.annotation;

import java.lang.annotation.*;

/**
 * @author: @zyz
 */

@Target ({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
@Retention (RetentionPolicy.RUNTIME)
@Documented
public @interface Value {

	String value();
}
