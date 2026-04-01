package com.wanted.aop.section02.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    /* comment.
    *   Pointcut 표현식 : execution(접근제어자 반환타입 패키지.클래스.메서드(파라미터))
    *   - "execution(* com.wanted.aop.section02.MemberService.*(..))"
    *   - * : 모든 반환타입을 의미
    *   - 패키지 : com.wanted.aop.section02. / 클래스 : com.wanted.aop.section02
    *   - 클래스.*(..) : .* 은 클래스 내부 모든 메서드를 의미
    *   - (..) : 메서드의 매개변수가 0개 이상인 모든 경우를 의미
    *   - () : 파라미터가 없는 메소드 지칭
    *   - (String) : 매개변수가 String 1개인 메서드를 의미
    *   - (String, ..) : 첫 번째 String 이여야 하며, 나머지는 상관 없음.
    * */

    @Before("execution(* com.wanted.aop.section02.MemberService.*(..))")
    public void logBefore(JoinPoint joinPoint) { // ProceedingJoinPoint(@Around에서 함) 안 하고 JoinPoint.

        // JoinPoint 는 Advice 메서드가 적용되는 지점에 대한 정보를 제공하는 객체이다.
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();    // getArgs() 반환 타입이 Object[]임.

        // "1", "2".. 형식으로 끄내올 것
        StringBuilder argStr = new StringBuilder();
        for (Object arg : args) {
            argStr.append(arg).append(", ");
        }

        System.out.println("[Before 로그] " + methodName + " 메소드 실행 시작, 매개변수 : " + argStr);
    }

    /* comment.
    *   @After : 대상 메서드 실행이 완료된 후 실행되는 Advice
    *   자원 정리, 종료 로그 등에 유용하다.
    *   정상 종료든, 예외 발생이든 상관없이 항상 실행된다.
    * */
    @After("execution(* com.wanted.aop.section02.MemberService.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[After 로그] " + methodName + "메소드 실행 완료!");
    }

    /* comment.
    *   @AfterReturning
    *   대상 메서드가 정상적으로 실행을 완료하고, 값을 반환한 후 실행되는 Advice
    *   대상 메서드의 return 값을 확인하거나, 값을 변환할 때 사용될 수 있음.
    *   returning : 메서드의 반환값을 result 라는 이름의 매개변수로 받겠다는 의미
    */
    @AfterReturning(
            pointcut = "execution(* com.wanted.aop.section02.MemberService.*(..))",
            returning = "result"
    )
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[AfterReturn 로그] " + methodName + "메서드 정상 종료, 종료 시 반환값 : " + result);
    }

    /* comment.
    *   @AfterThrowing : 대상 메서드 실행 중 예외가 발생했을 때 동작하는 Advice
    *   사용 예시 : 예외 관련 로그,
    *   예외 복구 로직 등에 사용된다. -> 할 수 있긴 하지만, 더 좋은 예외처리로 하는 것이 좋다. (굳이 하지마)
    * */
    @AfterThrowing(
            pointcut = "execution(* com.wanted.aop.section02.MemberService.*(..))",
            throwing = "exception"
    )
    public void logAfterThrowing(JoinPoint joinPoint, Exception exception) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("[AfterThrowing 로그] " + methodName + " 메소드 실행 중 예외 발생 : " + exception.getMessage());
    }

}
