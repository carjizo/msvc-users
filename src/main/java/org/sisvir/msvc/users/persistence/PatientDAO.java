package org.sisvir.msvc.users.persistence;


import org.sisvir.msvc.users.models.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientDAO {

    List<Patient> findAll();

    Optional<Patient> findById(Long id);

    void create(Patient patient);

    void deleteById(Long id);

    void deleteEntityPatientById(Long id);

    Optional<Patient> findByDni(String dni);

    void deletePatientDeviceById(Long id);
}
