package com.jayfaunadb.Exercise;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

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

public class ConnectToDB {

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
	
	public static void main(String[] args) throws Exception {
		
		
		Properties props = new Properties();
		LoadSaveConfigs config = new LoadSaveConfigs();
		config.loadProperties(props);

		String endpoint = props.getProperty("endpoint");
		String secret = props.getProperty("adminSecret");
        String dbName = props.getProperty("dbName");
        
        logger.info(endpoint);
        logger.info(secret);
        logger.info(dbName);
        
        FaunaClient adminClient = FaunaClient.builder()
                .withEndpoint(endpoint)
                .withSecret(secret)
                .build();
        logger.info("Succesfully connected to FaunaDB as Admin!");

        
        Value result = adminClient.query(
                Arr(
                        If(
                                Exists(Database(dbName)),
                                Delete(Database(dbName)),
                                Value(true)
                        ),
                        CreateDatabase(Obj("name", Value(dbName)))
                )
        ).get();
        logger.info("Created database: {} :: \n{}", dbName, toPrettyJson(result));
        
        /*
         * Create a key specific to the database we just created. We will use this to
         * create a new client we will use in the remainder of the exercise.
         */
        result = adminClient.query(
                CreateKey(Obj("database", Database(Value(dbName)), "role", Value("admin")))
        ).get();
        String clientSecret = result.at("secret").to(String.class).get();
        logger.info("DB {} secret: {}", dbName, clientSecret);
        
        props.setProperty("clientSecret", clientSecret);

        config.saveProperties(props);
                
        //
        // Just to keep things neat and tidy, close the client connections
        //
        adminClient.close();
        logger.info("Disconnected from FaunaDB");

        // add this at the end of execution to make things shut down nicely
        System.exit(0);
	}
}
