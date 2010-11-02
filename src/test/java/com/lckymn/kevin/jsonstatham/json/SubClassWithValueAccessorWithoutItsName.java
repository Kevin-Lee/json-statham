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
public class SubClassWithValueAccessorWithoutItsName extends SuperClassWithValueAccessorWithoutItsName
{
	@ValueAccessor
	@JsonField(name = "email")
	private String email;

	public SubClassWithValueAccessorWithoutItsName(String name, int number, String email)
	{
		super(name, number);
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return "My email address is " + email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	@Override
	public int hashCode()
	{
		return hash(super.hashCode(), email);
	}

	@Override
	public boolean equals(Object subClassWithValueAccessorWithoutItsName)
	{
		if (areIdentical(this, subClassWithValueAccessorWithoutItsName))
		{
			return true;
		}
		final SubClassWithValueAccessorWithoutItsName that =
			castIfInstanceOf(getClass(), subClassWithValueAccessorWithoutItsName);
		/* @formatter:off */
		return isNotNull(that) && 
				and(super.equals(subClassWithValueAccessorWithoutItsName), 
					equal(this.email, that.email));
		/* @formatter:on */
	}

	@Override
	public String toString()
	{
		return toStringBuilder(this).value(super.toString())
				.newLine()
				.add("email", email)
				.toString();
	}
}
