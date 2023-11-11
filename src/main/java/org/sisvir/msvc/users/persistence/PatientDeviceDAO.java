package org.sisvir.msvc.users.persistence;

import org.sisvir.msvc.users.models.entities.PatientDevice;
import org.sisvir.msvc.users.models.entities.User;

import java.util.Optional;

public interface PatientDeviceDAO {

    void deleteEntityPatientDeviceById(Long id);

    Optional<PatientDevice> findByDeviceId(Long deviceId);
}
