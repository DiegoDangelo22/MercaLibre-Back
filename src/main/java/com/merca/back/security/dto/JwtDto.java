package com.merca.back.security.dto;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Getter
@Setter
public class JwtDto {
    private String token;
    private String bearer = "Bearer";
    private String nombreUsuario;
    private int UserId;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtDto(String token, String nombreUsuario, int UserId, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.nombreUsuario = nombreUsuario;
        this.UserId = UserId;
        this.authorities = authorities;
    }
}