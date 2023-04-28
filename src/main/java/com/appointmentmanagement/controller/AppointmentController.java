package com.appointmentmanagement.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.appointmentmanagement.dto.AppointmentResponseDto;
import com.appointmentmanagement.dto.CommanApiResponse;
import com.appointmentmanagement.dto.UpdateAppointmentRequest;
import com.appointmentmanagement.entity.Appointment;
import com.appointmentmanagement.entity.User;
import com.appointmentmanagement.exception.AppointmentNotFoundException;
import com.appointmentmanagement.service.AppointmentService;
import com.appointmentmanagement.service.UserService;
import com.appointmentmanagement.utility.Constants.AppointmentStatus;
import com.appointmentmanagement.utility.Constants.BloodGroup;
import com.appointmentmanagement.utility.Constants.DoctorSpecialist;
import com.appointmentmanagement.utility.Constants.ResponseCode;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/appointment/")
@CrossOrigin(origins = "http://localhost:3000")
public class AppointmentController {

	Logger LOG = LoggerFactory.getLogger(AppointmentController.class);

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private UserService userService;

	@PostMapping("patient/add")
	@ApiOperation(value = "Api to add patient appointment")
	public ResponseEntity<?> addAppointment(@RequestBody Appointment appointment) {
		LOG.info("Recieved request to add patient appointment");

		CommanApiResponse response = new CommanApiResponse();

		if (appointment == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to add patient appointment");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		if (appointment.getPatientId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to add patient appointment");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		appointment.setDate(LocalDate.now().toString());
		appointment.setStatus(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());

		Appointment addedAppointment = appointmentService.addAppointment(appointment);

		if (addedAppointment != null) {
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage("Appointment Added");
			return new ResponseEntity(response, HttpStatus.OK);
		}

		else {
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage("Failed to add Appointment");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("all")
	public ResponseEntity<?> getAllAppointments() {
		LOG.info("recieved request for getting ALL Appointments !!!");

		List<Appointment> appointments = this.appointmentService.getAllAppointment();

		List<AppointmentResponseDto> response = new ArrayList();

		for (Appointment appointment : appointments) {

			AppointmentResponseDto a = new AppointmentResponseDto();

			User patient = this.userService.getUserById(appointment.getPatientId());

			a.setPatientContact(patient.getContact());
			a.setPatientId(patient.getId());
			a.setPatientName(patient.getFirstName() + " " + patient.getLastName());

			if (appointment.getDoctorId() != 0) {
				User doctor = this.userService.getUserById(appointment.getDoctorId());
				a.setDoctorContact(doctor.getContact());
				a.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
				a.setDoctorId(doctor.getId());
				a.setPrescription(appointment.getPrescription());

				if (appointment.getStatus().equals(AppointmentStatus.TREATMENT_DONE.value())) {
					a.setPrice(String.valueOf(appointment.getPrice()));
				}

				else {
					a.setPrice(AppointmentStatus.TREATMENT_PENDING.value());
				}
			}

			else {
				a.setDoctorContact(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
				a.setDoctorName(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
				a.setDoctorId(0);
				a.setPrice(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
				a.setPrescription(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
			}

			a.setStatus(appointment.getStatus());
			a.setProblem(appointment.getProblem());
			a.setDate(appointment.getDate());
			a.setAppointmentDate(appointment.getAppointmentDate());
			a.setId(appointment.getId());

			response.add(a);
		}

		LOG.info("response sent!!!");
		return ResponseEntity.ok(response);
	}

	@GetMapping("id")
	public ResponseEntity<?> getAllAppointments(@RequestParam("appointmentId") int appointmentId) {
		LOG.info("recieved request for getting  Appointment by id !!!");

		Appointment appointment = this.appointmentService.getAppointmentById(appointmentId);

		if (appointment == null) {
			throw new AppointmentNotFoundException();
		}

		AppointmentResponseDto a = new AppointmentResponseDto();

		User patient = this.userService.getUserById(appointment.getPatientId());

		a.setPatientContact(patient.getContact());
		a.setPatientId(patient.getId());
		a.setPatientName(patient.getFirstName() + " " + patient.getLastName());

		if (appointment.getDoctorId() != 0) {
			User doctor = this.userService.getUserById(appointment.getDoctorId());
			a.setDoctorContact(doctor.getContact());
			a.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
			a.setDoctorId(doctor.getId());
			a.setPrescription(appointment.getPrescription());

			if (appointment.getStatus().equals(AppointmentStatus.TREATMENT_DONE.value())) {
				a.setPrice(String.valueOf(appointment.getPrice()));
			}

			else {
				a.setPrice(AppointmentStatus.TREATMENT_PENDING.value());
			}

		}

		else {
			a.setDoctorContact(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
			a.setDoctorName(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
			a.setDoctorId(0);
			a.setPrice(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
			a.setPrescription(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());

		}

		a.setStatus(appointment.getStatus());
		a.setProblem(appointment.getProblem());
		a.setDate(appointment.getDate());
		a.setAppointmentDate(appointment.getAppointmentDate());
		a.setBloodGroup(patient.getBloodGroup());
		a.setId(appointment.getId());

		LOG.info("response sent!!!");
		return ResponseEntity.ok(a);
	}

	@GetMapping("patient/id")
	public ResponseEntity<?> getAllAppointmentsByPatientId(@RequestParam("patientId") int patientId) {
		LOG.info("recieved request for getting ALL Appointments by patient Id !!!");

		List<Appointment> appointments = this.appointmentService.getAppointmentByPatientId(patientId);

		List<AppointmentResponseDto> response = new ArrayList();

		for (Appointment appointment : appointments) {

			AppointmentResponseDto a = new AppointmentResponseDto();

			User patient = this.userService.getUserById(appointment.getPatientId());

			a.setPatientContact(patient.getContact());
			a.setPatientId(patient.getId());
			a.setPatientName(patient.getFirstName() + " " + patient.getLastName());

			if (appointment.getDoctorId() != 0) {
				User doctor = this.userService.getUserById(appointment.getDoctorId());
				a.setDoctorContact(doctor.getContact());
				a.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
				a.setDoctorId(doctor.getId());
				a.setPrescription(appointment.getPrescription());

				if (appointment.getStatus().equals(AppointmentStatus.TREATMENT_DONE.value())) {
					a.setPrice(String.valueOf(appointment.getPrice()));
				}

				else {
					a.setPrice(AppointmentStatus.TREATMENT_PENDING.value());
				}

			}

			else {
				a.setDoctorContact(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
				a.setDoctorName(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
				a.setDoctorId(0);
				a.setPrice(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
				a.setPrescription(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());

			}

			a.setStatus(appointment.getStatus());
			a.setProblem(appointment.getProblem());
			a.setDate(appointment.getDate());
			a.setAppointmentDate(appointment.getAppointmentDate());
			a.setBloodGroup(patient.getBloodGroup());
			a.setId(appointment.getId());

			response.add(a);

		}

		LOG.info("response sent!!!");
		return ResponseEntity.ok(response);
	}

	@GetMapping("doctor/id")
	public ResponseEntity<?> getAllAppointmentsByDoctorId(@RequestParam("doctorId") int doctorId) {
		LOG.info("recieved request for getting ALL Appointments by doctor Id !!!");

		List<Appointment> appointments = this.appointmentService.getAppointmentByDoctorId(doctorId);

		List<AppointmentResponseDto> response = new ArrayList();

		for (Appointment appointment : appointments) {

			AppointmentResponseDto a = new AppointmentResponseDto();

			User patient = this.userService.getUserById(appointment.getPatientId());

			a.setPatientContact(patient.getContact());
			a.setPatientId(patient.getId());
			a.setPatientName(patient.getFirstName() + " " + patient.getLastName());

			if (appointment.getDoctorId() != 0) {
				User doctor = this.userService.getUserById(appointment.getDoctorId());
				a.setDoctorContact(doctor.getContact());
				a.setDoctorName(doctor.getFirstName() + " " + doctor.getLastName());
				a.setDoctorId(doctor.getId());
				a.setPrescription(appointment.getPrescription());

				if (appointment.getStatus().equals(AppointmentStatus.TREATMENT_DONE.value())) {
					a.setPrice(String.valueOf(appointment.getPrice()));
				}

				else {
					a.setPrice(AppointmentStatus.TREATMENT_PENDING.value());
				}
				a.setPrescription(appointment.getPrescription());

			}

			else {
				a.setDoctorContact(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
				a.setDoctorName(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
				a.setDoctorId(0);
				a.setPrice(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
				a.setPrescription(AppointmentStatus.NOT_ASSIGNED_TO_DOCTOR.value());
			}

			a.setStatus(appointment.getStatus());
			a.setProblem(appointment.getProblem());
			a.setDate(appointment.getDate());
			a.setAppointmentDate(appointment.getAppointmentDate());
			a.setBloodGroup(patient.getBloodGroup());
			a.setId(appointment.getId());

			response.add(a);

		}

		LOG.info("response sent!!!");
		return ResponseEntity.ok(response);
	}

	@PostMapping("admin/assign/doctor")
	@ApiOperation(value = "Api to assign appointment to doctor")
	public ResponseEntity<?> updateAppointmentStatus(UpdateAppointmentRequest request) {
		LOG.info("Recieved request to assign appointment to doctor");

		CommanApiResponse response = new CommanApiResponse();

		if (request == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to assign appointment");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getDoctorId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Doctor not found");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		User doctor = this.userService.getUserById(request.getDoctorId());

		if (doctor == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Doctor not found");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getAppointmentId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Appointment not found");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		Appointment appointment = appointmentService.getAppointmentById(request.getAppointmentId());

		if (appointment == null) {
			throw new AppointmentNotFoundException();
		}

		if (appointment.getStatus().equals(AppointmentStatus.CANCEL.value())) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Appointment is cancel by patient");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		appointment.setDoctorId(request.getDoctorId());
		appointment.setStatus(AppointmentStatus.ASSIGNED_TO_DOCTOR.value());

		Appointment updatedAppointment = this.appointmentService.addAppointment(appointment);

		if (updatedAppointment != null) {
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage("Successfully Assigned Appointment to doctor");
			return new ResponseEntity(response, HttpStatus.OK);
		}

		else {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to assign");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("doctor/update")
	@ApiOperation(value = "Api to assign appointment to doctor")
	public ResponseEntity<?> assignAppointmentToDoctor(UpdateAppointmentRequest request) {
		LOG.info("Recieved request to update appointment");

		CommanApiResponse response = new CommanApiResponse();

		if (request == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to assign appointment");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getAppointmentId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Appointment not found");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		Appointment appointment = appointmentService.getAppointmentById(request.getAppointmentId());

		if (appointment == null) {
			throw new AppointmentNotFoundException();
		}

		appointment.setPrescription(request.getPrescription());
		appointment.setStatus(request.getStatus());

		if (request.getStatus().equals(AppointmentStatus.TREATMENT_DONE.value())) {
			appointment.setPrice(request.getPrice());
		}

		Appointment updatedAppointment = this.appointmentService.addAppointment(appointment);

		if (updatedAppointment != null) {
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage("Updated Treatment Status");
			return new ResponseEntity(response, HttpStatus.OK);
		}

		else {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to update");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("patient/update")
	@ApiOperation(value = "Api to update appointment patient")
	public ResponseEntity<?> udpateAppointmentStatus(@RequestBody UpdateAppointmentRequest request) {
		LOG.info("Recieved request to update appointment");

		CommanApiResponse response = new CommanApiResponse();

		if (request == null) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to assign appointment");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		if (request.getAppointmentId() == 0) {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Appointment not found");
			return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
		}

		Appointment appointment = appointmentService.getAppointmentById(request.getAppointmentId());

		if (appointment == null) {
			throw new AppointmentNotFoundException();
		}

		appointment.setStatus(request.getStatus());
		Appointment updatedAppointment = this.appointmentService.addAppointment(appointment);

		if (updatedAppointment != null) {
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage("Updated Treatment Status");
			return new ResponseEntity(response, HttpStatus.OK);
		}

		else {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to update");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
