/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static com.lckymn.kevin.common.util.Conditions.*;
import static com.lckymn.kevin.common.util.Objects.*;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-06)
 */
@JsonObject
public class SubClassWithNoJsonObjectSuperClass extends SuperClassWithoutJsonObject
{
	@JsonField(name = "email")
	private String email;

	public SubClassWithNoJsonObjectSuperClass(String name, int number, String email)
	{
		super(name, number);
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
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
		return hash(hash(hash(getName()), getNumber()), email);
	}

	@Override
	public boolean equals(Object subClassWithNoJsonObjectSuperClass)
	{
		if (areIdentical(this, subClassWithNoJsonObjectSuperClass))
		{
			return true;
		}
		final SubClassWithNoJsonObjectSuperClass that =
			castIfInstanceOf(SubClassWithNoJsonObjectSuperClass.class, subClassWithNoJsonObjectSuperClass);
		/* @formatter:off */
		return isNotNull(that) && 
				and(super.equals(subClassWithNoJsonObjectSuperClass), 
					equal(this.email, that.getEmail()));
		/* @formatter:on */
	}
}
