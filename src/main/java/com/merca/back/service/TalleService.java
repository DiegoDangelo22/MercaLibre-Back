package com.merca.back.service;

import com.merca.back.model.Ropa;
import com.merca.back.model.Talle;
import com.merca.back.repository.TalleRepository;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class TalleService {
    @Autowired
    TalleRepository talleRepository;
    
    public List<Talle> list() {
        return talleRepository.findAll();
    }
    
    public void save(Talle talle) {
        talleRepository.save(talle);
    }
    
    public void delete(int id) {
        talleRepository.deleteById(id);
    }
}