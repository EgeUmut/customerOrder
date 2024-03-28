package egeumut.customerOrder.Core.exceptions.problemDetails;

import org.springframework.http.HttpStatus;

public class BusinessProblemDetails extends ProblemDetails{
    public BusinessProblemDetails(){
        setTitle("Business Rule Violation");
        setType("http://egeumut.com/exceptions/business");
        setStatus(HttpStatus.BAD_REQUEST.toString());
    }
}
