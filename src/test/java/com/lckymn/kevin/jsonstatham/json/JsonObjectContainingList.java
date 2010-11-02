/**
 * 
 */
package com.lckymn.kevin.jsonstatham.json;

import static com.lckymn.kevin.common.util.Conditions.*;
import static com.lckymn.kevin.common.util.Objects.*;

import java.util.List;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-03)
 */
@JsonObject
public class JsonObjectContainingList
{
	@JsonField(name = "name")
	private final String name;

	@JsonField(name = "valueList")
	private final List<String> list;

	public JsonObjectContainingList(String name, List<String> list)
	{
		this.name = name;
		this.list = list;
	}

	@Override
	public int hashCode()
	{
		return hash(name, list);
	}

	@Override
	public boolean equals(Object jsonObjectContainingList)
	{
		if (areIdentical(this, jsonObjectContainingList))
		{
			return true;
		}
		final JsonObjectContainingList that =
			castIfInstanceOf(JsonObjectContainingList.class, jsonObjectContainingList);
		/* @formatter:off */
		return isNotNull(that) && 
				and(equal(this.name, that.name), 
					equal(this.list, that.list));
		/* @formatter:on */
	}
}
