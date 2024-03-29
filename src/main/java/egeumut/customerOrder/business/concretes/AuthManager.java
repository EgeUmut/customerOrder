package egeumut.customerOrder.business.concretes;

import egeumut.customerOrder.Core.config.JwtService;
import egeumut.customerOrder.Core.utilities.mappers.ModelMapperService;
import egeumut.customerOrder.business.abstracts.AuthService;
import egeumut.customerOrder.business.requests.auth.AuthenticationRequest;
import egeumut.customerOrder.business.requests.auth.RegisterRequest;
import egeumut.customerOrder.business.responses.auth.AuthenticationResponse;
import egeumut.customerOrder.business.rules.UserBusinessRules;
import egeumut.customerOrder.dataAccess.abstracts.UserRepository;
import egeumut.customerOrder.entities.concretes.Role;
import egeumut.customerOrder.entities.concretes.User;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
public class AuthManager implements AuthService {
    private UserRepository userRepository;
    private ModelMapperService modelMapperService;
    private PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private UserBusinessRules userBusinessRules;

    @Override
    public AuthenticationResponse register(RegisterRequest request) {
        userBusinessRules.CheckIfEmailExist(request.getEmail());

        User user = modelMapperService.forRequest().map(request , User.class);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        user.setCreatedDate(LocalDateTime.now());
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        userBusinessRules.CheckIfPasswordMatches(request.getEmail() , request.getPassword());

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
