package org.sisvir.msvc.users.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientDeviceDTO {

    private Long id;

    private Long deviceId;

    private Long patientId;
}
