package com.jayfaunadb.Exercise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import com.faunadb.client.*;
import com.faunadb.client.types.*;
import static com.faunadb.client.query.Language.*;

public class DataLoading {


    private static int intervalMillis = 10000; 
    
    private static final Logger logger = LoggerFactory.getLogger(DataLoading.class);

    private static ObjectMapper mapper = getMapper();

    private static ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return mapper;
    }

    private static String toPrettyJson(Value value) throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
    }
    
    private static void createClass(FaunaClient client, String className) throws Exception {
        /*
         * Create the class to store the data instances
         */
        Value result = client.query(
                CreateClass(Obj("name", Value(className)))
        ).get();
        logger.info("Created data class : {} :: \n{}", className, toPrettyJson(result));
    }
    
    
    private static void createClassIndex(FaunaClient client, String className) throws Exception {
        /*
         * Create a class level index. This is really a convenience feature that lets us
         * evaluate if the data loaded properly.
         */
        String classIndexName = className + "_all";
        Value result = client.query(
                // This index supports primary key access. Equality constraints only
                    CreateIndex(
                            Obj(
                                    "name", Value(classIndexName),
                                    "source", Class(Value(className))
                            )
                    )
        ).get();
        logger.info("Created {} index :: \n{}", classIndexName, toPrettyJson(result));
    }
    
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void loadData(FaunaClient client, Class dataClass, String dataTypeName, String jsonFilePath)
			throws Exception {
//
// This is cheesy. If the file was really big you would stream it
// I am cheating by reading the whole thing and then parsing
//
		JsonNode rootNode = mapper.readTree(new File(jsonFilePath));
		Iterator<JsonNode> it = rootNode.elements();

		ObjectMapper objectMapper = getMapper();
		while (it.hasNext()) {

			Object data = dataClass.newInstance();
			data = objectMapper.readValue(it.next().toString(), dataClass);

			client.query(Create(Class(Value(dataTypeName)), Obj("data", Value(data)))).get(); 
			// this makes the call Async. We could always catch the futures and check later.
		}
		logger.info("Loaded {} {} records.", rootNode.size(), dataTypeName);

	}
    

	public static void main(String[] args) throws Exception {
		
		Properties props = new Properties();
		LoadSaveConfigs config = new LoadSaveConfigs();
		config.loadProperties(props);
		String endpoint = props.getProperty("endpoint");
		String secret = props.getProperty("clientSecret");


		FaunaClient client = FaunaClient.builder()
                .withEndpoint(endpoint)
                .withSecret(secret)
                .build();
        logger.info("Connected to FaunaDB"); 
        
        ArrayList<String> ClassList = new ArrayList<String>();
       ClassList.add("products");
        ClassList.add("categories");
        ClassList.add("customers");
        ClassList.add("orders");
        ClassList.add("suppliers");
        ClassList.add("employees");
        ClassList.add("shippers");
        ClassList.add("regions");
        
		for (String ClassName : ClassList) {
			createClass(client, ClassName);
			Thread.sleep(intervalMillis);
			createClassIndex(client, ClassName);
			Thread.sleep(intervalMillis); 
			
			if (ClassName.equals("products"))  {
				loadData(client, Product.class, ClassName, "./resources/northwinds-json/products.json");
			} else if (ClassName.equals("categories")) {
				loadData(client, Category.class, ClassName, "./resources/northwinds-json/categories.json");
			} else if (ClassName.equals("customers")) {
				loadData(client, Customer.class, ClassName, "./resources/northwinds-json/customers.json");
			} else if (ClassName.equals("orders")) {
				loadData(client, Order.class, ClassName, "./resources/northwinds-json/orders.json");
			} else if (ClassName.equals("suppliers")) {
				loadData(client, Supplier.class, ClassName, "./resources/northwinds-json/suppliers.json");
			} else if (ClassName.equals("employees")) {
				loadData(client, Employee.class, ClassName, "./resources/northwinds-json/employees.json");
			} else if (ClassName.equals("shippers")) {
				loadData(client, Shipper.class, ClassName, "./resources/northwinds-json/shippers.json");
			} else if (ClassName.equals("regions")) {
				loadData(client, Region.class, ClassName, "./resources/northwinds-json/regions.json");
			} else {
				logger.info("Choose correct class to load");
			}
			Thread.sleep(intervalMillis);
		}
              

        //
        // Just to keep things neat and tidy, close the client connections
        //
        client.close();
        logger.info("Disconnected from FaunaDB");

        // add this at the end of execution to make things shut down nicely
        System.exit(0);
		
	}

}
