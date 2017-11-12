package com.epam.service.impl;

import com.epam.hospital.dao.impl.PatientDaoImpl;
import com.epam.hospital.model.Patient;
import com.epam.hospital.service.api.PatientService;
import com.epam.hospital.service.implementation.PatientServiceImpl;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static junit.framework.Assert.assertEquals;

public class PatientServiceTest {

    @Test
    public void getPatientById() throws IOException {
        PatientService patientService = new PatientServiceImpl();
        ((PatientServiceImpl) patientService).setPatientDao(new PatientDaoImpl());
        Patient patientById = patientService.getPatientById(1);
        // 1	Richard	Barthelmess	1964-0	38-12
        assertEquals(Optional.ofNullable(patientById.getId()), Optional.ofNullable(1));
        assertEquals(java.util.Optional.ofNullable(patientById.getName()), Optional.ofNullable("Richard"));
    }

    @Test
    public void getNextPartOfPatients() throws IOException {
        int step = 2;
        PatientService patientService = new PatientServiceImpl();
        List<Patient> list = new ArrayList<>();
        list.add(new Patient());
        list.add(new Patient());
        list.add(new Patient());
        list.add(new Patient());
        list.add(new Patient());
        ((PatientServiceImpl) patientService).setAllPatients(list);
        ((PatientServiceImpl) patientService).setStep(step);
        int all = list.size();
        while (all >= step) {
            List<Patient> PartOfPatients = patientService.getNextPartOfPatients();
            assertEquals(PartOfPatients.size(), step);
            all -= step;
        }
        List<Patient> PartOfPatients = patientService.getNextPartOfPatients();
        assertEquals(PartOfPatients.size(), all);
    }

    @Test
    public void getPreviousPartOfPatients() throws IOException {
        int step = 2;
        PatientService patientService = new PatientServiceImpl();
        List<Patient> list = new ArrayList<>();
        list.add(new Patient());
        list.add(new Patient());
        list.add(new Patient());
        list.add(new Patient());
        list.add(new Patient());
        ((PatientServiceImpl) patientService).setAllPatients(list);
        ((PatientServiceImpl) patientService).setStep(step);
        ((PatientServiceImpl) patientService).setCurrentPos(list.size());
        int all = list.size();
        while (all >= step) {
            List<Patient> PartOfPatients = patientService.getPreviousPartOfPatients();
            assertEquals(PartOfPatients.size(), step);
            all -= step;
        }
        List<Patient> PartOfPatients = patientService.getPreviousPartOfPatients();
        assertEquals(PartOfPatients.size(), all);
    }
}
