package org.sisvir.msvc.users.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sisvir.msvc.users.models.Device;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 8)
    @NotBlank
    private String dni;

    @Size(max = 25)
    private String name;

    @Size(max = 25)
    @Column(name = "last_name")
    private String lastName;

    @Column(unique = true)
    @Size(max = 15)
    @NotBlank
    private String phone;

    @Column(unique = true)
    @Size(max = 25)
    @Email
    @NotBlank
    private String email;

    @Size(max = 25)
    private String address;

    @Column(name = "created_at", updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "patient_id")
    private List<PatientDevice> patientDevices;

    @Transient
    private List<Device> devices;


    public void assignPatientDevice(PatientDevice patientDevice) {
        patientDevices.add(patientDevice);
    }

    public void unassignPatientDevice(PatientDevice patientDevice) {
        patientDevices.remove(patientDevice);
    }
    @PrePersist
    protected void onCreate() {
        ZoneId zoneId = ZoneId.of("America/Lima"); // Zona horaria de Perú
        createdAt = OffsetDateTime.now(zoneId);
        updatedAt = OffsetDateTime.now(zoneId);
    }

    @PreUpdate
    protected void onUpdate() {
        ZoneId zoneId = ZoneId.of("America/Lima"); // Zona horaria de Perú
        updatedAt = OffsetDateTime.now(zoneId);
    }
}
