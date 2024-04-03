package egeumut.customerOrder.Core.exceptions.problemDetails;

import org.springframework.http.HttpStatus;

public class InternalServerErrorProblemDetails extends ProblemDetails{
    public InternalServerErrorProblemDetails(){
        setTitle("Unexpected Error");
        setType("http://egeumut.com/exceptions/UnexpectedError");
        setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }
}
