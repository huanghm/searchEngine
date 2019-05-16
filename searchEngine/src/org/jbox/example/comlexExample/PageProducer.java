package org.jbox.example.comlexExample;

import org.jbox.dao.Page;
import org.jbox.webSpider.WebSpider;



public class PageProducer implements Runnable {
	private PagePool pool;
	private WebSpider spider;

	public PageProducer(PagePool pool, WebSpider spider) {
		this.pool = pool;
		this.spider = spider;
	}

	private void fetchPage() {
		while (spider.hashNext()) {
			try {
				Page p = spider.next();
				pool.putUncuttedPage(p);
			} catch (RuntimeException re) {
				System.err.println(re.getStackTrace());
				continue;
			}
		}
	}

	public void run() {
		long start = System.currentTimeMillis();
		fetchPage();
		long end = System.currentTimeMillis();
		System.err.println(start-end);
	}
}
