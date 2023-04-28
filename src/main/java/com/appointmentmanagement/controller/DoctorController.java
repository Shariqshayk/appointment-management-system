package com.appointmentmanagement.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.appointmentmanagement.dto.CommanApiResponse;
import com.appointmentmanagement.dto.DoctorRegisterDto;
import com.appointmentmanagement.entity.User;
import com.appointmentmanagement.service.UserService;
import com.appointmentmanagement.utility.Constants.DoctorSpecialist;
import com.appointmentmanagement.utility.Constants.ResponseCode;
import com.appointmentmanagement.utility.Constants.UserRole;
import com.appointmentmanagement.utility.Constants.UserStatus;
import com.appointmentmanagement.utility.StorageService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("api/doctor/")
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {
	
	Logger LOG = LoggerFactory.getLogger(DoctorController.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;

	@PostMapping("register")
	@ApiOperation(value = "Api to register doctor")
	public ResponseEntity<?> registerDoctor(DoctorRegisterDto doctorRegisterDto) {
		LOG.info("Recieved request for doctor register");

		CommanApiResponse response = new CommanApiResponse();
		
		User user = DoctorRegisterDto.toEntity(doctorRegisterDto);

		String image = storageService.store(doctorRegisterDto.getImage());
		
		user.setDoctorImage(image);
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());

		user.setPassword(encodedPassword);
		user.setStatus(UserStatus.ACTIVE.value());

		User registerUser = userService.registerUser(user);

		if (registerUser != null) {
			response.setResponseCode(ResponseCode.SUCCESS.value());
			response.setResponseMessage(user.getRole() + " Doctor Registered Successfully");
			return new ResponseEntity(response, HttpStatus.OK);
		}

		else {
			response.setResponseCode(ResponseCode.FAILED.value());
			response.setResponseMessage("Failed to Register Doctor");
			return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("all")
	public ResponseEntity<?> getAllDoctor() {
		LOG.info("recieved request for getting ALL Customer!!!");
		
		List<User> doctors = this.userService.getAllUserByRole(UserRole.DOCTOR.value());
		
		LOG.info("response sent!!!");
		return ResponseEntity.ok(doctors);
	}
	
	@GetMapping(value = "/{doctorImageName}", produces = "image/*")
	@ApiOperation(value = "Api to fetch doctor image by using image name")
	public void fetchProductImage(@PathVariable("doctorImageName") String doctorImageName, HttpServletResponse resp) {
		LOG.info("request came for fetching doctor pic");
		LOG.info("Loading file: " + doctorImageName);
		Resource resource = storageService.load(doctorImageName);
		if (resource != null) {
			try (InputStream in = resource.getInputStream()) {
				ServletOutputStream out = resp.getOutputStream();
				FileCopyUtils.copy(in, out);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		LOG.info("response sent!");
	}
	
	@GetMapping("/specialist/all")
	public ResponseEntity<?> getAllSpecialist() {

		LOG.info("Received the request for getting as Specialist");
		
		List<String> specialists = new ArrayList<>();

		for (DoctorSpecialist s : DoctorSpecialist.values()) {
			specialists.add(s.value());
		}
		
		LOG.info("Response sent!!!");

		return new ResponseEntity(specialists, HttpStatus.OK);
	}

}
