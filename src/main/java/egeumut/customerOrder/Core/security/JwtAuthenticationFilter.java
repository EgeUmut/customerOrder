package egeumut.customerOrder.Core.security;

import egeumut.customerOrder.Core.exceptions.types.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        //System.out.println("auth test1");
        final String jwt;
        final String userEmail;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){    //Checks User Token
            filterChain.doFilter(request,response);
            //System.out.println("auth test2");
            //throw new BusinessException("test");
            return;
        }
        //System.out.println("auth test3");
        //User Token is valid go on
        jwt = authHeader.substring(7);
        userEmail =  jwtService.extractUsername(jwt);  // extract the userEmail from JWT token
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);    //you can use your user class
            if (jwtService.isTokenValid(jwt,userDetails)){
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                // Yetkilendirme başarısız olduğunda istisna fırlat
                throw new BusinessException("Geçersiz JWT token");
            }
        } else {
            // Kullanıcı bulunamadığında veya kimlik doğrulama başarısız olduğunda istisna fırlat
            throw new BusinessException("Kullanıcı bulunamadı veya kimlik doğrulama başarısız");
        }
        filterChain.doFilter(request,response);
    }
}
