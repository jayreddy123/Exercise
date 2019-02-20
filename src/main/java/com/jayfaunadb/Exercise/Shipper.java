package com.jayfaunadb.Exercise;

import com.faunadb.client.types.FaunaConstructor;
import com.faunadb.client.types.FaunaField;
import com.faunadb.client.types.Field;

public class Shipper {

	
    static final Field<Shipper> SHIPPER_FIELD = Field.at("data").to(Shipper.class);

    @FaunaField private int shipperID;
    @FaunaField private String companyName;
    @FaunaField private String Phone;
    
    @FaunaConstructor
    public Shipper(@FaunaField("supplierId") int shipperID,
                    @FaunaField("companyName") String companyName,
                    @FaunaField("Phone") String Phone) {
        this.shipperID = shipperID;
        this.companyName = companyName;
        this.Phone = Phone;      
    }
    
    public Shipper () {
        this.shipperID = 0;
        this.companyName = "";
        this.Phone = "";
    }

	public int getShipperID() {
		return shipperID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getPhone() {
		return Phone;
	}

	public void setShipperID(int shipperID) {
		this.shipperID = shipperID;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}
}
