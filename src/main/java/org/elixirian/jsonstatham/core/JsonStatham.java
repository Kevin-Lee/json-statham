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
package org.elixirian.jsonstatham.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.Map;

import org.elixirian.jsonstatham.annotation.Json;
import org.elixirian.jsonstatham.core.convertible.JsonConvertible;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.elixirian.kommonlee.io.CharAndStringWritable;
import org.elixirian.kommonlee.io.CharReadable;
import org.elixirian.kommonlee.reflect.TypeHolder;

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
 * @version 0.0.1 (2009-11-21)
 * @version 0.1.0 (2010-09-08) {@link #convertFromJson(Class, String)} is added.
 * @version 0.1.0 (2010-10-09) {@link #convertFromJson(TypeHolder, String)} is added.
 */
public interface JsonStatham
{
  /**
   * Returns String value containing JSON text. It turns the given target object which must be annotated with
   * {@link Json} into JSON text. If the target object is not annotated with {@link Json}, it throws
   * {@link IllegalStateException}.
   *
   * @param target
   *          the target object.
   * @return String value which contains JSON text created based on the given target object.
   * @throws JsonStathamException
   *           TODO: finish it!
   */
  String convertIntoJson(Object source) throws JsonStathamException;

  /**
   * Return a {@link JsonConvertible} object converted from the given JSON object which is annotated with {@link Json}
   * or any object that can be converted into {@link JsonConvertible}.
   *
   * @param target
   *          the given JSON object which is annotated with {@link Json} or any object that can be converted into
   *          {@link JsonConvertible}.
   * @return a {@link JsonConvertible} object converted from the given JSON object.
   * @throws JsonStathamException
   */
  <T extends JsonConvertible> T convertIntoJsonConvertible(Object source) throws JsonStathamException;

  /**
   * Converts the given object into JSON then writer it using the given {@link CharAndStringWritable}. It returns the
   * {@link CharAndStringWritable} without flushing and closing it. So it should be flushed and closed by the caller.
   *
   * @param source
   * @param charAndStringWritable
   * @return
   * @throws JsonStathamException
   */
  CharAndStringWritable convertIntoJsonWriteAndGetWriter(Object source, CharAndStringWritable charAndStringWritable)
      throws JsonStathamException;

  /**
   * Converts the given object into JSON then writer it using the given {@link Writer}. It returns a
   * {@link CharAndStringWritable} object containing the {@link Writer} without flushing and closing it. So it should be
   * flushed and closed by the caller.
   *
   * @param source
   * @param writer
   * @return
   * @throws JsonStathamException
   */
  CharAndStringWritable convertIntoJsonWriteAndGetWriter(Object source, Writer writer) throws JsonStathamException;

  /**
   * Converts the given object into JSON then writer it using the given {@link OutputStream}. It returns a
   * {@link CharAndStringWritable} object containing the {@link OutputStream} without flushing and closing it. So it
   * should be flushed and closed by the caller.
   *
   * @param source
   * @param outputStream
   * @return
   * @throws JsonStathamException
   */
  CharAndStringWritable convertIntoJsonWriteAndGetWriter(Object source, OutputStream outputStream) throws JsonStathamException;

  /**
   * Converts the given object into JSON then writer it using the given {@link CharAndStringWritable}. It flushes and
   * closes the {@link CharAndStringWritable} after writing the JSON.
   *
   * @param source
   * @param charAndStringWritable
   * @throws JsonStathamException
   */
  void convertIntoJsonAndWrite(Object source, CharAndStringWritable charAndStringWritable) throws JsonStathamException;

  /**
   * Converts the given object into JSON then writer it using the given {@link Writer}. It flushes and closes the
   * {@link Writer} after writing the JSON.
   *
   * @param source
   * @param writer
   * @throws JsonStathamException
   */
  void convertIntoJsonAndWrite(Object source, Writer writer) throws JsonStathamException;

  /**
   * Converts the given object into JSON then writer it using the given {@link OutputStream}. It flushes and closes the
   * {@link OutputStream} after writing the JSON.
   *
   * @param source
   * @param outputStream
   */
  void convertIntoJsonAndWrite(Object source, OutputStream outputStream);

  JsonConvertible convertJsonStringIntoJsonConvertible(String json) throws JsonStathamException;

  /**
   * Returns an object of the given type. It extracts all the data from the given JSON String then creates the given
   * type object based on the data.
   *
   * @param <T>
   *          the type of the object to be created.
   * @param type
   *          the given type.
   * @param json
   *          the given JSON String used to create the given type object.
   * @return T type object containing the data extracted from the JSON String.
   * @throws JsonStathamException
   *           TODO: finish it!
   */
  <T> T convertFromJson(Class<T> type, String json) throws JsonStathamException;

  /**
   * Returns an object of the type which the given {@link TypeHolder} contains. It extracts all the data from the given
   * JSON String then creates the given type object based on the data. This method is useful to create array,
   * {@link Collection} and {@link Map} objects directly from JSON.
   *
   * @param <T>
   *          the type of the object to be created.
   * @param typeHolder
   *          the {@link TypeHolder} object containing the type to be created.
   * @param jsonString
   *          the given JSON String used to create the given type object.
   * @return T type object containing the data extracted from the JSON String.
   * @throws JsonStathamException
   */
  <T> T convertFromJson(TypeHolder<T> typeHolder, String jsonString) throws JsonStathamException;

  <T> T convertFromJsonConvertible(Class<T> type, JsonConvertible jsonConvertible) throws JsonStathamException;

  <T> T convertFromJsonConvertible(TypeHolder<T> typeHolder, JsonConvertible jsonConvertible) throws JsonStathamException;

  <T> T convertFromJson(Class<T> type, CharReadable charReadable);

  <T> T convertFromJson(Class<T> type, Reader reader);

  <T> T convertFromJson(Class<T> type, InputStream inputStream);
}
