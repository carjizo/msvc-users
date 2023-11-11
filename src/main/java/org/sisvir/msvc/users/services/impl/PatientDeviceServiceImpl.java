package org.sisvir.msvc.users.services.impl;

import org.sisvir.msvc.users.models.entities.PatientDevice;
import org.sisvir.msvc.users.persistence.PatientDeviceDAO;
import org.sisvir.msvc.users.services.PatientDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientDeviceServiceImpl implements PatientDeviceService {

    @Autowired
    private PatientDeviceDAO patientDeviceDAO;

    @Override
    public void deleteEntityPatientDeviceById(Long id) {
        patientDeviceDAO.deleteEntityPatientDeviceById(id);
    }

    @Override
    public Optional<PatientDevice> findByDeviceId(Long deviceId) {
        return patientDeviceDAO.findByDeviceId(deviceId);
    }
}
