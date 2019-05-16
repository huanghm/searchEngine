package org.jbox.dao;

/**
 * The root interface of DAO for {@link Page}, defines methods for visiting
 * database.
 * 
 * @author YiBin.H
 * @version 1.0
 * @see Page
 */
public interface PageHome {
	/**
	 * Save a {@link Page} object to data base,and return the page id in data
	 * base.
	 * 
	 * @param p
	 *            a {@link Page} to save.
	 * @return id of {@link Page} object.
	 */
	long savePage(Page p);

	/**
	 * Delete a {@link Page} object from data base.
	 * 
	 * @param p
	 *            {@link Page} object needed to delete.
	 */
	void deletePage(Page p);

	/**
	 * Find a {@link Page} object from data base by the specified id.
	 * 
	 * @param id
	 *            {@link Page} object id, a long value.
	 * @return {@link Page} object
	 */
	Page findById(long id);

	/**
	 * Find {@link Page} objects from data base by IDs.
	 * 
	 * @param ids
	 *            Integer array containing id of {@link Page} object.
	 * @return {@link Page} array.
	 */
	Page[] findByIds(long[] ids);

	/**
	 * Count the number of records in table "page" in data base.
	 * 
	 * @return number of records in table "page" in data base.
	 */
	Page findByUrl(String url);

	long findPageNum();
}
