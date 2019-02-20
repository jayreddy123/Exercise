package com.jayfaunadb.Exercise;

import java.util.ArrayList;

import com.faunadb.client.types.FaunaConstructor;
import com.faunadb.client.types.FaunaField;
import com.faunadb.client.types.Field;

public class Region {
	
    static final Field<Region> REGION_FIELD = Field.at("data").to(Region.class);

    @FaunaField private int regionID;
    @FaunaField private String name;
    @FaunaField private ArrayList<Territory> territories;

    @FaunaConstructor
    public Region(@FaunaField("regionID") int regionID,
                    @FaunaField("name") String name,
    				@FaunaField("territories") ArrayList<Territory> territories){
        this.regionID = regionID;
        this.name = name;
        this.territories = new ArrayList<Territory>();
    }
    
    public Region() {
    	this.regionID = 0;
    	this.name = "";
    	this.territories = new ArrayList<Territory>();
    }

	public int getRegionID() {
		return regionID;
	}

	public String getName() {
		return name;
	}

	public ArrayList<Territory> getTerritories() {
		return territories;
	}

	public void setRegionID(int regionID) {
		this.regionID = regionID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTerritories(ArrayList<Territory> territories) {
		this.territories = territories;
	}
}
