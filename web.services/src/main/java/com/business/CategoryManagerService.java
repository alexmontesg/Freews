package com.business;

import java.util.Collection;

import com.model.Category;

/**
 * Defines the operations that can be done with {@link Category categories}
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.0
 */
public interface CategoryManagerService {
	
	/**
	 * Gets all the categories available
	 * 
	 * @return A {@link Collection} containing all the {@link Category
	 *         categories}
	 */
	public Collection<Category> getCategories();

	/**
	 * Adds a {@link Category}
	 * 
	 * @param category
	 *            The value of the {@link Category} to be added
	 */
	public void addCategory(String category);
}
