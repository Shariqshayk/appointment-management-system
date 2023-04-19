package com.appointmentmanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.beans.BeanUtils;

import com.appointmentmanagement.dto.DoctorRegisterDto;
import com.appointmentmanagement.dto.UserLoginResponse;

import lombok.Data;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String firstName;

	private String lastName;

	private int age;

	private String sex;

	private String bloodGroup;

	private String emailId;

	private String contact;

	private String street;

	private String city;

	private String pincode;

	private String password;

	private String role;

	private String specialist;

	private int status;

	private String doctorImage;

	private int experience;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
		System.out.println(this.id);
	}

	public String getFirstName() {
		System.out.println(this.firstName);
		return firstName;		
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
		System.out.println(this.firstName);
	}

	public String getLastName() {
		System.out.println(this.lastName);
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
		System.out.println(this.firstName);
	}

	public int getAge() {
		System.out.println(this.age);
		return age;
	}

	public void setAge(int age) {
		this.age = age;
		System.out.println(this.age);
	}

	public String getSex() {
		System.out.println(this.sex);
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
		System.out.println(this.sex);
	}

	public String getBloodGroup() {
		System.out.println(this.bloodGroup);
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
		System.out.println(this.bloodGroup);
	}

	public String getEmailId() {
		System.out.println(this.emailId);
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
		System.out.println(this.emailId);
	}

	public String getContact() {
		System.out.println(this.contact);
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
		System.out.println(this.contact);
	}

	public String getStreet() {
		System.out.println(this.street);
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
		System.out.println(this.street);
	}

	public String getCity() {
		System.out.println(this.city);
		return city;
	}

	public void setCity(String city) {
		this.city = city;
		System.out.println(this.city);
	}

	public String getPincode() {
		System.out.println(this.pincode);
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
		System.out.println(this.pincode);	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		System.out.println(this.role);
		return role;
	}

	public void setRole(String role) {
		this.role = role;
		System.out.println(this.role);
	}

	public String getSpecialist() {
		System.out.println(this.specialist);
		return specialist;
	}

	public void setSpecialist(String specialist) {
		this.specialist = specialist;
		System.out.println(this.specialist);
	}

	public int getStatus() {
		System.out.println(this.status);
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		System.out.println(this.status);
	}

	public String getDoctorImage() {
		return doctorImage;
	}

	public void setDoctorImage(String doctorImage) {
		this.doctorImage = doctorImage;
	}

	public int getExperience() {
		System.out.println(this.experience);
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
		System.out.println(this.experience);
	}

	public static UserLoginResponse toUserLoginResponse(User user) {
		UserLoginResponse userLoginResponse = new UserLoginResponse();
		BeanUtils.copyProperties(user, userLoginResponse, "password");
		return userLoginResponse;
	}

	public static DoctorRegisterDto toUserDto(User user) {
		DoctorRegisterDto userDto = new DoctorRegisterDto();
		BeanUtils.copyProperties(user, userDto, "password");
		return userDto;
	}

}
