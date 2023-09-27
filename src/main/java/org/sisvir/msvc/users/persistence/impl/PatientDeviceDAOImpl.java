package org.sisvir.msvc.users.persistence.impl;

import org.sisvir.msvc.users.persistence.PatientDeviceDAO;
import org.sisvir.msvc.users.repositories.PatientDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PatientDeviceDAOImpl implements PatientDeviceDAO {

    @Autowired
    private PatientDeviceRepository patientDeviceRepository;

    @Override
    @Transactional
    public void deleteEntityPatientDeviceById(Long id) {
        patientDeviceRepository.deleteEntityPatientDeviceById(id);
    }
}
