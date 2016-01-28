/**
 * 
 */
package com.msip.external;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author juanz
 *
 */
public class Utility {

	public static String getHashedPassword(String password) throws NoSuchAlgorithmException {
		return hashsha256(password);
	}
	
	public static String hashsha256(String password) throws NoSuchAlgorithmException {
	    MessageDigest sha256 = MessageDigest.getInstance("SHA-256");        
	    byte[] passBytes = password.getBytes();
	    byte[] passHash = sha256.digest(passBytes);
	    return new String(passHash);
	}

}
