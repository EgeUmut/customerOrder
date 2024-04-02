package egeumut.customerOrder.business.concretes;

import egeumut.customerOrder.Core.security.JwtService;
import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.business.abstracts.AuthService;
import egeumut.customerOrder.business.requests.auth.AuthenticationRequest;
import egeumut.customerOrder.business.requests.auth.RegisterRequest;
import egeumut.customerOrder.business.responses.auth.AuthenticationResponse;
import egeumut.customerOrder.business.rules.UserBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.UserRepository;
import egeumut.customerOrder.entities.concretes.Role;
import egeumut.customerOrder.entities.concretes.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthManager implements AuthService {
    private final UserRepository userRepository;
    private final ModelMapperService modelMapperService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserBusinessRules userBusinessRules;

    public AuthManager(UserRepository userRepository, ModelMapperService modelMapperService,
                       PasswordEncoder passwordEncoder, JwtService jwtService,
                       AuthenticationManager authenticationManager, UserBusinessRules userBusinessRules) {
        this.userRepository = userRepository;
        this.modelMapperService = modelMapperService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userBusinessRules = userBusinessRules;
    }

    /**
     * Registers a new user with the provided registration details.
     *
     * @param registerRequest The registration request containing user details.
     * @return AuthenticationResponse containing JWT token upon successful registration.
     */
    @Override
    public AuthenticationResponse register(RegisterRequest registerRequest) {
        userBusinessRules.CheckIfEmailExist(registerRequest.getEmail());

        User user = modelMapperService.forRequest().map(registerRequest , User.class);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedDate(LocalDateTime.now());
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    /**
     * Authenticates a user based on the provided authentication details.
     *
     * @param authenticationRequest The authentication request containing user credentials.
     * @return AuthenticationResponse containing JWT token upon successful authentication.
     */
    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        userBusinessRules.CheckIfPasswordMatches(authenticationRequest.getEmail() , authenticationRequest.getPassword());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        ));
        var user = userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
