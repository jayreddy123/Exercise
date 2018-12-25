package com.jayfaunadb.Exercise;

import com.faunadb.client.types.FaunaConstructor;
import com.faunadb.client.types.FaunaField;
import com.faunadb.client.types.Field;

public class Details {

    static final Field<Details> DETAILS_FIELD = Field.at("data").to(Details.class);

    @FaunaField private int  productID;
    @FaunaField private double unitPrice;
    @FaunaField private int quantity;
    @FaunaField private double discount;
    
    @FaunaConstructor
    public Details(@FaunaField("productID") int  productID,
                    @FaunaField("unitPrice") double unitPrice,
                    @FaunaField("quantity") int quantity,
                    @FaunaField("discount") double discount) {
        this.productID = productID;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.discount = discount;
    }
    
    public Details() {
		this.productID = 0;
		this.unitPrice = 0.00;
		this.quantity = 0;
		this.discount = 0.00;
    }

	public int getProductID() {
		return productID;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getDiscount() {
		return discount;
	}

	public void setProductID(int productID) {
		this.productID = productID;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}
}
