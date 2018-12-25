package com.jayfaunadb.Exercise;

import java.util.ArrayList;
import java.util.List;

import com.faunadb.client.types.FaunaConstructor;
import com.faunadb.client.types.FaunaField;
import com.faunadb.client.types.Field;
import com.faunadb.client.types.Value.DateV;

public class Order {
    static final Field<Order> ORDER_FIELD = Field.at("data").to(Order.class);

    @FaunaField private int orderID;
    @FaunaField private String customerID;
    @FaunaField private int employeeID;
    @FaunaField private String orderDate;
    @FaunaField private String requiredDate;
    @FaunaField private String shippedDate;
    @FaunaField private int shipVia;
    @FaunaField private double freight;
    @FaunaField private String shipName;
    @FaunaField private Address shipAddress;
    @FaunaField private ArrayList<Details> details;
    
    @FaunaConstructor
    public Order(@FaunaField("orderID") int orderID,
                    @FaunaField("customerID") String customerID,
                    @FaunaField("employeeID") int employeeID,
                    @FaunaField("orderDate") String orderDate,
                    @FaunaField("requiredDate") String requiredDate,
                    @FaunaField("shippedDate") String shippedDate,
                    @FaunaField("shipVia") int shipVia,
                    @FaunaField("freight") double freight,
                    @FaunaField("shipName") String shipName,
                    @FaunaField("shipAddress") Address shipAddress,
    				@FaunaField("details") ArrayList<Details> details){
        this.orderID = orderID;
        this.customerID = customerID;
        this.employeeID = employeeID;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.shipVia = shipVia;
        this.freight = freight;
        this.shipName = shipName;
        this.shipAddress = shipAddress;
        this.details = new ArrayList<Details>();
    }



	public Order () {
        this.orderID = 0;
        this.customerID = "";
        this.employeeID = 0;
        this.orderDate = "";
        this.requiredDate = "";
        this.shippedDate = "";
        this.shipVia = 0;
        this.freight = 0.0;
        this.shipName ="";
        this.shipAddress = new Address();
        this.details = new ArrayList<Details>();
    }

	public int getOrderID() {
		return orderID;
	}

	public String getCustomerID() {
		return customerID;
	}

	public int getEmployeeID() {
		return employeeID;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public String getRequiredDate() {
		return requiredDate;
	}

	public String getShippedDate() {
		return shippedDate;
	}

	public int getShipVia() {
		return shipVia;
	}

	public double getFreight() {
		return freight;
	}

	public String getShipName() {
		return shipName;
	}

	public Address getShipAddress() {
		return shipAddress;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public void setCustomerID(String customerID) {
		this.customerID = customerID;
	}

	public void setEmployeeID(int employeeID) {
		this.employeeID = employeeID;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public void setRequiredDate(String requiredDate) {
		this.requiredDate = requiredDate;
	}

	public void setShippedDate(String shippedDate) {
		this.shippedDate = shippedDate;
	}

	public void setShipVia(int shipVia) {
		this.shipVia = shipVia;
	}

	public void setFreight(double freight) {
		this.freight = freight;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public void setShipAddress(Address shipAddress) {
		this.shipAddress = shipAddress;
	}
	
    public ArrayList<Details> getDetails() {
		return details;
	}

	public void setDetails(ArrayList<Details> details) {
		this.details = details;
	}
}



