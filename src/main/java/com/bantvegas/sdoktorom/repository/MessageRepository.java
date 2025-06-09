package com.bantvegas.sdoktorom.repository;

import com.bantvegas.sdoktorom.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByDoctorId(Long doctorId);

    List<Message> findByPatientId(Long patientId);

}
