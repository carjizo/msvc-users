package org.sisvir.msvc.users.controllers.dto;

//import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sisvir.msvc.users.models.entities.Patient;

import javax.validation.constraints.NotBlank;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

    private Patient patient;
}
