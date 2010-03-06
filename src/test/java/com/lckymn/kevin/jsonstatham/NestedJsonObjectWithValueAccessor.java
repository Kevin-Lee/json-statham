/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import com.lckymn.kevin.jsonstatham.annotation.JsonField;
import com.lckymn.kevin.jsonstatham.annotation.JsonObject;
import com.lckymn.kevin.jsonstatham.annotation.ValueAccessor;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.01 (2009-11-28)
 */
@JsonObject
public class NestedJsonObjectWithValueAccessor
{
	@ValueAccessor
	@JsonField(name = "id")
	private Long primaryKey;

	@ValueAccessor
	@JsonField(name = "name")
	private String name;

	@ValueAccessor
	@JsonField(name = "parent")
	private NestedJsonObjectWithValueAccessor parent;

	public NestedJsonObjectWithValueAccessor(Long primaryKey, String name, NestedJsonObjectWithValueAccessor parent)
	{
		this.primaryKey = primaryKey;
		this.name = name;
		this.parent = parent;
	}

	public Long getPrimaryKey()
	{
		return primaryKey;
	}

	public void setPrimaryKey(Long primaryKey)
	{
		this.primaryKey = primaryKey;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public NestedJsonObjectWithValueAccessor getParent()
	{
		return parent;
	}

	public void setParent(NestedJsonObjectWithValueAccessor parent)
	{
		this.parent = parent;
	}
}
