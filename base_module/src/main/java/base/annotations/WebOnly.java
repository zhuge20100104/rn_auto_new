package base.annotations;

import java.lang.annotation.*;



/**
 * Annotation for web only methods in Search Tools.
 */

@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WebOnly {
}
