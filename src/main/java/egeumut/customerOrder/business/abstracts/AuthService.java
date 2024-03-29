package egeumut.customerOrder.business.abstracts;

import egeumut.customerOrder.business.requests.auth.AuthenticationRequest;
import egeumut.customerOrder.business.requests.auth.RegisterRequest;
import egeumut.customerOrder.business.responses.auth.AuthenticationResponse;

public interface AuthService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);
}
