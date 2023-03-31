package com.merca.back.security.dto;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NuevoUsuario {
    @NotBlank
    @Size(min = 2, max = 50)
    private String nombreUsuario;
    @NotBlank
    @Size(min = 4, max = 40)
    private String password;
    private Set<String> roles = new HashSet<>();
}