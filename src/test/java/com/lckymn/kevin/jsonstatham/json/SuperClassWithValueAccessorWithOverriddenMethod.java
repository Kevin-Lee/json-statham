/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static com.lckymn.kevin.common.util.Conditions.*;
import static com.lckymn.kevin.common.util.Objects.*;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
@JsonObject
public abstract class SuperClassWithValueAccessorWithOverriddenMethod
{
	@ValueAccessor(name = "name")
	@JsonField(name = "name")
	private String name;

	@ValueAccessor(name = "number")
	@JsonField(name = "number")
	private int number;

	public SuperClassWithValueAccessorWithOverriddenMethod(String name, int number)
	{
		this.name = name;
		this.number = number;
	}

	/**
	 * @return the name
	 */
	public String name()
	{
		return name;
	}

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
	public String number()
	{
		return String.valueOf(number);
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number)
	{
		this.number = number;
	}

	@Override
	public int hashCode()
	{
		return hash(hash(name), number);
	}

	@Override
	public boolean equals(Object superClassWithValueAccessorWithOverriddenMethod)
	{
		if (areIdentical(this, superClassWithValueAccessorWithOverriddenMethod))
		{
			return true;
		}
		final SuperClassWithValueAccessorWithOverriddenMethod that =
			castIfInstanceOf(SuperClassWithValueAccessorWithOverriddenMethod.class,
					superClassWithValueAccessorWithOverriddenMethod);
		/* @formatter:off */
		return isNotNull(that) && 
				and(equal(this.name, that.name), 
					equal(this.number, that.number));
		/* @formatter:on */
	}

	@Override
	public String toString()
	{
		return toStringBuilder(this).add("name", name)
				.add("number", number)
				.toString();
	}
}
