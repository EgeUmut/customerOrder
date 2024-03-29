package egeumut.customerOrder.webApi.controllers;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.business.abstracts.AuthService;
import egeumut.customerOrder.business.requests.auth.AuthenticationRequest;
import egeumut.customerOrder.business.requests.auth.RegisterRequest;
import egeumut.customerOrder.business.responses.auth.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private AuthService authService;

    @Loggable
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.register(request));
    }

    @Loggable
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
