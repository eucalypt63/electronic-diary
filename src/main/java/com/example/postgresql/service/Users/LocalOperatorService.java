package com.example.postgresql.service.Users;

import com.example.postgresql.model.Users.Administrations.Administrations;
import com.example.postgresql.model.Users.Administrations.AdministrationsTypes;
import com.example.postgresql.model.Users.LocalOperator;
import com.example.postgresql.repository.Users.Administrations.AdministrationsRepository;
import com.example.postgresql.repository.Users.Administrations.AdministrationsTypesRepository;
import com.example.postgresql.repository.Users.LocalOperatorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalOperatorService {

    @Autowired
    private LocalOperatorRepository localOperatorRepository;

    public LocalOperator findLocalOperatorByUserId(Long id) {
        return localOperatorRepository.findLocalOperatorByUserId(id);
    }

    public LocalOperator findLocalOperatorById(Long id) {
        return localOperatorRepository.findById(id).orElse(null);
    }

    public void saveLocalOperator(LocalOperator localOperator) {
        localOperatorRepository.save(localOperator);
    }

    public void deleteLocalOperatorById(Long id) {
        localOperatorRepository.deleteById(id);
    }

}
