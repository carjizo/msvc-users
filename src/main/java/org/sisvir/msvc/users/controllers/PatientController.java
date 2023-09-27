package org.sisvir.msvc.users.controllers;

import feign.FeignException;
//import jakarta.validation.Valid;
import org.sisvir.msvc.users.controllers.dto.PatientDTO;
import org.sisvir.msvc.users.models.Device;
import org.sisvir.msvc.users.models.entities.Patient;
import org.sisvir.msvc.users.services.PatientDeviceService;
import org.sisvir.msvc.users.services.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDeviceService patientDeviceService;

    @GetMapping("/getAll")
    public ResponseEntity<?> listar() {

        List<PatientDTO> patientDTOList = patientService.findAll()
                .stream()
                .map(patient -> PatientDTO.builder()
                        .id(patient.getId())
                        .dni(patient.getDni())
                        .name(patient.getName())
                        .lastName(patient.getLastName())
                        .phone(patient.getPhone())
                        .email(patient.getEmail())
                        .address(patient.getAddress())
                        .user(patient.getUser())
                        .patientDevices(patient.getPatientDevices())
                        .build())
                .toList();
        return ResponseEntity.ok(patientDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detailById(@PathVariable Long id) {

        Optional<Patient> patientOptional = patientService.findAllById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            PatientDTO patientDTO = PatientDTO.builder()
                    .id(patient.getId())
                    .dni(patient.getDni())
                    .name(patient.getName())
                    .lastName(patient.getLastName())
                    .phone(patient.getPhone())
                    .email(patient.getEmail())
                    .address(patient.getAddress())
                    .user(patient.getUser())
                    .patientDevices(patient.getPatientDevices())
                    .devices(patient.getDevices())
                    .build();
            return ResponseEntity.ok(patientDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> crear(@Valid @RequestBody PatientDTO patientDTO, BindingResult result) throws URISyntaxException {

        if (!patientDTO.getDni().isEmpty() && patientService.findByDni(patientDTO.getDni()).isPresent()) {
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje", "Ya existe un usuario con ese dni!"));
        }

        if (result.hasErrors()) {
            return validate(result);
        }

        patientService.create(Patient.builder()
                        .dni(patientDTO.getDni())
                        .name(patientDTO.getName())
                        .lastName(patientDTO.getLastName())
                        .phone(patientDTO.getPhone())
                        .email(patientDTO.getEmail())
                        .address(patientDTO.getAddress())
                        .user(patientDTO.getUser())
                .build());
        return ResponseEntity.created(new URI("/patients/create")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateById(@Valid @RequestBody PatientDTO patientDTO, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validate(result);
        }

        Optional<Patient> patientOptional = patientService.findById(id);
        if (patientOptional.isPresent()) {
            Patient patient = patientOptional.get();
            if (!patientDTO.getDni().isEmpty() &&
                    !patientDTO.getDni().equalsIgnoreCase(patient.getDni()) &&
                    patientService.findByDni(patientDTO.getDni()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje", "Ya existe un usuario con ese dni!"));
            }
            patient.setDni(patientDTO.getDni());
            patient.setName(patientDTO.getName());
            patient.setLastName(patientDTO.getLastName());
            patient.setPhone(patientDTO.getPhone());
            patient.setEmail(patientDTO.getEmail());
            patient.setAddress(patientDTO.getAddress());
            patient.setUser(patientDTO.getUser());
            patientService.create(patient);
            return ResponseEntity.ok("Registro Actualizado");
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        Optional<Patient> patientOptional = patientService.findById(id);
        if (patientOptional.isPresent()) {
            patientDeviceService.deleteEntityPatientDeviceById(id);
            patientService.deleteEntityPatientById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/assign-device/{patientId}")
    public ResponseEntity<?> assignDevice(@Valid @RequestBody Device device, BindingResult result, @PathVariable Long patientId) {

        if (result.hasErrors()) {
            return validate(result);
        }

        Optional<Device> deviceOptional;
        try {
            deviceOptional = patientService.assignDevice(device, patientId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje",
                            "No existe mensaje por id o error en la comunicación: " + e.getMessage()));
        }
        if (deviceOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(deviceOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/unassign-device/{patientId}")
    public ResponseEntity<?> unassignDevice(@RequestBody Device device, @PathVariable Long patientId) {

        Optional<Device> deviceOptional;
        try {
            deviceOptional = patientService.unassignDevice(device, patientId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje",
                            "No existe mensaje por id o error en la comunicación: " + e.getMessage()));
        }
        if (deviceOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(deviceOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-device/{id}")
    public ResponseEntity<?> deletePatientDeviceById(@PathVariable Long id) {
        patientService.deletePatientDeviceById(id);
        return ResponseEntity.noContent().build();
    }


    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
