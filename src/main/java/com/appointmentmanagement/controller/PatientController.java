package com.appointmentmanagement.controller;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.appointmentmanagement.entity.User;
import com.appointmentmanagement.service.UserService;
import com.appointmentmanagement.utility.Constants.BloodGroup;
import com.appointmentmanagement.utility.Constants.UserRole;

@RestController
@RequestMapping("api/patient/")
@CrossOrigin(origins = "http://localhost:3000")
public class PatientController {
	
	Logger LOG = LoggerFactory.getLogger(PatientController.class);

	@Autowired
	private UserService userService;

	@GetMapping("/bloodgroup/all")
	public ResponseEntity<?> getAllBloodGroups() {
		
		LOG.info("Received the request for getting all the Blood Groups");
		
		List<String> bloodGroups = new ArrayList<>();

		for (BloodGroup bg : BloodGroup.values()) {
			bloodGroups.add(bg.value());
		}
		
		LOG.info("Response Sent!!!");

		return new ResponseEntity(bloodGroups, HttpStatus.OK);
	}
	
	@GetMapping("all")
	public ResponseEntity<?> getAllPatient() {
		LOG.info("recieved request for getting ALL Customer!!!");
		
		List<User> patients = this.userService.getAllUserByRole(UserRole.PATIENT.value());
		
		LOG.info("response sent!!!");
		return ResponseEntity.ok(patients);
	}
	

}
