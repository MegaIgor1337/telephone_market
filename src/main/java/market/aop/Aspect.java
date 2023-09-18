package market.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@org.aspectj.lang.annotation.Aspect
@Component
public class Aspect {
    @Pointcut("@within(org.springframework.stereotype.Controller)")
    public void isControllerLayer() {
    }

    @Pointcut("execution(public * market.http.controller.LoginController.*(..))")
    public void isOnLoginPage() {}

    @Pointcut("within(market.service.*Service)")
    public void isServiceLayer() {
    }

    //    @Pointcut("this(org.springframework.stereotype.Repository)")
    @Pointcut("target(org.springframework.stereotype.Repository)")
    public void isRepositoryLayer() {
    }

    @Pointcut("isControllerLayer() && @annotation(org.springframework.web.bind.annotation.GetMapping)")
    public void hasGetMapping() {
    }

    @Pointcut("isControllerLayer() && args(org.springframework.ui.Model,..)")
    public void hasModelArg() {
    }
    


    @Pointcut("execution(public * market.service.*Service.findById(*))")
    public void anyServiceFindByIdMethod() {
    }


    @Pointcut("execution(public * market.http.controller.UserController.payOrder(..))")
    public void anyControllerPayOrderMethod() {}

    @Pointcut("execution(public * market.service.*Service.setNew*(..))")
    public void anyServiceSetParameterMethod() {
    }

    @Pointcut("execution(public * market.service.impl.OrderServiceImpl.payOrder(..))")
    public void anyServicePayOrder() {}


    @Pointcut("execution(public * market.http.*.*.*(..))")
    public void anyController() {}

    @Pointcut("execution(public * market.service.*.*(..))")
    public void anyService() {}

    @After("isOnLoginPage()")
    public void userInLoginPage() {
        log.info("User on login page");
    }

    @AfterThrowing(value = "anyController() " +
                           "&& target(service) ",
            throwing = "ex")
    public void addLoggingAfterThrowingController(Throwable ex, Object service) {
        log.info("AfterThrowing invoke controller method in class {}, with throwing {}", service, ex);
    }

    @AfterThrowing(value = "anyService() " +
                           "&& target(service) ",
            throwing = "ex")
    public void addLoggingAfterThrowingService(Throwable ex, Object service) {
        log.info("AfterThrowing invoke service method in class {}, with throwing {}", service, ex);
    }

    @Before("anyControllerPayOrderMethod()")
    public void addLoggingBeforeControllerPayOrder() {
        log.info("Before invoke pay order method in user controller");
    }



    @Before("anyServicePayOrder()")
    public void addLoggingBeforeServicePayOrder() {
        log.info("Before invoke pay order method in order service");
    }

    @After("anyServicePayOrder()")
    public void addLoggingAfterServicePayOrder() {
        log.info("After invoke pay order method in order service");
    }

    @After("anyControllerPayOrderMethod()")
    public void addLoggingAfterControllerPayOrder() {
        log.info("Before invoke pay order method in user controller");
    }



    @Before("anyServiceFindByIdMethod() " +
            "&& args(id) " +
            "&& target(service) " +
            "&& this(serviceProxy) " +
            "&& @within(transactional)")
    public void addLogging(JoinPoint joinPoint,
                           Object id,
                           Object service,
                           Object serviceProxy,
                           Transactional transactional) {
        log.info("Before invoke findById method in class {}, with id {}", service, id);
    }

    @AfterReturning(value = "anyServiceFindByIdMethod() " +
                            "&& target(service) ",
            returning = "result")
    public void addLoggingAfterReturning(Object result, Object service) {
        log.info("AfterReturning invoke findById method in class {}, with result {}", service, result);
    }

    @After("anyServiceSetParameterMethod() " +
           "&& target(service) ")
    public void addLoggingSetParam(Object service) {
        log.info("After method setNewParam in class {}", service);
    }



    @After("anyServiceFindByIdMethod() " +
           "&& target(service) ")
    public void addLoggingAfterThrowing(Object service) {
        log.info("After invoke findById method in class {}", service);
    }


}
