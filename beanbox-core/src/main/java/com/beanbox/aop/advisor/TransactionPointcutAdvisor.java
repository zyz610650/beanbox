package com.beanbox.aop.advisor;

import com.beanbox.aop.aspect.AspectJExpressionPointCut;
import com.beanbox.aop.aspect.Pointcut;
import com.beanbox.aop.aspect.TransactionPointCut;
import org.aopalliance.aop.Advice;

public class TransactionPointcutAdvisor extends AspectJExpressionPointcutAdvisor implements PointcutAdvisor,Advisor{

    /**
     * 切点 单例
     */
    private TransactionPointCut pointCut;

    /**
     * 具体拦截方法
     */
    private Advice advice;

    /**
     * 表达式
     */
    private String expression;



    @Override
    public Pointcut getPointcut () {
        if (null==pointCut)
        {
            pointCut=new TransactionPointCut();
        }
        return pointCut;
    }

    @Override
    public Advice getAdvice () {
        return advice;
    }

    public void setAdvice(org.aopalliance.aop.Advice advice){
        this.advice = advice;
    }
}
