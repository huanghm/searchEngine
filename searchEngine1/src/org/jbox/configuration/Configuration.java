package org.jbox.configuration;

import java.io.File;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jbox.dao.PageHome;
import org.jbox.dao.WordHome;
import org.jbox.indexer.IndexWriter;
import org.jbox.searcher.Searcher;
import org.jbox.textCutter.Cutter;
import org.jbox.textCutter.CutterBox;
import org.jbox.webSpider.WebSpider;

/**
 * The <code>Configuration</code> class is provided to specify properties and
 * mapping documents to be used when creating {@link WebSpider},
 * {@link CutterBox}, {@link IndexWriter},and {@link Searcher}.
 * <p>
 * Note: build methods in this class are not designed in single pattern.
 * <p>
 * A new <tt>Configuration</tt> will use the properties specified in
 * <tt>jbox.cfg.xml</tt> by default.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see WebSpider
 * @see Cutter
 * @see CutterBox
 * @see IndexWriter
 * @see Searcher
 */
public class Configuration {
	private static Logger logger = Logger.getLogger(Configuration.class);

	private static Document d;
	
	private static String workDir;

	private Configuration() {
	}

	/**
	 * Use default path: <tt>jbox.cfg.xml</tt> to load configuration file.
	 */
	public static Configuration config() {
		return config("jbox.cfg.xml");
	}

	/**
	 * Load configuration file with the specified path.
	 * 
	 * @param cfgPath
	 *            cfgPath of configuration file.
	 */
	public static Configuration config(String cfgPath) {
		if (d == null) {
			SAXReader sr = new SAXReader();
			try {
				d = sr.read(cfgPath);
			} catch (DocumentException e) {
				RuntimeException re = new ConfigurationException(
						"error when loading config file.", e);
				if (logger.isEnabledFor(Level.ERROR))
					logger.error(re.getMessage(), re);
				throw re;
			}
			File configFile = new File(cfgPath);
			workDir = configFile.getAbsoluteFile().getParent();	
		}
		return new Configuration();
	}

	/**
	 * Create a new {@link WebSpider} object using the properties and mappings
	 * in this configuration.
	 * 
	 * <p>
	 * <br>
	 * The configuration of WebSpider may be like below:
	 * <p>
	 * <br>
	 * &#60spider class = "org.jbox.spider.htmlSpider.SimpleSpider"&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&#60maxPageNume&#62 1 &#60/maxPageNum&#62 
	 * <br>
	 * &nbsp;&nbsp;&nbsp;&#60startUrls&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#60property name =
	 * "URL"&#62http://localhost&#60/property&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&#60/startUrls&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&#60crawlRules&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&#60property name =
	 * "Rule"&#62http://.*&#60/property&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&#60/crawlRules&#62 <br>
	 * &#60/spider&#62
	 * <p>
	 * Attribute "class" specify which concrete {@link WebSpider} to be created.
	 * <p>
	 * Element &#60maxPageNum&#62 represents the number of pages to be visited.
	 * <p>
	 * Element &#60startUrls&#62 represents the URL which a WebSpider start
	 * with.
	 * <p>
	 * Element &#60crawlRules&#62 represents the rule used when WebSpider
	 * visiting internet. A rule is written in REGEXP(regular expression).For
	 * example: <br>
	 * "http://.*(\.html)$" <br>
	 * means WebSpider will ignore all URLs unless the URL end with".html".
	 * 
	 * @return Concrete WebSpider object specified by &#60Spider&#62 tag in
	 *         configuration file.
	 * @exception ConfigurationException -
	 *                if fail to create a {@link WebSpider}.
	 */
	public WebSpider buildWebSpider() {
		Element cfg = d.getRootElement().element("webSpider");
		StringBuffer temp = new StringBuffer();

		Iterator<?> urlIt = cfg.element("startUrls").elementIterator();
		while (urlIt.hasNext()) {
			temp.append(((Element) urlIt.next()).getTextTrim() + ";;;");
		}
		String[] startUrls = temp.toString().split(";;;");

		temp = new StringBuffer();
		Iterator<?> ruleIt = cfg.element("crawlRules").elementIterator();
		while (ruleIt.hasNext()) {
			temp.append(((Element) ruleIt.next()).getTextTrim() + ";;;");
		}
		String[] rules = temp.toString().split(";;;");

		String maxNumStr = cfg.elementTextTrim("maxPageNum");
		int maxPageNum = Integer.parseInt(maxNumStr);

		String spiderClassName = cfg.attributeValue("class");
		WebSpider webSpider = null;
		Class<?> spiderClass = null;
		try {
			spiderClass = Class.forName(spiderClassName);
			webSpider = (WebSpider) spiderClass.newInstance();
		} catch (Exception e) {
			RuntimeException re = new ConfigurationException(
					"error when building spider.", e);
			if (logger.isEnabledFor(Level.ERROR))
				logger.error(re.getMessage(), re);
			throw re;
		}
		webSpider.setMaxPageNum(maxPageNum);
		webSpider.setRules(rules);
		webSpider.setStartUrls(startUrls);
		return webSpider;
	}

	/**
	 * Create a new {@link CutterBox} object using the properties and mappings
	 * in this configuration.
	 * 
	 * <p>
	 * <br>
	 * The configuration of CutterBox may be like below:
	 * <p>
	 * <br>
	 * &#60cutterBox&#62 <br>
	 * &nbsp;&nbsp;&#60cutter language="EN"
	 * class="org.jbox.textCutter.EN.SimpleENCutter"&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&#60property name = "UnicodeScope" start="0x0030"
	 * end="0x0039"/&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&#60property name = "UnicodeScope" start="0x0041"
	 * end="0x005a"/&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&#60property name = "UnicodeScope" start="0x0061"
	 * end="0x007a"/&#62 <br>
	 * &nbsp;&nbsp;&#60/cutter&#62 <br>
	 * &nbsp;&nbsp;&#60cutter language="CJK"
	 * class="org.jbox.textCutter.CJK.SimpleCJKCutter"&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&#60property name =
	 * "UnicodeBlock"&#62CJK_UNIFIED_IDEOGRAPHS&#60/property&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&#60property name =
	 * "UnicodeBlock"&#62KATAKANA&#60/property&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&nbsp;&#60property name =
	 * "UnicodeBlock"&#62HANUNOO&#60/property&#62 <br>
	 * &nbsp;&nbsp;&#60/cutter&#62 <br>
	 * &#60/cutterBox&#62
	 * <p>
	 * Element &#60cutter&#62 specify a concrete {@link Cutter} to put in
	 * CutterBox.
	 * <p>
	 * Property "UnicodeBlock" specify a unicode scope of {@link Cutter} by
	 * <code>java.lang.Character.UnicodeBlock</code>.
	 * <p>
	 * Property "UnicodeScope" specify a unicode scope of {@link Cutter} by a
	 * int array with two element: start unicode, and end unicode.
	 * <p>
	 * 
	 * @return {@link CutterBox} object represented by &#60CutterBox&#62 tag in
	 *         configuration file.
	 * @exception ConfigurationException -
	 *                if fail to create a {@link CutterBox}
	 */
	public CutterBox buildCutterBox() {
		CutterBox cutterBox = new CutterBox();
		Element cfg = d.getRootElement().element("cutterBox");
		Iterator<?> cutterIt = cfg.elementIterator("cutter");
		while (cutterIt.hasNext()) {
			Element cutterCfg = (Element) cutterIt.next();

			ArrayList<int[]> scopeCollection = new ArrayList<int[]>();
			ArrayList<UnicodeBlock> blockCollection = new ArrayList<UnicodeBlock>();
			Iterator<?> propertyIt = cutterCfg.elementIterator("property");
			while (propertyIt.hasNext()) {
				Element property = (Element) propertyIt.next();
				String type = property.attributeValue("name");
				if (type.equals("UnicodeScope")) {
					int start = Integer.parseInt(property.attributeValue(
							"start").substring(2), 16);
					int end = Integer.parseInt(property.attributeValue("end")
							.substring(2), 16);
					int[] scope = new int[] { start, end };
					scopeCollection.add(scope);
				} else if (type.equals("UnicodeBlock")) {
					UnicodeBlock block = UnicodeBlock.forName(property
							.getTextTrim());
					blockCollection.add(block);
				}
			}
			int[][] scopes = new int[scopeCollection.size()][2];
			UnicodeBlock[] blocks = new UnicodeBlock[blockCollection.size()];
			scopeCollection.toArray(scopes);
			blockCollection.toArray(blocks);
			String cutterClassName = cutterCfg.attributeValue("class");
			Class<?> cutterClass = null;
			Cutter cutter = null;
			try {
				cutterClass = Class.forName(cutterClassName);
				cutter = (Cutter) cutterClass.newInstance();
			} catch (Exception e) {
				if (logger.isEnabledFor(Level.ERROR))
					logger.error(e.getMessage(), e);
				throw new ConfigurationException(
						"error when building cutter-box.", e);
			}
			cutter.setUnicode(blocks, scopes);
			cutterBox.addCutter(cutter);
		}
		return cutterBox;
	}

	/**
	 * Create a new {@link IndexWriter} object using the properties and mappings
	 * in this configuration.
	 * 
	 * <p>
	 * <br>
	 * The configuration of {@link IndexWriter} may be like below:
	 * <p>
	 * <br>
	 * &#60indexWriter class = "org.jbox.index.IndexWriterWithTFLOC"&#62 <br>
	 * &nbsp;&nbsp;&#60property name = "PageHome"&#62org.jbox.dao.
	 * PageHomeByProcedure&#60/property&#62 <br>
	 * &nbsp;&nbsp;&#60property name = "WordHome"&#62org.jbox.dao.
	 * WordHomeByProcedure&#60/property&#62 <br>
	 * &#60/indexWriter&#62
	 * <p>
	 * Property "PageHome" specify the DAO {@link PageHome} of
	 * {@link org.jbox.dao.Page Page}
	 * <p>
	 * Property "wordHome" specify the DAO {@link WordHome} of
	 * {@link org.jbox.dao.Word Word}
	 * <p>
	 * 
	 * @return Concrete {@link IndexWriter} object represented by
	 *         &#60IndexWriter&#62 tag in configuration file.
	 * @exception ConfigurationException -
	 *                if fail to create a {@link IndexWriter}.
	 */
	public IndexWriter buildIndexWriter() {
		Element iwCfg = d.getRootElement().element("indexWriter");
		IndexWriter indexWriter = null;
		String indexWriterClassName = iwCfg.attributeValue("class");
		Class<?> indexWriterClass = null;
		try {
			indexWriterClass = Class.forName(indexWriterClassName);
			indexWriter = (IndexWriter) indexWriterClass.newInstance();
		} catch (Exception e) {
			if (logger.isEnabledFor(Level.ERROR))
				logger.error(e.getMessage(), e);
			throw new ConfigurationException(
					"error when building index writer.", e);
		}
		Iterator<?> homeIt = iwCfg.elementIterator("property");
		while (homeIt.hasNext()) {
			Element homeElement = (Element) homeIt.next();
			String name = homeElement.attributeValue("name");
			String homeClassName = homeElement.getTextTrim();
			Object home = null;
			try {
				Class<?> homeClass = Class.forName(homeClassName);
				home = homeClass.newInstance();
			} catch (Exception e) {
				RuntimeException re = new ConfigurationException(
						"error when building index writer.", e);
				if (logger.isEnabledFor(Level.ERROR))
					logger.error(re.getMessage(), re);
				throw re;
			}
			if (name.equals("PageHome")) {
				indexWriter.setPageHome((PageHome) home);
			} else if (name.equals("WordHome")) {
				indexWriter.setWordHome((WordHome) home);
			}
		}
		return indexWriter;
	}

	/**
	 * Create a new {@link Searcher} object using the properties and mappings in
	 * this configuration.
	 * 
	 * <p>
	 * <br>
	 * The configuration of {@link Searcher} may be like below:
	 * <p>
	 * <br>
	 * &#60searcher class =
	 * "org.jbox.searcher.simpleSearcher.SimpleSearcher"&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&#60property name =
	 * "PageHome"&#62org.jbox.dao.PageHomeByProcedure&#60/property&#62 <br>
	 * &nbsp;&nbsp;&nbsp;&#60property name =
	 * "WordHome"&#62org.jbox.dao.WordHomeByProcedure&#60/property&#62 <br>
	 * &#60/searcher&#62
	 * <p>
	 * Property "PageHome" specify the DAO {@link PageHome} of
	 * {@link org.jbox.dao.Page Page}
	 * <p>
	 * Property "wordHome" specify the DAO {@link WordHome} of
	 * {@link org.jbox.dao.Word Word}
	 * <p>
	 * 
	 * @return Concrete {@link Searcher} object represented by &#60Searcher&#62
	 *         tag in configuration file.
	 * @exception ConfigurationException -
	 *                if fail to create a {@link Searcher}.
	 */
	public Searcher buildSearcher() {
		Element iwCfg = d.getRootElement().element("searcher");
		Searcher searcher = null;
		String searcherClassName = iwCfg.attributeValue("class");
		Class<?> searcherClass = null;
		try {
			searcherClass = Class.forName(searcherClassName);
			searcher = (Searcher) searcherClass.newInstance();
		} catch (Exception e) {
			if (logger.isEnabledFor(Level.ERROR))
				logger.error(e.getMessage(), e);
			throw new ConfigurationException("error when building searcher.", e);
		}
		Iterator<?> homeIt = iwCfg.elementIterator("property");
		while (homeIt.hasNext()) {
			Element homeElement = (Element) homeIt.next();
			String name = homeElement.attributeValue("name");
			String homeClassName = homeElement.getTextTrim();
			Object home = null;
			try {
				Class<?> homeClass = Class.forName(homeClassName);
				home = homeClass.newInstance();
			} catch (Exception e) {
				RuntimeException re = new ConfigurationException(
						"error when building searcher.", e);
				if (logger.isEnabledFor(Level.ERROR))
					logger.error(re.getMessage(), re);
				throw re;
			}
			if (name.equals("PageHome")) {
				searcher.setPageHome((PageHome) home);
			} else if (name.equals("WordHome")) {
				searcher.setWordHome((WordHome) home);
			}
		}
		return searcher;
	}

	public static String getWorkDir() {
		return workDir;
	}
}
