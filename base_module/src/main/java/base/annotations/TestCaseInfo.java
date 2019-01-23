package base.annotations;


import java.lang.annotation.*;

@Documented
@Inherited
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface TestCaseInfo {
    String owner() default "";
    String caseNo() default "";
}
