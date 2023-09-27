package org.sisvir.msvc.users.controllers.dto;

//import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sisvir.msvc.users.models.Device;
import org.sisvir.msvc.users.models.entities.PatientDevice;
import org.sisvir.msvc.users.models.entities.User;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {

    private Long id;

    @NotBlank
    private String dni;

    private String name;

    private String lastName;

    @NotBlank
    private String phone;

    @NotBlank
    private String email;

    private String address;

    private User user;

    private List<PatientDevice> patientDevices;

    private List<Device> devices;
}
