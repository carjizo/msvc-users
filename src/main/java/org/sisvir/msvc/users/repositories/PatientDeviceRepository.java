package org.sisvir.msvc.users.repositories;

import org.sisvir.msvc.users.models.entities.PatientDevice;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientDeviceRepository extends CrudRepository<PatientDevice, Long> {

    @Modifying
    @Query(nativeQuery = true, value = "delete from msvc_users.patients_devices where patient_id=?1")
    void deleteEntityPatientDeviceById(Long id);

    Optional<PatientDevice> findByDeviceId(Long deviceId);
}
