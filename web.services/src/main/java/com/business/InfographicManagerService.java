package com.business;

import java.io.File;

/**
 * Defines the operations that can be done with {@link com.model.Infographic
 * Infographic}s
 * 
 * @author <a href="http://alejandro-montes.appspot.com">Alejandro Montes
 *         García</a>
 * @since 10/08/2012
 * @version 1.2
 */
public interface InfographicManagerService {

	/**
	 * Uploads a blast
	 * 
	 * @param category
	 *            The {@link com.model.Category Category} value of the blast to
	 *            be saved
	 * @param infographic
	 *            The {@link File} to be uploaded
	 */
	public void uploadBlast(String category, File infographic);

	/**
	 * Uploads a header
	 * 
	 * @param category
	 *            The {@link com.model.Category Category} value of the header to
	 *            be saved
	 * @param infographic
	 *            The {@link File} to be uploaded
	 */
	public void uploadHeader(String category, File infographic);

	/**
	 * Uploads a ending
	 * 
	 * @param category
	 *            The {@link com.model.Category Category} value of the ending to
	 *            be saved
	 * @param infographic
	 *            The {@link File} to be uploaded
	 */
	public void uploadEnding(String category, File infographic);

}
