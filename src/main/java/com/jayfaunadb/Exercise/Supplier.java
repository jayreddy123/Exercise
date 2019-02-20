package com.jayfaunadb.Exercise;

import com.faunadb.client.types.FaunaConstructor;
import com.faunadb.client.types.FaunaField;
import com.faunadb.client.types.Field;

public class Supplier {

    static final Field<Supplier> SUPPLIER_FIELD = Field.at("data").to(Supplier.class);

    @FaunaField private int supplierId;
    @FaunaField private String companyName;
    @FaunaField private String contactName;
    @FaunaField private String contactTitle;
    @FaunaField private Address address;
    
    @FaunaConstructor
    public Supplier(@FaunaField("supplierId") int supplierId,
                    @FaunaField("companyName") String companyName,
                    @FaunaField("contactName") String contactName,
                    @FaunaField("contactTitle") String contactTitle,
                    @FaunaField("address") Address address) {
        this.supplierId = supplierId;
        this.companyName = companyName;
        this.contactName = contactName;
        this.contactTitle = contactTitle;
        this.address = address;
    }
    
    public Supplier () {
        this.supplierId = 0;
        this.companyName = "";
        this.contactName = "";
        this.contactTitle = "";
        this.address = new Address();
    }

	public int getSupplierId() {
		return supplierId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public String getContactName() {
		return contactName;
	}

	public String getContactTitle() {
		return contactTitle;
	}

	public Address getAddress() {
		return address;
	}

	public void setSupplierId(int supplierId) {
		this.supplierId = supplierId;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setContactTitle(String contactTitle) {
		this.contactTitle = contactTitle;
	}

	public void setAddress(Address address) {
		this.address = address;
	} 
}
