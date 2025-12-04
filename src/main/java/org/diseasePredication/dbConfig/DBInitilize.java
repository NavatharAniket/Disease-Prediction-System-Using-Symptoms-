package org.diseasePredication.dbConfig;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
public class DBInitilize {
	protected Connection conn;
	protected PreparedStatement stsmt;
	protected ResultSet rs;
	private FileInputStream fin;
	
	public DBInitilize()
	{
		try 
		{
			File f  = new File("");
			String rootPath = f.getAbsolutePath()+"\\src\\main\\resources\\db.properties";
			//System.out.println("absulute path is ");
			//System.out.println(rootPath);
			fin = new FileInputStream(rootPath);
			Properties p = new Properties();
			p.load(fin);
			
			String username = p.getProperty("username");
			String password = p.getProperty("password"); 
			String url = p.getProperty("url");
			String driverClass = p.getProperty("driver");
		
			Class.forName(driverClass);
			conn = DriverManager.getConnection(url,username,password);
			if(conn != null)
			{
				
			}
			else 
			{
				System.out.println("DataBase is not Connected");
			}
		}
		catch(Exception ex)
		{
			System.out.println("Error is "+ex);
		}
	}
/*
	public static void main(String [] args)
	{
		new DBInitilize();
	}
*/
}


	
	