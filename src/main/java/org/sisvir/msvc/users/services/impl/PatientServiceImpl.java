package org.sisvir.msvc.users.services.impl;


import org.sisvir.msvc.users.clients.DeviceClientRest;
import org.sisvir.msvc.users.models.Device;
import org.sisvir.msvc.users.models.entities.Patient;
import org.sisvir.msvc.users.models.entities.PatientDevice;
import org.sisvir.msvc.users.persistence.PatientDAO;
import org.sisvir.msvc.users.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.cloud.openfeign.FeignClientFactory;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientDAO patientDAO;

    @Autowired
    private DeviceClientRest clientRest;

    @Override
    public List<Patient> findAll() {
        return patientDAO.findAll();
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientDAO.findById(id);
    }

    @Override
    public void create(Patient patient) {
        patientDAO.create(patient);
    }

    @Override
    public void deleteById(Long id) {
        patientDAO.deleteById(id);
    }

    @Override
    public void deleteEntityPatientById(Long id) {
        patientDAO.deleteEntityPatientById(id);
    }

    @Override
    public Optional<Patient> findByDni(String dni) {
        return patientDAO.findByDni(dni);
    }

    @Override
    public Optional<Patient> findAllById(Long id) {

        Optional<Patient> patientOptional = patientDAO.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            if (!patient.getPatientDevices().isEmpty()) {
                List<Long> ids = patient.getPatientDevices().stream().map(PatientDevice::getDeviceId)
                        .toList();

                List<Device> devices = clientRest.findAllById(ids);
                patient.setDevices(devices);
            }
            return Optional.of(patient);
        }
        return Optional.empty();
    }

    @Override
    public void deletePatientDeviceById(Long id) {
        patientDAO.deletePatientDeviceById(id);
    }

    @Override
//    @Transactional
    public Optional<Device> assignDevice(Device device, Long patientId) {

        Optional<Patient> patientOptional = patientDAO.findById(patientId);
        if (patientOptional.isPresent()) {
            Device deviceMsvc = clientRest.detailById(device.getId());

            Patient patient = patientOptional.get();
            PatientDevice patientDevice = new PatientDevice();
            patientDevice.setDeviceId(deviceMsvc.getId());

            patient.assignPatientDevice(patientDevice);
            patientDAO.create(patient);
            return Optional.of(deviceMsvc);
        }
        return Optional.empty();
    }


    @Override
//    @Transactional
    public Optional<Device> unassignDevice(Device device, Long patientId) {
        Optional<Patient> patientOptional = patientDAO.findById(patientId);
        if (patientOptional.isPresent()) {
            Device deviceMsvc = clientRest.detailById(device.getId());

            Patient patient = patientOptional.get();
            PatientDevice patientDevice = new PatientDevice();
            patientDevice.setDeviceId(deviceMsvc.getId());

            patient.unassignPatientDevice(patientDevice);
            patientDAO.create(patient);
            return Optional.of(deviceMsvc);
        }
        return Optional.empty();
    }
}
