package com.epam.hospital.service.implementation;

import com.epam.hospital.dao.api.DiagnosisDao;
import com.epam.hospital.model.Diagnosis;
import com.epam.hospital.service.api.DiagnosisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DiagnosisServiceImpl implements DiagnosisService {

    @Autowired
    DiagnosisDao diagnosisDao;

    @Override
    @Transactional
    public Diagnosis getDiagnosisById(int id) {
        return diagnosisDao.getDiagnosisById(id);
    }

    @Override
    @Transactional
    public List<Diagnosis> getAllDiagnosis() {
        return diagnosisDao.getAllDiagnosis();
    }
}
