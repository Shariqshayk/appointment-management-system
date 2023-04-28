package com.appointmentmanagement.utility;

public class Constants {
	
	public enum UserRole {
		ADMIN("admin"),
		PATIENT("patient"),
		DOCTOR("doctor");
		
		
		private String role;

	    private UserRole(String role) {
	      this.role = role;
	    }

	    public String value() {
	      return this.role;
	    }    
	}
	
	public enum Sex {
		MALE("Male"),
		FEMALE("Female");
		
		
		private String sex;

	    private Sex(String sex) {
	      this.sex = sex;
	    }

	    public String value() {
	      return this.sex;
	    }    
	}
	
	public enum AppointmentStatus {
		ASSIGNED_TO_DOCTOR("Assigned to Doctor"),
		PENDING("Pending"),
		CANCEL("Cancel"),
		NOT_ASSIGNED_TO_DOCTOR("Not Assigned to Doctor"),
		TREATMENT_DONE("Treatment Done"),
		TREATMENT_PENDING("Treatment pending");
		
		private String status;

	    private AppointmentStatus(String status) {
	      this.status = status;
	    }

	    public String value() {
	      return this.status;
	    }    
	}
	
	public enum ResponseCode {
		SUCCESS(0),
		FAILED(1);
		
		private int code;

	    private ResponseCode(int code) {
	      this.code = code;
	    }

	    public int value() {
	      return this.code;
	    }    
	}
	
	public enum BloodGroup {
		A_POSITIVE("A+"),
		A_NEGATIVE("A-"),
		B_POSITIVE("B+"),
		B_NEGATIVE("B-"),
		O_POSITIVE("O+"),
		O_NEGATIVE("O-"),
		AB_POSITIVE("AB+"),
		AB_NEGATIVE("AB-");
		
		private String type;

	    private BloodGroup(String type) {
	      this.type = type;
	    }

	    public String value() {
	      return this.type;
	    }

	    public boolean equals(String type) {
	      return this.type.equals(type.toUpperCase());
	    }
	}
	
    public enum DoctorSpecialist {
		
		IMMUNOLOGISTS("Immunologists"),
		ANESTHESIOLOGISTS("Anesthesiologists"),
		CARDIOLOGISTS("Cardiologists"),
		DERMATOLOGISTS("Dermatologists"),
		ENDOCRINNOLOGISTS("Endocrinologists"),
		GASTROENTEROLOGISTS("Gastroenterologists"),
		HEMATOLOGISTS("Hematologists");
		
		private String type;

	    private DoctorSpecialist(String type) {
	      this.type = type;
	    }

	    public String value() {
	      return this.type;
	    }

	    public boolean equals(String type) {
	      return this.type.equals(type.toUpperCase());
	    }
	}
    
    public enum UserStatus {
		ACTIVE(0),
		DELETED(1);
		
		private int status;

	    private UserStatus(int status) {
	      this.status = status;
	    }

	    public int value() {
	      return this.status;
	    }    
	}
	
}
