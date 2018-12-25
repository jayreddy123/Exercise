package com.jayfaunadb.Exercise;

/*
 * These imports are for basic functionality around logging and JSON handling and Futures.
 * They should best be thought of as a convenience items for our exercises.
 */
import com.faunadb.client.query.Expr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Properties;

import com.faunadb.client.*;
import com.faunadb.client.types.*;

import static com.faunadb.client.query.Language.*;

public class IndexClasses {

	private static final Logger logger = LoggerFactory.getLogger(IndexClasses.class);

	private static ObjectMapper mapper = getMapper();

	private static ObjectMapper getMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

		return mapper;
	}

	private static String toPrettyJson(Value value) throws JsonProcessingException {
		return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);
	}

	private static void createIndex(FaunaClient client, Expr indexDef, boolean waitForIndexToBeActive)
			throws Exception {
		/*
		 * This is a generalized example of Fauna Query Language composable nature. The
		 * function accepts a type of Epr which will be the definition of the index.
		 */
		Value result = client.query(CreateIndex(indexDef)).get();
		String indexName = result.at("name").to(String.class).get();
		logger.info("Created index: {} \n{}", indexName, toPrettyJson(result));

		if (waitForIndexToBeActive) {
			waitForIndexToBuild(client, indexName);
		}
	}

	/*
	 * When you create a new index it can take it a little while to become active.
	 * In other words, it may take it a whil before it has fully added all instances
	 * of the class being indexed. If you try to query before it is active you may
	 * not see all instances of the data.
	 */
	private static void waitForIndexToBuild(FaunaClient client, String indexName) throws Exception {
		int totalWaitMillis = 120000; // maximum time to wait for build 2 minutes
		int intervalMillis = 5000; // check every 5 seconds
		int maxIntervals = totalWaitMillis / intervalMillis;

		logger.info("Waiting for index {} to become active...", indexName);
		for (int i = 0; i < maxIntervals; i++) {
			try {
				Value result = client.query(Select(Value("active"), Get(Index(indexName)))).get();
				if (result.to(Boolean.class).get()) {
					logger.info("Index {} is active.", indexName);
					return;
				}
				Thread.sleep(intervalMillis);
			} catch (InterruptedException ie) {
			}
		}
	}
	
    public static void getProductsByCategory(FaunaClient client, String categoryName) throws Exception
    {
    	Value result = client.query(
    			Map(
    			Paginate(
    			  Join(
    			    Match(Index("category_by_id"), Value(categoryName)),
    			    Index("products_by_category_id")
    			  )      
    			),
    			Lambda(Value("products"),Select(Value("data"),Get(Var("products")))))).get();
    	logger.info("Products for : {} :: \n{}", categoryName, toPrettyJson(result));
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
        
        Expr categoryByIdIdx = Obj(
                "name", Value("category_by_id"),
                "source", Class(Value("categories")),
                "terms", Arr(Obj("field", Arr(Value("data"), Value("name")))),
                "values", Arr(Obj("field", Arr(Value("data"), Value("categoryID"))))
        );
       
        createIndex(client, categoryByIdIdx, true);

        Expr productsByCategoryIdIdx = Obj(
                "name", Value("products_by_category_id"),
                "source", Class(Value("products")),
                "terms", Arr(Obj("field", Arr(Value("data"), Value("categoryID"))))
        );
        
        createIndex(client, productsByCategoryIdIdx, true);     
             
        
        getProductsByCategory(client, "Condiments");
        
        client.close();
        logger.info("Disconnected from FaunaDB");

        // add this at the end of execution to make things shut down nicely
        System.exit(0);
        
		
	}
}

