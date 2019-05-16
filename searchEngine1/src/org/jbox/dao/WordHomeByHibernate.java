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
 * Implementation of {@link WordHome} with hibernate.
 * 
 * <p>
 * Because there are a large number of data to be written to database when
 * crawling Internet, it should be noticed that hibernate may not be the best
 * choice for writing data base. If needed better performance, stored procedure
 * may be a better choice.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see Word
 * 
 */
public class WordHomeByHibernate implements WordHome {
	private static Logger logger = Logger.getLogger(WordHomeByHibernate.class);

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

	public Word findByWordStr(String wordStr) {
		String hql = "from Word as w where w.wordStr = ?";
		Query q = session.createQuery(hql);
		q.setString(0, wordStr);
		Word w = (Word) q.uniqueResult();
		return w;
	}

	public void saveWord(Word w) {
		Transaction tx = session.beginTransaction();
		Word word = this.findByWordStr(w.getWordStr());
		if (word == null) {
			session.save(w);
		} else {
			word.setIndex(word.getIndex() + ";" + w.getIndex());
			session.update(word);
		}
		tx.commit();
	}

	public void saveWords(Word[] words) {
		Transaction tx = session.beginTransaction();
		for (int i = 1; i <= words.length; i++) {
			Word w = words[i - 1];
			Word word = this.findByWordStr(w.getWordStr());
			if (word == null) {
				session.save(w);
			} else {
				word.setIndex(word.getIndex() + ";" + w.getIndex());
				session.update(word);
			}
			if (i % 10 == 0) {
				session.flush();
				session.clear();
			}
		}
		tx.commit();
	}

	public void deleteWord(Word w) {
		Criteria crt = session.createCriteria(Word.class).add(
				Expression.eq("wordStr", w.getWordStr()));
		Iterator<?> it = crt.list().iterator();
		Transaction tx = session.beginTransaction();
		while (it.hasNext()) {
			w = (Word) it.next();
			session.delete(w);
		}
		tx.commit();
	}
}
