package org.tomcat.external.source;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Properties;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.apache.tomcat.util.IntrospectionUtils;

/**
 * Externalize the tomcat properties
 * 
 * @author
 *
 */
public class ExternalPropertySource implements IntrospectionUtils.PropertySource {
	public static final String PROP_FILES = ExternalPropertySource.class.getName() + ".PROPPATH";
	private static final Log LOGGER = LogFactory.getLog(ExternalPropertySource.class);
	private final Properties properties = new Properties();

	public ExternalPropertySource() throws IOException {
	    LOGGER.debug("Creating ExternalPropertySource");
	    LOGGER.debug("Relative filenames are resolved against USER.DIR which is: " + System.getProperty("user.dir"));

        /* String propfiles = System.getProperty(PROP_FILES); */
        String propfiles = System.getProperty("catalina.base");
	    if (propfiles != null) {
	        File propertyFile = new File(propfiles + "/conf");
		    loadProperties(propertyFile);
	    } else {
		    LOGGER.debug("No properties file specified");
	    }
	}

	/**
	 * Get property
	 */
	public String getProperty(String key) {
	    if (properties == null)
		    return null;

        return properties.getProperty(key);
	}

	/**
	 * Load properties
	 * 
	 * @param propertyFile
	 * @throws IOException
	 */
	private void loadProperties(File propertyFile) throws IOException {
	    LOGGER.info("Reading properties from external file: " + propertyFile.getAbsolutePath());
      	loadNonEncryptedProperties(propertyFile);
	    if (properties.size() == 0) {
		    LOGGER.warn("No properties were found in external file");
	    }
	}

	/**
	 * Get the non encrypted properties
	 * 
	 * @param propertyFile - Property file
	 * @throws IOException
	 */
	private void loadNonEncryptedProperties(File propertyFile) throws IOException {
        if (propertyFile.isDirectory()) {
		    File propFiles[] = propertyFile.listFiles(new FilenameFilter() {

    		   @Override
		       public boolean accept(File dir, String name) {
			       return name.endsWith(".properties");
		       }
	        });

    	    if (null != propFiles && propFiles.length > 0) {
	           for (File file : propFiles) {
		            properties.load(new FileInputStream(file));
		       }
	        } else {
		       LOGGER.error("Given directory does not have property files");
	        }
	    } else {
			LOGGER.error("Given path is not directory path of property files");
    	}

	}
}
