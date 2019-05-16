package org.jbox.searcher.simpleSearcher;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.jbox.dao.Page;
import org.jbox.dao.Word;
import org.jbox.searcher.AbstractSearcher;

/**
 * A concrete Searcher.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see Page
 * @see PageProxy
 * 
 */
public class SimpleSearcher extends AbstractSearcher {
	/**
	 * Query the index with a specified word string.
	 * 
	 * @param word
	 *            a string to queried.
	 * @return HashMap mapping page id to {@link PageProxy}.
	 */
	protected HashMap<Long, PageProxy> query(String word) {
		HashMap<Long, PageProxy> pageProxyMap = new HashMap<Long, PageProxy>();
		Word w = this.wordHome.findByWordStr(word);
		if (w == null)
			return pageProxyMap;
		String[] index = w.getIndex().split(";");
		for (int i = 0; i < index.length; i++) {
			String[] id_tf_loc = index[i].split("-");
			long pageId = Long.parseLong(id_tf_loc[0]);
			double tf = Double.parseDouble(id_tf_loc[1]);
			double tfidf = this.calculateTFIDF(tf, index.length);
			String[] locStr = id_tf_loc[2].split(",");
			HashSet<Integer> locs = new HashSet<Integer>();
			for (int j = 0; j < locStr.length; j++) {
				locs.add(Integer.parseInt(locStr[j].trim()));
			}
			PageProxy pageProxy = new PageProxy();
			pageProxy.setPage(this.pageHome.findById(pageId));
			pageProxy.setLocation(locs);
			pageProxy.setTfidf(tfidf);
			pageProxyMap.put(pageId, pageProxy);
		}
		return pageProxyMap;
	}

	/**
	 * Query index by a string array.
	 * 
	 * @param words
	 *            String array to be queried.
	 * @return Collection containing {@link PageProxy} objects which relate to
	 *         the query words.
	 */
	public Collection<PageProxy> query(String[] words) {
		HashMap<Long, PageProxy> pageProxyMap = new HashMap<Long, PageProxy>();
		for (String word : words) {
			Iterator<Entry<Long, PageProxy>> it = this.query(word).entrySet()
					.iterator();
			while (it.hasNext()) {
				Entry<Long, PageProxy> entry = it.next();
				long id = entry.getKey();
				PageProxy newPageProxy = entry.getValue();
				if (pageProxyMap.containsKey(id)) {
					PageProxy PageProxy = pageProxyMap.get(id);
					PageProxy.setTfidf(PageProxy.getTFIDF()
							+ newPageProxy.getTFIDF());
					PageProxy.addLocation(newPageProxy.getLocation());
				} else {
					pageProxyMap.put(id, newPageProxy);
				}
				it.remove();
			}
		}
		return pageProxyMap.values();
	}

	/**
	 * Search index by a query string, return a {@link Page} array related to
	 * the query string.
	 * 
	 * <p>
	 * It should be noticed that the type of result of this method is not real
	 * {@link Page},but {@link PageProxy}, a proxy of {@link Page}. It change
	 * the behavior of {@link Page#getText()}, returning a introduction instead
	 * of returning the whole text. <br>
	 * If need to get the the prototype of {@link Page}, cast the result type
	 * to {@link PageProxy}, and then calling {@link PageProxy#getPage()} to
	 * get the {@link Page} object.
	 * 
	 * <p>
	 * Note: results of the method are sorted by TFIDF.
	 * 
	 * @param query
	 *            string to be queried.
	 * @return {@link Page} array related to the query string.
	 */
	public Page[] search(String query) {
		Collection<String> queryWords = cutterBox.cutText(query);
		String[] words = new String[queryWords.size()];
		queryWords.toArray(words);
		Collection<PageProxy> collection = this.query(words);
		PageProxy[] pageProxys = new PageProxy[collection.size()];
		collection.toArray(pageProxys);
		this.pageRank(pageProxys, 0, pageProxys.length - 1);
		return pageProxys;
	}

}
