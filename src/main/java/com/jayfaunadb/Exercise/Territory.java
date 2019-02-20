package com.jayfaunadb.Exercise;

import com.faunadb.client.types.FaunaConstructor;
import com.faunadb.client.types.FaunaField;
import com.faunadb.client.types.Field;

public class Territory {
	
    static final Field<Territory> TERRITORY_FIELD = Field.at("data").to(Territory.class);

    @FaunaField private int  territoryID;
    @FaunaField private String name;

    
    @FaunaConstructor
    public Territory(@FaunaField("territoryID") int  territoryID,
                    @FaunaField("name") String name) {
        this.territoryID = territoryID;
        this.name = name;
    }
    
    public Territory() {
		this.territoryID = 0;
		this.name = "";
    }

	public int getTerritoryID() {
		return territoryID;
	}

	public String getName() {
		return name;
	}

	public void setTerritoryID(int territoryID) {
		this.territoryID = territoryID;
	}

	public void setName(String name) {
		this.name = name;
	}
}
