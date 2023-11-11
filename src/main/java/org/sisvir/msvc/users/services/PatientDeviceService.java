package org.sisvir.msvc.users.services;

import org.sisvir.msvc.users.models.entities.PatientDevice;

import java.util.Optional;

public interface PatientDeviceService {

    void deleteEntityPatientDeviceById(Long id);

    Optional<PatientDevice> findByDeviceId(Long deviceId);
}
