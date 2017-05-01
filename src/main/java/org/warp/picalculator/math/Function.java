package org.warp.picalculator.math;

import java.util.List;

import org.warp.picalculator.Error;

public interface Function {

	/**
	 * Returns this function and its children in a string form.
	 * 
	 * @return This function and its children in a string form.
	 */
	@Override
	public String toString();

	@Override
	public boolean equals(Object o);

	/**
	 * Deep clone this function.
	 * 
	 * @return A clone of this function.
	 */
	public Function clone();

	/**
	 * Generic method to change a parameter in a known position.
	 * 
	 * @param index
	 *            parameter index.
	 * @param var
	 *            parameter.
	 * @return A new instance of this function.
	 */
	public Function setParameter(int index, Function var) throws IndexOutOfBoundsException;

	/**
	 * Generic method to retrieve a parameter in a known position.
	 * 
	 * @param index
	 *            parameter index.
	 * @return The requested parameter.
	 */
	public Function getParameter(int index) throws IndexOutOfBoundsException;

	/**
	 * Retrieve the current Math Context used by this function
	 * 
	 * @return Calculator mathContext
	 */
	public MathContext getMathContext();

	/**
	 * Simplify the current function or it's children
	 */
	public List<Function> simplify() throws Error;

	/**
	 * The current simplification status of this function and it's childrens
	 * 
	 * @return boolean
	 */
	public boolean isSimplified();
}