package egeumut.customerOrder.Core.exceptions.handlers;

import egeumut.customerOrder.Core.exceptions.problemDetails.BusinessProblemDetails;
import egeumut.customerOrder.Core.exceptions.problemDetails.ValidationProblemDetails;
import egeumut.customerOrder.Core.exceptions.types.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BusinessException.class})    //For Business Rules Violation
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public BusinessProblemDetails handleBusinessException(BusinessException businessException){
        BusinessProblemDetails businessProblemDetails = new BusinessProblemDetails();
        businessProblemDetails.setDetail(businessException.getMessage());
        return businessProblemDetails;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})  //For validation exception
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ValidationProblemDetails handleValidationException(MethodArgumentNotValidException exception){
        List<Map<String, String>> errorList =
                exception.getBindingResult().getFieldErrors().stream().map(
                        fieldError-> {
                            Map<String,String> validationError = new HashMap<>();
                            validationError.put(fieldError.getField(),fieldError.getDefaultMessage());
                            return validationError;
                        }).collect(Collectors.toList());
        ValidationProblemDetails validationProblemDetails = new ValidationProblemDetails();
        validationProblemDetails.setErrors(errorList);
        return validationProblemDetails;
    }
}
