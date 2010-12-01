import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import hu.bme.viaum105.Util;
import hu.bme.viaum105.data.nonpersistent.Role;
import hu.bme.viaum105.data.persistent.User;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SchemaGen {

    private static final Log LOG = LogFactory.getLog(SchemaGen.class);

    private static final String DEFAULT_PASSWORD = "adminadmin";

    private static final String DEFAULT_USER = "admin";

    public static void main(String[] args) {
	try {
	    System.out.println("Initializing persistence...");
	    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("SERIESPORTAL");
	    System.out.println("Inserting default data...");
	    new SchemaGen(entityManagerFactory).insertDefaultData();
	    System.out.println("A user with administrator privileges was successfully created with the following credentials:\n" + //
		    "loginname: \"" + SchemaGen.DEFAULT_USER + "\"\n" + //
		    "password: \"" + SchemaGen.DEFAULT_PASSWORD + "\"\n" + //
		    "Please note that quotation marks are to be ignored!");
	} catch (Exception e) {
	    System.out.println("An error has occured during the schema generation process: " + e.getMessage());
	    SchemaGen.LOG.error(e.getMessage(), e);
	}
    }

    private final EntityManagerFactory entityManagerFactory;

    public SchemaGen(EntityManagerFactory entityManagerFactory) {
	this.entityManagerFactory = entityManagerFactory;
    }

    private void insertDefaultData() {
	EntityManager em = this.entityManagerFactory.createEntityManager();
	try {
	    User adminUser = new User();
	    adminUser.setDisplayName("Adminisztrátor");
	    adminUser.setLoginName(SchemaGen.DEFAULT_USER);
	    adminUser.setPassword(Util.md5Hash(SchemaGen.DEFAULT_PASSWORD));
	    adminUser.setRole(Role.ADMIN);

	    em.getTransaction().begin();
	    em.merge(adminUser);
	    em.getTransaction().commit();
	} finally {
	    if (em.getTransaction().isActive()) {
		em.getTransaction().rollback();
	    }
	    em.close();
	}
    }

}
