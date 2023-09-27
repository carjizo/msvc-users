package org.sisvir.msvc.users.persistence.impl;

import org.sisvir.msvc.users.models.entities.Patient;
import org.sisvir.msvc.users.persistence.PatientDAO;
import org.sisvir.msvc.users.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class PatientDAOImpl implements PatientDAO {

    @Autowired
    private PatientRepository patientRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Patient> findAll() {
        return (List<Patient>) patientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findById(Long id) {
        return patientRepository.findById(id);
    }

    @Override
    @Transactional
    public void create(Patient patient) {
        patientRepository.save(patient);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        patientRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteEntityPatientById(Long id) {
        patientRepository.deleteEntityPatientById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Patient> findByDni(String dni) {
        return patientRepository.findByDni(dni);
    }

    @Override
    @Transactional
    public void deletePatientDeviceById(Long id) {
        patientRepository.deletePatientDeviceById(id);
    }
}
