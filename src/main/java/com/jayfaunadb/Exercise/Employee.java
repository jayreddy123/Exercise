package com.jayfaunadb.Exercise;

import java.util.ArrayList;

import com.faunadb.client.types.FaunaConstructor;
import com.faunadb.client.types.FaunaField;
import com.faunadb.client.types.Field;

public class Employee {

	static final Field<Employee> EMPLOYEE_FIELD = Field.at("data").to(Employee.class);

	
	@FaunaField private int employeeID;
	@FaunaField private String lastName;
	@FaunaField private String firstName;
	@FaunaField private String title;
	@FaunaField private String titleOfCourtesy;
	@FaunaField private String birthDate;
	@FaunaField private String hireDate;
	@FaunaField private Address address;
	@FaunaField private String notes;
	@FaunaField private ArrayList<Integer> territoryIDs;
	
    @FaunaConstructor
    public Employee(@FaunaField("employeeID") int employeeID,
                    @FaunaField("lastName") String lastName,
                    @FaunaField("firstName") String firstName,
                    @FaunaField("title") String title,
                    @FaunaField("titleOfCourtesy") String titleOfCourtesy,
                    @FaunaField("birthDate") String birthDate,
                    @FaunaField("hireDate") String hireDate,
                    @FaunaField("address") Address address,
                    @FaunaField("notes") String notes,                   
    				@FaunaField("territoryIDs") ArrayList<Integer> territoryIDs){
        this.employeeID = employeeID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.title = title;
        this.titleOfCourtesy = titleOfCourtesy;
        this.birthDate = birthDate;
        this.hireDate = hireDate;
        this.address = address;
        this.notes = notes;
        this.territoryIDs = territoryIDs;
    }
	
	public Employee () {
		
        this.employeeID = 0;
        this.lastName = "";
        this.firstName = "";
        this.title = "";
        this.titleOfCourtesy = "";
        this.birthDate = "";
        this.hireDate = "";
        this.address = new Address();
        this.notes = "";
        this.territoryIDs = new ArrayList<Integer>();
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getTitle() {
		return title;
	}

	public String getTitleOfCourtesy() {
		return titleOfCourtesy;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getHireDate() {
		return hireDate;
	}

	public Address getAddress() {
		return address;
	}

	public String getNotes() {
		return notes;
	}

	public ArrayList<Integer> getTerritoryIDs() {
		return territoryIDs;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitleOfCourtesy(String titleOfCourtesy) {
		this.titleOfCourtesy = titleOfCourtesy;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setTerritoryIDs(ArrayList<Integer> territoryIDs) {
		this.territoryIDs = territoryIDs;
	}
	
	
}
