package egeumut.customerOrder.Core.exceptions.problemDetails;

import org.springframework.http.HttpStatus;

public class AuthorizationProblemDetails extends ProblemDetails{
    public AuthorizationProblemDetails(){
        setTitle("Unauthorized Action");
        setType("http://egeumut.com/exceptions/authorization");
        setStatus(HttpStatus.FORBIDDEN.toString());
    }
}
