package egeumut.customerOrder.Core.exceptions.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import egeumut.customerOrder.Core.aspects.logging.logAspect;
import egeumut.customerOrder.Core.exceptions.problemDetails.AuthorizationProblemDetails;
import egeumut.customerOrder.Core.exceptions.problemDetails.BusinessProblemDetails;
import egeumut.customerOrder.Core.exceptions.problemDetails.InternalServerErrorProblemDetails;
import egeumut.customerOrder.Core.exceptions.problemDetails.ValidationProblemDetails;
import egeumut.customerOrder.Core.exceptions.types.AuthorizationException;
import egeumut.customerOrder.Core.exceptions.types.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private ObjectMapper mapper;
    Logger log = LoggerFactory.getLogger(logAspect.class);

    @ExceptionHandler({BusinessException.class})    //For Business Rules Violation
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public BusinessProblemDetails handleBusinessException(BusinessException businessException) throws JsonProcessingException {
        BusinessProblemDetails businessProblemDetails = new BusinessProblemDetails();
        businessProblemDetails.setDetail(businessException.getMessage());

        log.warn(mapper.writeValueAsString(businessProblemDetails));
        return businessProblemDetails;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})  //For validation exception
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ValidationProblemDetails handleValidationException(MethodArgumentNotValidException exception) throws JsonProcessingException {
        List<Map<String, String>> errorList =
                exception.getBindingResult().getFieldErrors().stream().map(
                        fieldError-> {
                            Map<String,String> validationError = new HashMap<>();
                            validationError.put(fieldError.getField(),fieldError.getDefaultMessage());
                            return validationError;
                        }).collect(Collectors.toList());
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setErrors(errorList);

        log.warn(mapper.writeValueAsString(validationProblemDetails));

        return validationProblemDetails;
    }


    //@ExceptionHandler({AuthorizationException.class})    //For Authorization
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationException.class)
    public AuthorizationProblemDetails handleAuthorizationException(AuthorizationException ex) throws JsonProcessingException {
        AuthorizationProblemDetails authorizationProblemDetails = new AuthorizationProblemDetails();
        authorizationProblemDetails.setDetail(ex.getMessage());

        log.warn(mapper.writeValueAsString(authorizationProblemDetails));
        return authorizationProblemDetails;
    }


    @ExceptionHandler({Exception.class})    //500 Exception
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public InternalServerErrorProblemDetails HandleException(Exception exception) throws JsonProcessingException {
        InternalServerErrorProblemDetails internalServerErrorProblemDetails = new InternalServerErrorProblemDetails();
        internalServerErrorProblemDetails.setDetail(exception.getMessage());

        log.warn(mapper.writeValueAsString(internalServerErrorProblemDetails));
        return internalServerErrorProblemDetails;
    }
}
