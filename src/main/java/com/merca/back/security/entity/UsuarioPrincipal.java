package com.merca.back.security.entity;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
public class UsuarioPrincipal implements UserDetails {
    @Autowired
    Usuario usuario;
    private String nombreUsuario;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UsuarioPrincipal(String nombreUsuario, String password, Collection<? extends GrantedAuthority> authorities) {
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.authorities = authorities;
    }
    
    public static UsuarioPrincipal build(Usuario usuario) {
        List<GrantedAuthority> authorities = usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name())).collect(Collectors.toList());
        UsuarioPrincipal userPrincipal = new UsuarioPrincipal(usuario.getNombreUsuario(), usuario.getPassword(), authorities);
        userPrincipal.usuario = usuario;
        return userPrincipal;
    }
    
    public int getId() {
        return this.usuario.getId();
    }
    
    @Override
    public String getUsername() {
        return nombreUsuario;
    }
    
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
}