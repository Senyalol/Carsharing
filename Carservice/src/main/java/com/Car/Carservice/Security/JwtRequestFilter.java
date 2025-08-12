package com.Car.Carservice.Security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final String secretKey = "c506558b364ad0dc9d45508b0e172ac5fa1d262e3d18363768c9222da1b925b42929864c6e663dec416db3c1e136f7c325f4adb51280e9ba6b71b0cd80104dd03153d43f86c47e4921d889755f7694158dc1c4825ff8324e35dbb1dccec7ad544ddf60057a1c92ae710d7277daab3a6adce875cdf0940710d105f0f7857dc78f50ff34783f780dd486b546dba9c77f4af25ea962d073167933e933028b97657953fe720ab501a649a2ebe6f3c2dc22f52ebfd1e3ae8d66c15394aabbd9a9fde77350b4ebce6c7c74d76a8aecc9aa0d32d2e1491a492b2e33d53a161677cc755556689bcaa5c43abd363eeebdaaba134d4f10b574c430e2533680549f746a5118";

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtRequestFilter(UserDetailsServiceImpl userDetailsServiceimpl) {
        this.userDetailsService = userDetailsServiceimpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String jwt = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
        }

        if(jwt != null) {

            try {
                Claims claims = Jwts.parser()
                        .setSigningKey(secretKey)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String username = claims.getSubject();

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            catch(Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
                return;
            }

        }

        filterChain.doFilter(request, response);

    }
}
