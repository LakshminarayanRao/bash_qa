package com.gsngames.qa.vimana.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Generic Annotation to provide test metadata related to TM,BUG and Data driven tests
 * @author lnr
 *
 */
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.TYPE })
public @interface TestMetadata {

    int[] bugId() default { -1 };

    String dataSetName() default "";
    
    String userStory() default "";
    
    String scenario() default "";
    
    String steps() default "";

}
