package com.persistence;

import java.util.Collection;

import com.model.Infographic;

/**
 * Defines the operations that can be done with {@link Infographic}s in the
 * persistence level
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 23/07/2012
 * @version 1.5
 */
public interface InfographicDataService {

	/**
	 * Retrieves a random header of a specific {@link com.model.Category
	 * Category}
	 * 
	 * @param category
	 *            The {@link com.model.Category Category} value of the desired
	 *            header
	 * @return A random header, <code>null</code> if there are not any header of
	 *         the desired {@link com.model.Category Category}
	 */
	public Infographic getRandomHeader(String category);

	/**
	 * Retrieves a random blast of a specific {@link com.model.Category
	 * Category}
	 * 
	 * @param category
	 *            The {@link com.model.Category Category} value of the desired
	 *            blast
	 * @return A random blast, <code>null</code> if there are not any blast of
	 *         the desired category
	 */
	public Infographic getRandomBlast(String category);

	/**
	 * Retrieves a random ending of a specific {@link com.model.Category
	 * Category}
	 * 
	 * @param category
	 *            The {@link com.model.Category Category} value of the desired
	 *            ending
	 * @return A random ending, <code>null</code> if there are not any ending of
	 *         the desired category
	 */
	public Infographic getRandomEnding(String category);

	/**
	 * Adds a header to the database
	 * 
	 * @param infographic
	 *            The header to be added
	 * @return The identifier of the header
	 */
	public String addHeader(Infographic infographic);

	/**
	 * Adds a blast to the database
	 * 
	 * @param infographic
	 *            The blast to be added
	 * @return The identifier of the blast
	 */
	public String addBlast(Infographic infographic);

	/**
	 * Adds an ending to the database
	 * 
	 * @param infographic
	 *            The ending to be added
	 * @return The identifier of the ending
	 */
	public String addEnding(Infographic infographic);

	/**
	 * Gets all the headers from the database
	 * 
	 * @return A {@link Collection} of {@link Infographic}s containing all the
	 *         headers
	 */
	public Collection<Infographic> getHeaders();

	/**
	 * Gets all the blasts from the database
	 * 
	 * @return A {@link Collection} of {@link Infographic}s containing all the
	 *         blasts
	 */
	public Collection<Infographic> getBlasts();

	/**
	 * Gets all the endings from the database
	 * 
	 * @return A {@link Collection} of {@link Infographic}s containing all the
	 *         endings
	 */
	public Collection<Infographic> getEndings();

	/**
	 * Deletes a header from the database
	 * 
	 * @param id
	 *            The identifier of the {@link Infographic} to be deleted
	 */
	public void deleteHeader(String id);

	/**
	 * Deletes a blast from the database
	 * 
	 * @param id
	 *            The identifier of the {@link Infographic} to be deleted
	 */
	public void deleteBlast(String id);

	/**
	 * Deletes an ending from the database
	 * 
	 * @param id
	 *            The identifier of the {@link Infographic} to be deleted
	 */
	public void deleteEnding(String id);

	/**
	 * Deletes all the {@link Infographic}s from the database
	 */
	public void deleteAll();

}
