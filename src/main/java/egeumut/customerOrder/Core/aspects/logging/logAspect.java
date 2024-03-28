package egeumut.customerOrder.Core.aspects.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Aspect
@Component
public class logAspect {

    @Autowired
    private ObjectMapper mapper;
    Logger log = LoggerFactory.getLogger(logAspect.class);

    @Pointcut("within(@org.springframework.stereotype.Repository *)"
            + " || within(@org.springframework.stereotype.Service *)"
            + " || within(@org.springframework.web.bind.annotation.RestController *)")
    public void myPointCut(){
    }

    @Around("@annotation(Loggable)")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable {
        //ObjectMapper mapper = new ObjectMapper();
        String methodName = pjp.getSignature().getName();   //get method name
        String className = pjp.getTarget().getClass().toString();   //get class name
        Object[] array = pjp.getArgs(); //get inputs
        //onBefore
        Map<String, Object> logInfo = new LinkedHashMap<>();
        logInfo.put("message", "method invoked");
        logInfo.put("className", className);
        logInfo.put("methodName", methodName);
        logInfo.put("arguments", array);

        log.info(mapper.writeValueAsString(logInfo));
        //onAfter with response
        Object object = pjp.proceed();
        Map<String, Object> logInfoAfter = new LinkedHashMap<>();
        logInfoAfter.put("className", className);
        logInfoAfter.put("methodName", methodName);
        logInfoAfter.put("response", object);

        log.info(mapper.writeValueAsString(logInfoAfter));

//        log.info(className+" : "+ methodName + "()"+" Responses: " +
//                mapper.writeValueAsString(object));

        return object;
    }
}
