package com.merca.back.service;

import com.merca.back.model.RopaColor;
import com.merca.back.repository.RopaColorRepository;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class RopaColorService {
    @Autowired
    RopaColorRepository ropaColorRepository;
    public void save(RopaColor ropaColor) {
        ropaColorRepository.save(ropaColor);
    }
}