/**
 * This project is licensed under the Apache License, Version 2.0
 * if the following condition is met:
 * (otherwise it cannot be used by anyone but the author, Kevin, only)
 *
 * The original JSON Statham project is owned by Lee, Seong Hyun (Kevin).
 *
 * -What does it mean to you?
 * Nothing, unless you want to take the ownership of
 * "the original project" (not yours or forked & modified one).
 * You are free to use it for both non-commercial and commercial projects
 * and free to modify it as the Apache License allows.
 *
 * -So why is this condition necessary?
 * It is only to protect the original project (See the case of Java).
 *
 *
 * Copyright 2009 Lee, Seong Hyun (Kevin)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.elixirian.jsonstatham.json;

import static org.elixirian.kommonlee.util.Objects.*;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.annotation.JsonField;
import org.elixirian.jsonstatham.annotation.ValueAccessor;

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
 * @version 0.0.1 (2010-03-06)
 */
@Json
public abstract class SuperClassWithValueAccessor
{
  @ValueAccessor(name = "name")
  @JsonField(name = "name")
  private String name;

  @ValueAccessor(name = "number")
  @JsonField(name = "number")
  private int number;

  public SuperClassWithValueAccessor(final String name, final int number)
  {
    this.name = name;
    this.number = number;
  }

  /**
   * @return the name
   */
  public String name()
  {
    return "My name is " + name;
  }

  /**
   * @param name
   *          the name to set
   */
  public void setName(final String name)
  {
    this.name = name;
  }

  /**
   * @return the number
   */
  public String number()
  {
    return "The number is " + number;
  }

  /**
   * @param number
   *          the number to set
   */
  public void setNumber(final int number)
  {
    this.number = number;
  }

  @Override
  public int hashCode()
  {
    return hash(name, number);
  }

  @Override
  public boolean equals(final Object superClassWithValueAccessor)
  {
    if (identical(this, superClassWithValueAccessor))
    {
      return true;
    }
    final SuperClassWithValueAccessor that =
      castIfInstanceOf(SuperClassWithValueAccessor.class, superClassWithValueAccessor);
    /* @formatter:off */
		return isNotNull(that) && 
						   (equal(this.name, that.name) && 
								equal(this.number, that.number));
		/* @formatter:on */
  }
}
