package models;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class Student {
	private int id;
	private String name;
	private int age;
	private String dob, address, ph, email;
	private String nrc;
	public Student() {
		
	}
	public Student(int id, String name,int age, String dob, String address, String ph, String email, String nrc) {
		this.id = id;
		this.age = age;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.ph = ph;
		this.email = email;
		this.nrc = nrc;
	}
	
	public Student(int id, String name, String dob, String address, String ph, String email, String nrc) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.ph = ph;
		this.email = email;
		this.nrc = nrc;
	}
	public Student(String name, String dob, String address, String ph, String email, String nrc) {
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.ph = ph;
		this.email = email;
		this.nrc = nrc;
		
	}
	public String getNrc() {
		return nrc;
	}
	public void setNrc(String nrc) {
		this.nrc = nrc;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPh() {
		return ph;
	}
	public void setPh(String ph) {
		this.ph = ph;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public int calAge(String dob) {
		String year = dob.substring(6);
		String month = dob.substring(0,2);
		String day = dob.substring(3,5);
		System.out.println("Year: "+year+", Month: "+month+", day: "+day);
		LocalDate birthDate = LocalDate.parse(year+"-"+month+"-"+day);
		LocalDate currentDate = LocalDate.now(ZoneId.systemDefault());
		Period period = Period.between(birthDate, currentDate);
				
		return period.getYears();
	}
	
}
