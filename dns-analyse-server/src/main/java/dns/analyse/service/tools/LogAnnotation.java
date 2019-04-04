package dns.analyse.service.tools;

import dns.analyse.Enum.LogTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public  @interface LogAnnotation {
    /** 模块 */
    String title() default "";
    /** 功能 */
    String action() default "";
    /** 方法日志等级 */
    LogTypeEnum type() default LogTypeEnum.INFO;

}
