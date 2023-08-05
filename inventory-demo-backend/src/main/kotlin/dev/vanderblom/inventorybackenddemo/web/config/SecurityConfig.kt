package dev.vanderblom.inventorybackenddemo.web.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http
            .authorizeExchange {
                it
                    .pathMatchers(HttpMethod.PUT, "/api/**").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.POST, "/api/**").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")
                    .pathMatchers(HttpMethod.GET, "/api/**").authenticated()
                    .pathMatchers(HttpMethod.GET).permitAll()
            }
            .csrf { it.disable() }
            .httpBasic(withDefaults())
        return http.build()
    }

    @Bean
    fun userDetailsService(): MapReactiveUserDetailsService {
        val normalUser = User.withDefaultPasswordEncoder()
            .username("user")
            .password("user")
            .roles("USER")
            .build()
        val adminUser = User.withDefaultPasswordEncoder()
            .username("admin")
            .password("admin")
            .roles("ADMIN")
            .build()
        return MapReactiveUserDetailsService(normalUser, adminUser)
    }
}