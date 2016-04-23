package util;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


public class DB {
	public static SqlSessionFactory sessionFactory;
	static {
		try{
		// class path
//		String resource = "/src/Configuration.xml";
//		String resource = "/Configuration.xml";
			
		String resource = "Configuration.xml";



		
		Reader reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder()
			.build(reader);
		}
		catch (Exception e) {
		 e.printStackTrace();
		}
	}
}
