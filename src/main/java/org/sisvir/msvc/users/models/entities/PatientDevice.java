package org.sisvir.msvc.users.models.entities;

//import jakarta.persistence.*;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients_devices")
public class PatientDevice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "device_id", unique = true)
    private Long deviceId;

    @Column(name = "patient_id", unique = true)
    private Long patientId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PatientDevice)) {
            return false;
        }
        PatientDevice o = (PatientDevice) obj;
        return this.deviceId != null && this.deviceId.equals(o.deviceId);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//
//        PatientDevice that = (PatientDevice) o;
//
//        if (!Objects.equals(id, that.id)) return false;
//        return Objects.equals(deviceId, that.deviceId);
//    }
}
