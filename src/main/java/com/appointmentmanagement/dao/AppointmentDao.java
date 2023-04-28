package com.appointmentmanagement.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.appointmentmanagement.entity.Appointment;

@Repository
public interface AppointmentDao extends JpaRepository<Appointment, Integer>  {
	
	List<Appointment> findByPatientId(int patientId);
	List<Appointment> findByDoctorId(int doctorId);
	

}
