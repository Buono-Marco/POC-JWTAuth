package com.example.pocjwtauth.security;

import com.example.pocjwtauth.dto.UserDTO;
import com.example.pocjwtauth.mapper.UserMapper;
import com.example.pocjwtauth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.example.pocjwtauth.util.ConstantUtils.AUTHORITY_PREFIX;
import static com.example.pocjwtauth.util.ConstantUtils.DEFAULT_ROLE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        jwt = authHeader.substring(7);
        userEmail = jwtService.extractUsername(jwt);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDTO authenticatedUser = userRepository.findByEmail(userEmail)
                    .map(UserMapper::mapToDTO)
                    .orElse(buildBasicUser(userEmail));
            Set<GrantedAuthority> grantedAuthorities = Set.of(
                    new SimpleGrantedAuthority(AUTHORITY_PREFIX + authenticatedUser.getRoleName()));
            if (jwtService.isTokenValid(jwt, authenticatedUser)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticatedUser, null, grantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);

    }

    private UserDTO buildBasicUser(String email) {
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail(email);
        userDTO.setRoleName(DEFAULT_ROLE);
        return userDTO;
    }
}
