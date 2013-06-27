package com.gzlabs.utils.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.gzlabs.utils.SSHUtils;

public class TestSSHUtils {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendFile() {
		try {
		String source="testfile.test";
		File file=new File(source);
		file.createNewFile();
		String host;
		int port;
		String username;
		String password;
		
		System.out.print("Enter host name:");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		host = br.readLine();
		
		System.out.print("Enter port number:");
		port = Integer.parseInt(br.readLine());
		
		System.out.print("Enter user name:");
		username = br.readLine();
		
		System.out.print("Enter password:");
		password = br.readLine();
		
		boolean success=SSHUtils.sendFile(source, source, username, host, port, password);
		
		assertTrue("File not sent", success);
		
		success=SSHUtils.sendFile(null, source, username, host, port, password);
		assertFalse("File should not have sent", success);
		
		success=SSHUtils.sendFile(source, null, username, host, port, password);
		assertFalse("File should not have sent", success);
		
		success=SSHUtils.sendFile(source, source, null, host, port, password);
		assertFalse("File should not have sent", success);
		
		success=SSHUtils.sendFile(source, source, username, null, port, password);
		assertFalse("File should not have sent", success);
		
		success=SSHUtils.sendFile(source, source, username, host, -1, password);
		assertFalse("File should not have sent", success);
		
		success=SSHUtils.sendFile(source, source, username, host, port, null);
		assertFalse("File should not have sent", success);
		
		success=SSHUtils.sendFile(source+"wrong", source, username, host, port, password);
		assertFalse("File should not have sent", success);
		
		success=SSHUtils.sendFile(source, source, username+"wrong", host, port, password);
		assertFalse("File should not have sent", success);
		
		success=SSHUtils.sendFile(source, source, username, host+"wrong", port, password);
		assertFalse("File should not have sent", success);
		
		//This one may take a minute or two to time out
		success=SSHUtils.sendFile(source, source, username, host, port+100, password);
		assertFalse("File should not have sent", success);
		
		success=SSHUtils.sendFile(source, source, username, host, port, password+"wrong");
		assertFalse("File should not have sent", success);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
