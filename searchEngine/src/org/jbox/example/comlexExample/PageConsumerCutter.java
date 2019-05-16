package org.jbox.example.comlexExample;


import org.jbox.dao.Page;
import org.jbox.textCutter.CutterBox;


public class PageConsumerCutter implements Runnable {
	private PagePool pool;

	private CutterBox cutterBox;

	public PageConsumerCutter(PagePool pool,CutterBox cutterBox) {
		this.pool = pool;
		this.cutterBox = cutterBox;
	}

	private void cutText() {
		while (true) {
			try {
				Page page = pool.takeUncuttedPage();
				if (page == null || page.getText() == null)
					continue;
				cutterBox.cutPage(page);
				pool.putCuttedPage(page);
			} catch (RuntimeException re) {
				System.err.println(re.getStackTrace());
				continue;
			}
		}
	}

	public void run() {
		cutText();
		System.err.println("cuting page into index complete.");
	}
}
