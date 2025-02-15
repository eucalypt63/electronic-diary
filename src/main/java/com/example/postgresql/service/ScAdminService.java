package com.example.postgresql.service;

import com.example.postgresql.model.ScAdmin;
import com.example.postgresql.repository.ScAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScAdminService {

    @Autowired
    private ScAdminRepository scAdminRepository;

    /*
    public ScAdmin createScAdmin(String login, String password, String role) {
        ScAdmin scAdmin = new ScAdmin(login, password, role);
        return scAdminRepository.save(scAdmin);
    }
     */

    public List<ScAdmin> getAllScAdmins() {
        return scAdminRepository.findAll();
    }
}
