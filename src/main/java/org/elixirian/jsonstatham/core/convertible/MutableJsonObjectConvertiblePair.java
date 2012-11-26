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
package org.elixirian.jsonstatham.core.convertible;

import static org.elixirian.kommonlee.util.Objects.*;

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
 * @version 0.0.1 (2012-10-23)
 */
public class MutableJsonObjectConvertiblePair<L, R> extends AbstractJsonObjectConvertiblePair<L, R>
{
	private L first;
	private R second;

	public MutableJsonObjectConvertiblePair()
	{
	}

	public MutableJsonObjectConvertiblePair(final L first, final R second)
	{
		this.first = first;
		this.second = second;
	}

	@Override
	public L getFirst()
	{
		return first;
	}

	public void setFirst(final L first)
	{
		this.first = first;
	}

	@Override
	public R getSecond()
	{
		return second;
	}

	public void setSecond(final R second)
	{
		this.second = second;
	}

	@Override
	public int hashCode()
	{
		return hash(first, second);
	}

	@Override
	public boolean equals(final Object jsonObjectConvertiblePair)
	{
		if (this == jsonObjectConvertiblePair)
		{
			return true;
		}
		final MutableJsonObjectConvertiblePair<?, ?> that =
			castIfInstanceOf(MutableJsonObjectConvertiblePair.class, jsonObjectConvertiblePair);
		/* @formatter:off */
		return null != that &&
						(equal(this.first, that.getFirst()) &&
						 equal(this.second, that.getSecond()));
		/* @formatter:on */
	}

	@Override
	public String toString()
	{
		/* @formatter:off */
		return toStringBuilder(this)
				.add("first", first)
				.add("second", second)
				.toString();
		/* @formatter:on */
	}
}