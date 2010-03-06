/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
@JsonObject
public abstract class SuperClassWithValueAccessorWithAbstractMethod
{
	@ValueAccessor(name = "name")
	@JsonField(name = "name")
	private String name;

	@ValueAccessor(name = "number")
	@JsonField(name = "number")
	private int number;

	public SuperClassWithValueAccessorWithAbstractMethod(String name, int number)
	{
		this.name = name;
		this.number = number;
	}

	/**
	 * @return the name
	 */
	public abstract String name();

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the number
	 */
	public abstract String number();

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number)
	{
		this.number = number;
	}
}
