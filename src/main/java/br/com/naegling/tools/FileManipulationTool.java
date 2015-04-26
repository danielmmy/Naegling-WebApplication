package br.com.naegling.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

public abstract class FileManipulationTool {
	/**
	 * Compute file MD5 hash
	 * @param filePath - path to the file
	 * @return String with the MD5 hash in 32 hexadecimal characters 
	 * @throws NoSuchAlgorithmException
	 * @throws FileNotFoundException
	 */
	public static String getMD5(String filePath) throws NoSuchAlgorithmException, FileNotFoundException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
		File f = new File(filePath);
		InputStream fis = new FileInputStream(f);				
		byte[] buffer = new byte[8192];
		int read = 0;
		try {
			while( (read = fis.read(buffer)) > 0) {
				digest.update(buffer, 0, read);
			}		
			byte[] md5sum = digest.digest();
			BigInteger bigInt = new BigInteger(1, md5sum);
			String output = bigInt.toString(16);
			return output;
		}
		catch(IOException e) {
			throw new RuntimeException("Unable to process file for MD5", e);
		}
		finally {
			try {
				fis.close();
			}
			catch(IOException e) {
				throw new RuntimeException("Unable to close input stream for MD5 calculation", e);
			}
		}		
	}
	
	public static String genMac(){
		String mac="52:54:00:";
		mac+=UUID.randomUUID().toString().substring(0, 2)+":";
		mac+=UUID.randomUUID().toString().substring(0, 2)+":";
		mac+=UUID.randomUUID().toString().substring(0, 2);
		return mac;
	}
	
	public static String genUuid(){
		return UUID.randomUUID().toString();
	}

}
