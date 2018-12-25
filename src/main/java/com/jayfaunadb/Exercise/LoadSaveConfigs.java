package com.jayfaunadb.Exercise;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadSaveConfigs {

	public void saveProperties(Properties p) throws IOException
	{
        FileOutputStream fr = new FileOutputStream("./resources/config.properties");
        p.store(fr, "Properties");
        fr.close();	
	}
	
    public void loadProperties(Properties p)throws IOException
    {
        FileInputStream fi=new FileInputStream("./resources/config.properties");
        p.load(fi);
        fi.close();
    }	
}
