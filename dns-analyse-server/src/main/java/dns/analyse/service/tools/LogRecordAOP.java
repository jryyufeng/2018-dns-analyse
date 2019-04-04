package dns.analyse.service.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 * 用于关键方法日志记录切面
 */
@Aspect
@Component("logAspect")
public class LogRecordAOP {
    private static final Logger log = LogManager.getLogger(LogRecordAOP.class.getName());

    /**
     * 配置织入点
     */
    @Pointcut("@annotation(dns.analyse.service.tools.LogAnnotation)")
    public void logPointcut(){

    }

    /**
     * 前置通知 用于拦截操作 在方法返回后执行
     * @Param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointcut()")
    public void doBefore(JoinPoint joinPoint) {
        handleLog(joinPoint,null);
    }

    /**
     * 异常信息拦截
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(value = "logPointcut()",throwing = "e")
    public void doAfter(JoinPoint joinPoint,Exception e){
        handleLog(joinPoint,e);
    }

    private void handleLog(JoinPoint joinPoint, Exception e) {
        try {
            // 获得注解
            LogAnnotation controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }
            // 获得方法名称
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            String action = controllerLog.action();
            String title = controllerLog.title();
            String type = controllerLog.type().getValue();
            //打印日志，如有需要还可以存入数据库
            log.info(">>>>>>>>>>>>>模块名称：{}",title);
            log.info(">>>>>>>>>>>>>操作名称：{}",action);
            log.info(">>>>>>>>>>>>>方法等级：{}",type);
            log.info(">>>>>>>>>>>>>类名：{}",className);
            log.info(">>>>>>>>>>>>>方法名：{}",methodName);
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }
    private static LogAnnotation getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(LogAnnotation.class);
        }
        return null;
    }


}
