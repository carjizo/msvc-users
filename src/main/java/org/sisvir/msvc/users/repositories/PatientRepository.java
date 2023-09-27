package org.sisvir.msvc.users.repositories;

import org.sisvir.msvc.users.models.entities.Patient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

    Optional<Patient> findByDni(String dni);

    @Modifying
    @Query(nativeQuery = true, value = "delete from msvc_users.patients where id=?1")
    void deleteEntityPatientById(Long id);

    @Modifying
    @Query("delete from PatientDevice pd where pd.deviceId=?1")
    void deletePatientDeviceById(Long id);
}
