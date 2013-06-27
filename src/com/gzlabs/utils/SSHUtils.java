package com.gzlabs.utils;

import com.jcraft.jsch.*;

import java.io.*;

/**
 * Performs file transfer over ssh.
 * 
 * @author apavlune
 * 
 */
public class SSHUtils {	

	/**
	 * Initiates host connection.
	 * @return true if connection is success, false otherwise
	 */
	private static Session connect(String username, String hostname, int port, String password) {

		Session session=null;
		try {
			JSch.setConfig("StrictHostKeyChecking", "no");
			session = new JSch().getSession(username, hostname, port);			
			session.setPassword(password);
			session.connect();
		} catch (JSchException e) {
			e.printStackTrace();
		}
		return session;
	
	}

	/**
	 * Sends file to the server.
	 * @param source Source file name.
	 * @param destination Destination file name.
	 * @return true is successful, false otherwise
	 */
	public static boolean sendFile(String source, 
			String destination, 
			String username, 
			String hostname, 
			int port, 
			String password) {
		
		if(source !=null && (new File(source).isFile()) && destination!=null &&
				username != null && hostname!=null && port>0 && password!=null)
		{
			Session session=connect(username, hostname, port, password);
			if (session!=null) {
				FileInputStream fis=null;
				try {
					boolean ptimestamp = true;
					String command = "scp " + (ptimestamp ? "-p" : "") + " -t "
							+ destination;
					Channel channel = session.openChannel("exec");
					((ChannelExec) channel).setCommand(command);
	
					// get I/O streams for remote scp
					OutputStream out = channel.getOutputStream();
					InputStream in = channel.getInputStream();
	
					channel.connect();
					if (checkAck(in) != 0) {
						return false;
					}
	
					File _lfile = new File(source);
	
					out.write(getFileModCommandString(_lfile).getBytes());
					out.flush();
					if (checkAck(in) != 0) {
						return false;
					}
	
					out.write(getFileSendCommandString(_lfile, source).getBytes());
					out.flush();
					if (checkAck(in) != 0) {
						return false;
					}
	
					fis = new FileInputStream(source);
					byte[] buf = new byte[1024];
					while (true) {
						int len = fis.read(buf, 0, buf.length);
						if (len <= 0)
							break;
						out.write(buf, 0, len); // out.flush();
					}
					fis.close();
					fis = null;
					// send '\0'
					buf[0] = 0;
					out.write(buf, 0, 1);
					out.flush();
					if (checkAck(in) != 0) {
						return false;
					}
					out.close();
	
					channel.disconnect();
					session.disconnect();
					System.out.println("File Sent!");
					return true;
	
				} catch (Exception e) {
					e.printStackTrace();
				}
				finally
				{
					if(fis!=null)
					{
						try {
							fis.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Generates file modification date command.
	 * @param file File to get the date from
	 * @return command string
	 */
	private static String getFileModCommandString(File file) {
		String retval = "T " + (file.lastModified() / 1000) + " 0";
		retval += (" " + (file.lastModified() / 1000) + " 0\n");
		return retval;
	}

	/**
	 * Generates file send command
	 * @param file File to send
	 * @param filename Name of the file
	 * @return command string
	 */
	private static String getFileSendCommandString(File file, String filename) {
		long filesize = file.length();
		String retval = "C0644 " + filesize + " ";
		if (filename.lastIndexOf('/') > 0) {
			retval += filename.substring(filename.lastIndexOf('/') + 1);
		} else {
			retval += filename;
		}
		retval += "\n";
		return retval;
	}

	/**
	 * Checks for ack
	 * @param in stream that is being received
	 * @return result code
	 * @throws IOException
	 */
	private static int checkAck(InputStream in) throws IOException {
		int b = in.read();
		// b may be 0 for success,
		// 1 for error,
		// 2 for fatal error,
		// -1
		if (b == 0)
			return b;
		if (b == -1)
			return b;

		if (b == 1 || b == 2) {
			StringBuffer sb = new StringBuffer();
			int c;
			do {
				c = in.read();
				sb.append((char) c);
			} while (c != '\n');
			if (b == 1) { // error
				System.out.print(sb.toString());
			}
			if (b == 2) { // fatal error
				System.out.print(sb.toString());
			}
		}
		return b;
	}

}
