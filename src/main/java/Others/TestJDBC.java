package Others;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class TestJDBC {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = factory.getCurrentSession();
        try {
        	
        	//Recipe recipe = new Recipe("12391230", "1234567823", "1", "1", "1", 
        	//		Date.valueOf("2025-02-17"), Date.valueOf("2025-02-17"), 23);
        	
        	User user = new User ("12345", "12345");
            session.beginTransaction();
            System.out.println("Saving the student...");
            //session.save(recipe);
            session.save(user);
            session.getTransaction().commit();
            System.out.println("Done!");
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}
