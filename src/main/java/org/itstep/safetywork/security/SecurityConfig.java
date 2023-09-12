package org.itstep.safetywork.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        UserDetails admin = User.builder()
                .username("admin")
                .password("$2a$10$3uUBBq3ikiB5lXsNbrudeue.NwNkmFcd59t5qeDCuiFp70Af.8H9W")
                .roles("ADMIN", "REDACTOR")
                .build();
        JdbcUserDetailsManager detailsManager = new JdbcUserDetailsManager(dataSource);
        detailsManager.createUser(admin);
        return detailsManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/").permitAll()
                                .requestMatchers("/tools").permitAll()
                                .requestMatchers("/employee/showdetails/**").permitAll()
                                .requestMatchers("/employee/edit/**").hasAnyRole("ADMIN", "SAFETY")
                                .requestMatchers("/leaders").permitAll()
                                .requestMatchers("/workers").permitAll()
                                .requestMatchers("/addemployee").hasAnyRole("ADMIN", "SAFETY")
                                .requestMatchers("/machinery").permitAll()
                                .requestMatchers("/machinery/edit/**").permitAll()
                                .requestMatchers("/responsible").hasAnyRole("ADMIN", "TECHNICAL")
                                .requestMatchers("/addTypeOfMachinery").hasAnyRole("ADMIN", "TECHNICAL")
                                .requestMatchers("/equipment").permitAll()
                                .requestMatchers("/equipment/edit/**").permitAll()
                                .requestMatchers("/tools").permitAll()
                                .requestMatchers("/toolName").hasAnyRole("ADMIN", "FOREMAN")
                                .requestMatchers("/tools/edit/**").permitAll()
                                .requestMatchers("/manufacturer").hasAnyRole("ADMIN", "FOREMAN")
                                .requestMatchers("/work").permitAll()
                                .requestMatchers("/workpermit").hasAnyRole("ADMIN", "FOREMAN")
                                .requestMatchers("/work/completed").permitAll()
                                .requestMatchers("/work/edit/complited/**").hasAnyRole("ADMIN", "FOREMAN")
                                .requestMatchers("/work/edit/**").permitAll()
                                .requestMatchers("/highrisk").permitAll()
                                .requestMatchers("/instruction").permitAll()
                                .requestMatchers("/instruction/edit/**").permitAll()
                                .requestMatchers("/education").permitAll()
                                .requestMatchers("/addeducation/edit/**").hasAnyRole("ADMIN", "SAFETY")
                                .requestMatchers("/npaop").hasAnyRole("ADMIN", "SAFETY")
                                .requestMatchers("/violation").permitAll()
                                .requestMatchers("/work/addViolation").permitAll()
                                .requestMatchers("/medicine").permitAll()
                                .requestMatchers("/medicine/edit/**").permitAll()
                                .requestMatchers("/register").hasRole("ADMIN")
                                .requestMatchers("/department").hasRole("ADMIN")
                                .requestMatchers("/css/**").permitAll()
                                .requestMatchers("/img/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/books").permitAll()
                                .requestMatchers(HttpMethod.POST, "/books").hasRole("REDACTOR")
                                .requestMatchers("/books/delete/**").hasRole("ADMIN")
                                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .permitAll())
                .logout(logout ->
                        logout.logoutUrl("/logout")
                                .permitAll())
                .csrf(csrf -> csrf.ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
