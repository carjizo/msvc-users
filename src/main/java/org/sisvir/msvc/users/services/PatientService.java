package org.sisvir.msvc.users.services;

import org.sisvir.msvc.users.models.Device;
import org.sisvir.msvc.users.models.entities.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    List<Patient> findAll();

    Optional<Patient> findById(Long id);

    void create(Patient patient);

    void deleteById(Long id);

    void deleteEntityPatientById(Long id);

    Optional<Patient> findByDni(String dni);

    Optional<Patient> findAllById(Long id);

    void deletePatientDeviceById(Long id);

    Optional<Device> assignDevice(Device device, Long patientId);

    Optional<Device> unassignDevice(Device device, Long patientId);
}
