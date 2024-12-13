package com.root14.gateway.route;


import com.root14.gateway.service.UserService;
import com.root14.gateway.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements GlobalFilter {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    @Autowired
    public JwtFilter(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    /**
     * todo
     * it's a security issue, consider encrypted headers if  microservices is not in local network or same not in same computer.
     *
     * @param exchange
     * @param chain
     * @return added auth user as header
     */

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (token != null && token.startsWith("Bearer ")) {

            String jwt = token.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            String authenticatedUserId = userService.findByUsername(username).getId();

            exchange = exchange.mutate().request(exchange.getRequest().mutate().header("authenticated-user-id", authenticatedUserId).build()).build();
        }

        return chain.filter(exchange);
    }
}
