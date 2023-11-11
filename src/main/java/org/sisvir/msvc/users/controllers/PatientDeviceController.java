package org.sisvir.msvc.users.controllers;

import org.sisvir.msvc.users.controllers.dto.PatientDeviceDTO;
import org.sisvir.msvc.users.models.entities.PatientDevice;
import org.sisvir.msvc.users.services.PatientDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/patients-device")
public class PatientDeviceController {

    @Autowired
    private PatientDeviceService service;

    @GetMapping("/{id}")
    public ResponseEntity<?> detailByDeviceId(@PathVariable Long id) {

        Optional<PatientDevice> optional = service.findByDeviceId(id);
        if (optional.isPresent()) {
            PatientDevice patientDevice = optional.get();
            PatientDeviceDTO dto = PatientDeviceDTO.builder()
                    .id(patientDevice.getId())
                    .deviceId(patientDevice.getDeviceId())
                    .patientId(patientDevice.getPatientId())
                    .build();
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }
}
