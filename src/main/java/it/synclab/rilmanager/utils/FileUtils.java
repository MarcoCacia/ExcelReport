package it.synclab.rilmanager.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.channels.FileChannel;

public class FileUtils {
	
	private static final int BUFFER_SIZE_1K = 1024;
	private static final int BUFFER_SIZE_8K = 1024 * 8;
	
	private FileUtils() {
	}
	
	public static StringBuilder readFile(File file) throws IOException {
		
		StringBuilder fileContent = new StringBuilder();
		BufferedReader reader = null;
		
		try {
			
			reader = new BufferedReader(new FileReader(file));
			char[] buf = new char[BUFFER_SIZE_1K];
			int numRead = 0;
			while ((numRead = reader.read(buf)) != -1) {
				String readData = String.valueOf(buf, 0, numRead);
				fileContent.append(readData);
				buf = new char[BUFFER_SIZE_1K];
			}
			
		} finally {
			
			if (reader != null)
				reader.close();
		}
		
		return fileContent;
	}
	
	public static void copyFile(File in, File out) throws IOException {
		
		FileChannel inChannel = new FileInputStream(in).getChannel();
		FileChannel outChannel = new FileOutputStream(out).getChannel();
		
		try {
			
			inChannel.transferTo(0, inChannel.size(), outChannel);
			
		} finally {
			if (inChannel != null)
				inChannel.close();
			if (outChannel != null)
				outChannel.close();
		}
		
	}
	
	public static class FindDirs implements FileFilter {
		
		public boolean accept(File pathname) {
			if (pathname.isDirectory())
				return true;
			return false;
		}
	}
	
	public static int inputStreamToOutputStream(InputStream in, OutputStream out) throws IOException {
		
		byte[] buffer = new byte[BUFFER_SIZE_8K];
		int bytesTotal = -1;
		int bytesRead = -1;
		
		while ((bytesRead = in.read(buffer)) > -1) {
			out.write(buffer, 0, bytesRead);
			bytesTotal += bytesRead;
		}
		
		return bytesTotal;
	}
	
	/**
	 * To convert the InputStream to String we use the BufferedReader.readLine()
	 * method. We iterate until the BufferedReader return null which means
	 * there's no more data to read. Each line will appended to a StringBuilder
	 * and returned as String.
	 */
	public static String convertStreamToString(InputStream is) throws IOException {
		if (is != null) {
			
			InputStreamReader in = new InputStreamReader(is);
			
			StringBuilder sb = null;
			
			try {
				sb = convertReaderToString(in);
			} finally {
				is.close();
			}
			return sb.toString();
		} else {
			return "";
		}
	}
	
	public static StringBuilder convertReaderToString(Reader in) throws IOException {
		
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(in);
		String line;
		while ((line = reader.readLine()) != null)
			sb.append(line);
			
		return sb;
		
	}
	
	public static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return path.delete();
	}
	
}
