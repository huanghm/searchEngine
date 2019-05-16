package org.jbox.dao;

import java.util.Iterator;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Expression;

/**
 * Implementation of {@link PageHome} with hibernate.
 * 
 * <p>
 * It should be noticed that because there may be thousands of data written to
 * data base when crawl Internet, it should be noticed that hibernate may not be
 * the best choice for writing data base. If needed better performance, stored
 * procedure may be a better choice.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see Page
 * 
 */
public class PageHomeByHibernate implements PageHome {
	private static Logger logger = Logger.getLogger(PageHomeByHibernate.class);

	private final Session session = getSession();

	private Session getSession() {
		try {
			return (Session) new Configuration().configure()
					.buildSessionFactory().openSession();
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
	}

	public Page findById(long id) {
		Page p = (Page) session.load(Page.class, id);
		return p;
	}

	public Page[] findByIds(long[] ids) {
		Page[] pages = new Page[ids.length];
		for (int i = 0; i < pages.length; i++) {
			pages[i] = this.findById(ids[i]);
		}
		return pages;
	}

	public Page findByUrl(String url) {
		String hql = "From Page as p where p.url = ?";
		Query query = session.createQuery(hql);
		query.setString(0, url);
		Page p = (Page) query.uniqueResult();
		return p;
	}

	public long findPageNum() {
		String hql = "select count(*) from Page";
		Query q = session.createQuery(hql);
		long pageNum = (Long) q.uniqueResult();
		return pageNum;
	}

	public long savePage(Page p) {
		Page pageInDB = this.findByUrl(p.getUrl());
		if (pageInDB != null)
			return -1;
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(p);
		tx.commit();
		return p.getUrlId();
	}

	public void deletePage(Page p) {
		Criteria crt = session.createCriteria(Page.class).add(
				Expression.eq("url", p.getUrl()));
		Iterator<?> it = crt.list().iterator();
		Transaction tx = session.beginTransaction();
		while (it.hasNext()) {
			p = (Page) it.next();
			session.delete(p);
		}
		tx.commit();
	}
}
