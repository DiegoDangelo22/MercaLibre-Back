package com.merca.back.security.service;

import com.merca.back.security.entity.Rol;
import com.merca.back.security.enums.RolNombre;
import com.merca.back.security.repository.RolRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RolService {
    @Autowired
    RolRepository rolRepo;
    
    @PostConstruct
    public void init() {
        if(rolRepo.count() == 0) {
          rolRepo.save(new Rol(RolNombre.ROLE_ADMIN));
          rolRepo.save(new Rol(RolNombre.ROLE_USER));
        }
    }
    
    public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
        return rolRepo.findByRolNombre(rolNombre);
    }
    
    public void save(Rol rol) {
        rolRepo.save(rol);
    }
}