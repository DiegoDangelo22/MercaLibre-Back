package com.merca.back.security.service;

import com.merca.back.security.entity.Rol;
import com.merca.back.security.enums.RolNombre;
import com.merca.back.security.repository.RolRepository;
import javax.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RolService {
    @Autowired
    RolRepository rolRepository;
    
    public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
        return rolRepository.findByRolNombre(rolNombre);
    }
    
    public void save(Rol rol) {
        rolRepository.save(rol);
    }
}