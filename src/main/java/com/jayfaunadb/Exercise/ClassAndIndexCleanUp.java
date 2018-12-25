package com.jayfaunadb.Exercise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;

/*
 * These are the required imports for Fauna.
 *
 * For these examples we are using the 2.1.0 version of the JVM driver. Also notice that we aliasing
 * the query and values part of the API to make it more obvious we we are using Fauna functionality.
 *
 */
import com.faunadb.client.*;
import com.faunadb.client.types.*;
import static com.faunadb.client.query.Language.*;

import java.util.ArrayList;
import java.util.Properties;

public class ClassAndIndexCleanUp {

	private static final Logger logger = LoggerFactory.getLogger(ConnectToDB.class);
	
	private static ObjectMapper mapper = getMapper();
	
	private static ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}
	
	private static String toPrettyJson(Value value) throws JsonProcessingException {
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
	}
	
    private static void deleteClass(FaunaClient client, String className) throws Exception {
        /// Delete Class
        Value result = client.query(Delete(Class(className))).get();
        logger.info("Deleted data class : {} :: \n{}", className, toPrettyJson(result));
    }
    
    
    private static void deleteClassIndex(FaunaClient client, String className) throws Exception {
        // Delete Class level Index
        String classIndexName = className + "_all";
        Value result = client.query(Delete(Index(classIndexName))).get();
        logger.info("Deleted {} index :: \n{}", classIndexName, toPrettyJson(result));
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
        logger.info("Succesfully connected to FaunaDB as Admin!");
        
        ArrayList<String> ClassList = new ArrayList<String>();

       // ClassList.add("products");
       // ClassList.add("categories");
      //  ClassList.add("customers");
        ClassList.add("orders");
        
		for (String ClassName : ClassList) {
			deleteClass(client, ClassName);
			deleteClassIndex(client, ClassName);  
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
