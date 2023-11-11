package org.sisvir.msvc.users.persistence.impl;

import org.sisvir.msvc.users.models.entities.PatientDevice;
import org.sisvir.msvc.users.persistence.PatientDeviceDAO;
import org.sisvir.msvc.users.repositories.PatientDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class PatientDeviceDAOImpl implements PatientDeviceDAO {

    @Autowired
    private PatientDeviceRepository patientDeviceRepository;

    @Override
    @Transactional
    public void deleteEntityPatientDeviceById(Long id) {
        patientDeviceRepository.deleteEntityPatientDeviceById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PatientDevice> findByDeviceId(Long deviceId) {
        return patientDeviceRepository.findByDeviceId(deviceId);
    }
}
