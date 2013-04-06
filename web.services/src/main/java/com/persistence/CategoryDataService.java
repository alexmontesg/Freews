package com.persistence;

import java.util.Collection;

import com.model.Category;

/**
 * Defines the operations that can be done with {@link Category categories} in
 * the persistence level
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.4
 */
public interface CategoryDataService {

	/**
	 * Retrieves all the {@link Category categories} from the database
	 * 
	 * @return A collection containing all the {@link Category categories}
	 */
	public Collection<Category> getCategories();

	/**
	 * Adds a {@link Category} to the database
	 * 
	 * @param category
	 *            The {@link Category} to be added
	 */
	public void addCategory(Category category);

	/**
	 * Deletes a {@link Category} from the database
	 * 
	 * @param category
	 *            The value of the {@link Category} to be deleted
	 */
	public void deleteCategory(String category);

	/**
	 * Deletes all the {@link Category categories} from the database
	 */
	public void deleteAll();
}
