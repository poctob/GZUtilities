package com.gzlabs.utils.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gzlabs.utils.CryptoUtils;

public class TestCryptoUtils {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHashPasswordSHA512() {
		String hash=CryptoUtils.hashPasswordSHA512("test123", "mortons");
		assertEquals("Resulting hash must be 128 characters long", 128, hash.length());
		
		hash=CryptoUtils.hashPasswordSHA512(null, "mortons");
		assertEquals("Resulting hash must be 128 characters long", 128, hash.length());
		
		hash=CryptoUtils.hashPasswordSHA512("test123", null);
		assertEquals("Resulting hash must be 128 characters long", 128, hash.length());
		
		hash=CryptoUtils.hashPasswordSHA512(null, null);
		assertEquals("Resulting hash must be 128 characters long", 128, hash.length());
	}

	@Test
	public void testGenerateRandomSalt() {
		String salt=CryptoUtils.generateRandomSalt(120, 32);
		assertEquals("Salt mush be 32 bit long", 24, salt.length());
		salt=CryptoUtils.generateRandomSalt(0, 32);
		assertNull("Salt should be null if any of the parameters are zero or less", salt);
		salt=CryptoUtils.generateRandomSalt(-1, 32);
		assertNull("Salt should be null if any of the parameters are zero or less", salt);
		salt=CryptoUtils.generateRandomSalt(120, 0);
		assertNull("Salt should be null if any of the parameters are zero or less", salt);
		salt=CryptoUtils.generateRandomSalt(120, -1);
		assertNull("Salt should be null if any of the parameters are zero or less", salt);
	}

	@Test
	public void testCheckPassSHA512() {
		String salt=CryptoUtils.generateRandomSalt(120, 32);
		String hash=CryptoUtils.hashPasswordSHA512("test123", salt);
		
		assertTrue("Hashes should match", CryptoUtils.checkPassSHA512("test123", salt, hash));
		
		assertFalse("Hashes should not match when incorrect password is used", 
				CryptoUtils.checkPassSHA512("badpassword", salt, hash));
		
		assertFalse("Hashes should not match when incorrect salt is used", 
				CryptoUtils.checkPassSHA512("test123", "mortons", hash));
	}

}
