package main.java.engine.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
 
@Aspect
public class StatsLogAspect {

    private static final String STATSLOG_ANNOTATED = "execution(@StatsLog  * *.*(..))";
    private StatsLogModel myModel;
    
    @Before(STATSLOG_ANNOTATED)
    public void logMethod(JoinPoint.StaticPart joinPointStaticPart) {
        if(myModel == null) {
            myModel = new StatsLogModel();
        }
        myModel.log(joinPointStaticPart.getSignature().getName());
        myModel.printStats();
    }
}