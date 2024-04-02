package egeumut.customerOrder.webApi.controllers;

import egeumut.customerOrder.Core.aspects.logging.Loggable;
import egeumut.customerOrder.business.abstracts.AuthService;
import egeumut.customerOrder.business.requests.auth.AuthenticationRequest;
import egeumut.customerOrder.business.requests.auth.RegisterRequest;
import egeumut.customerOrder.business.responses.auth.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    /**
     * Constructor for AuthController.
     * @param authService The AuthService instance.
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Registers a new user.
     * @param registerRequest The request body containing the details of the user to be registered.
     * @return The authentication response.
     */
    @Loggable
    @PostMapping("/register")
    @Operation(summary = "Registers a new user")
    public ResponseEntity<AuthenticationResponse> register(@Valid @RequestBody @Parameter(description = "The request body containing the details of the user to be registered")
                                                               RegisterRequest registerRequest){
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    /**
     * Authenticates a user.
     * @param authenticationRequest The request body containing the authentication details.
     * @return The authentication response.
     */
    @Loggable
    @PostMapping("/authenticate")
    @Operation(summary = "Authenticates a user")
    public ResponseEntity<AuthenticationResponse> authenticate(@Valid @RequestBody @Parameter(description = "The request body containing the authentication details")
                                                                   AuthenticationRequest authenticationRequest){
        return ResponseEntity.ok(authService.authenticate(authenticationRequest));
    }
}
