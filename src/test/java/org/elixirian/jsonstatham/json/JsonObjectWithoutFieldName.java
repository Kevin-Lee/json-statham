package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;

/**
 * <pre>
 *     ___  _____                                _____
 *    /   \/    /_________  ___ ____ __ ______  /    /   ______  ______
 *   /        / /  ___ \  \/  //___// //     / /    /   /  ___ \/  ___ \
 *  /        \ /  _____/\    //   //   __   / /    /___/  _____/  _____/
 * /____/\____\\_____/   \__//___//___/ /__/ /________/\_____/ \_____/
 * </pre>
 * 
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-02-12)
 */
@Json
public class JsonObjectWithoutFieldName
{
  @JsonField
  private final long id;

  @JsonField
  private final String name;

  @JsonField
  private final String address;

  public JsonObjectWithoutFieldName(final long id, final String name, final String address)
  {
    this.id = id;
    this.name = name;
    this.address = address;
  }

  @SuppressWarnings("boxing")
  @Override
  public int hashCode()
  {
    return hash(id, name, address);
  }

  @Override
  public boolean equals(final Object jsonObjectWithoutFieldName)
  {
    if (identical(this, jsonObjectWithoutFieldName))
    {
      return true;
    }
    final JsonObjectWithoutFieldName that =
      castIfInstanceOf(JsonObjectWithoutFieldName.class, jsonObjectWithoutFieldName);
    /* @formatter:off */
		return isNotNull(that) && 
						   (equal(this.id, that.id) && 
								equal(this.name, that.name) && 
								equal(this.address, that.address));
		/* @formatter:on */
  }

  @Override
  public String toString()
  {
    return toStringBuilder(this).add("id", id)
        .add("name", name)
        .add("address", address)
        .toString();
  }
}
