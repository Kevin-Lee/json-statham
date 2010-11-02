/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static com.lckymn.kevin.common.util.Conditions.*;
import static com.lckymn.kevin.common.util.Objects.*;

import java.util.Set;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingSet
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueSet")
	private final Set<String> set;

	public JsonObjectContainingSet(String name, Set<String> set)
	{
		this.name = name;
		this.set = set;
	}

	@Override
	public int hashCode()
	{
		return hash(name, set);
	}

	@Override
	public boolean equals(Object jsonObjectContainingSet)
	{
		if (areIdentical(this, jsonObjectContainingSet))
		{
			return true;
		}
		final JsonObjectContainingSet that = castIfInstanceOf(JsonObjectContainingSet.class, jsonObjectContainingSet);
		/* @formatter:off */
		return isNotNull(that) && 
				and(equal(this.name, that.name), 
					equal(this.set, that.set));
		/* @formatter:on */
	}

	@Override
	public String toString()
	{
		return toStringBuilder(this).add("name", name)
				.add("valueSet", set)
				.toString();
	}
}
