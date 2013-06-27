package com.gzlabs.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Crypto utilites for secure password storage.
 * @author apavlune
 *
 */
public class CryptoUtils {

	/**
	 * Hashes the password using SHA512 algorithm, password can be salted if salt is provided.
	 * @param input Plain text password.
	 * @param salt Password salt.
	 * @return Hashed password
	 */
	public static String hashPasswordSHA512(String input, String salt)
	{
		String salted_pass=input+salt;
		StringBuffer hexString = new StringBuffer();		
		MessageDigest md;
		
		try {
			md = MessageDigest.getInstance("SHA-512");
			md.update(salted_pass.getBytes());
			byte byteData[] = md.digest();
			
			 
		    	for (int i=0;i<byteData.length;i++) {
		    		String hex=Integer.toHexString(0xff & byteData[i]);
		   	     	if(hex.length()==1) hexString.append('0');
		   	     	hexString.append(hex);
		    	}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return hexString.toString();
	}
	
	/**
	 * Generates new random string to use for salt.
	 * Use 120 for bits and 32 for radix when in doubt.
	 * @param bits Number of bits to use
	 * @param radix String radix number
	 * @return Random salt.
	 */
	public static String generateRandomSalt(int bits, int radix)
	{
		if(bits>0 && radix>0)
		{
			SecureRandom random = new SecureRandom();
			return new BigInteger(bits, random).toString(radix);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Checks if the password matches stored hash.
	 * @param password Password to check.
	 * @param salt Salt used to hash it.
	 * @param hash Hashed value for comparison.
	 * @return True if passwords match. False otherwise.
	 */
	public static boolean checkPassSHA512(String password, String salt, String hash)
	{
		String hashedpass=hashPasswordSHA512(password, salt);
		return hashedpass.equals(hash);
	}

}
