package hu.bme.viaum105;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class Util {

    private static final Log log = LogFactory.getLog(Util.class);

    private static final char[] HEXADECIMAL = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * MD5 hashelés
     * 
     * @param string
     * @return
     */
    public static String md5Hash(String string) {
	StringBuilder sb = null;

	try {
	    MessageDigest md = MessageDigest.getInstance("MD5"); //$NON-NLS-1$
	    md.reset();

	    byte[] bytes = md.digest(string.getBytes());
	    sb = new StringBuilder(2 * bytes.length);
	    for (int i = 0; i < bytes.length; i++) {
		int low = (bytes[i] & 0x0f);
		int high = ((bytes[i] & 0xf0) >> 4);
		sb.append(Util.HEXADECIMAL[high]);
		sb.append(Util.HEXADECIMAL[low]);
	    }
	} catch (NoSuchAlgorithmException e) {
	    Util.log.error(e.getMessage(), e);
	}

	return sb != null ? sb.toString() : "";
    }

    private Util() {
	throw new UnsupportedOperationException("This class should not be instantiated");
    }

}
