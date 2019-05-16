package org.jbox.textCutter.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * A class representing a dictionary file.
 * 
 * <p>
 * <code>Dict</code> is used in NoiseFilter and SimpleCJKCutter.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see NoiseFilter
 * @see org.jbox.textCutter.CJK.SimpleCJKCutter
 */

public class Dict {
	private HashSet<String> dict = new HashSet<String>(50000);
	private static Logger logger = Logger.getLogger(Dict.class);

	/**
	 * Constructs a new Dict by the specified path.
	 * 
	 * @param path
	 *            the path of dictionary file or directory.
	 * @throws DictInitException
	 *             if failt to load dict file.
	 */
	public Dict(String path) {
		try {
			this.loadDict(path);
		} catch (IOException e) {
			DictInitException die = new DictInitException(e);
			if (logger.isEnabledFor(Level.ERROR))
				logger.error(die.getMessage(), die);
			throw die;
		}
	}

	/**
	 * Load dictionary file.
	 * 
	 * @param path
	 *            the path of dictionary file or directory.
	 * @throws IOException
	 *             if fail to load dict file.
	 */
	private void loadDict(String path) throws IOException {
		File dictFile = new File(path);
		if (!dictFile.exists()) {
			DictInitException die = new DictInitException(
					"dict file not found:abc" + dictFile.getAbsoluteFile().getAbsolutePath());
			if (logger.isEnabledFor(Level.ERROR))
				logger.error(die.getMessage(), die);
			throw die;
		}
		if (dictFile.isFile()) {
			this.loadDictFile(path);
		} else if (dictFile.exists()) {
			File[] subFiles = dictFile.listFiles();
			if (subFiles != null) {
				for (File subFile : subFiles) {
					this.loadDict(subFile.getPath());
				}
			}
		}
	}

	/**
	 * Load dictionary file.
	 * 
	 * @param filePath
	 *            path of dictionary file.
	 * @throws IOException
	 *             if fail to load dict file.
	 */
	private void loadDictFile(String filePath) throws IOException {
		BufferedReader br = null;
		br = new BufferedReader(new InputStreamReader(new FileInputStream(
				filePath)));
		String word = null;
		while ((word = br.readLine()) != null) {
			dict.add(word);
		}
		br.close();

	}

	/**
	 * Checked if there is a string in the dictionary as same as the specified
	 * word.
	 * 
	 * @param word
	 *            a string to check.
	 * @return true if exist a string in the dictionary as same as the specified
	 *         word, or false otherwise.
	 */
	public boolean isExist(String word) {
		return dict.contains(word);
	}
}
