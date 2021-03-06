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
package org.elixirian.jsonstatham.core.reflect.java2json;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter;
import org.elixirian.jsonstatham.core.KnownTypeProcessorWithReflectionJavaToJsonConverter;
import org.elixirian.jsonstatham.core.convertible.JsonArray;
import org.elixirian.jsonstatham.core.convertible.JsonArrayWithOrderedJsonObjectCreator;
import org.elixirian.jsonstatham.core.convertible.JsonConvertible;
import org.elixirian.jsonstatham.core.convertible.JsonObject;
import org.elixirian.jsonstatham.core.convertible.OrderedJsonObjectCreator;
import org.elixirian.jsonstatham.exception.JsonStathamException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

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
 * @version 0.0.1 (2010-06-11)
 */
public class KnownDataStructureTypeProcessorDeciderTest
{
  private static String[] strings = { null, "Kevin", "Lee", "test", "string" };
  private static int[] ints = { Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -1, 1 };
  private static Integer[] integers = { Integer.valueOf(Integer.MIN_VALUE), Integer.valueOf(Integer.MAX_VALUE),
      Integer.valueOf(0), Integer.valueOf(-1), Integer.valueOf(1) };
  private static Collection<String> collection1 = Arrays.asList(strings);
  private static Collection<Integer> collection2 = Arrays.asList(integers);
  private static Iterable<String> iterable1 = collection1;
  private static Iterable<Integer> iterable2 = collection2;
  private static Iterator<String> iterator1 = collection1.iterator();
  private static Iterator<Integer> iterator2 = collection2.iterator();
  private static Map<Long, String> map = new HashMap<Long, String>();

  /**
   * @throws java.lang.Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception
  {
    strings = new String[] { null, "Kevin", "Lee", "test", "string" };
    ints = new int[] { Integer.MIN_VALUE, Integer.MAX_VALUE, 0, -1, 1 };
    integers =
      new Integer[] { Integer.valueOf(Integer.MIN_VALUE), Integer.valueOf(Integer.MAX_VALUE), Integer.valueOf(0),
          Integer.valueOf(-1), Integer.valueOf(1) };
    collection1 = Arrays.asList(strings);
    collection2 = Arrays.asList(integers);
    iterable1 = collection1;
    iterable2 = collection2;
    iterator1 = collection1.iterator();
    iterator2 = collection2.iterator();
    map = new HashMap<Long, String>();
    map.put(Long.valueOf(1L), "Kevin");
    map.put(Long.valueOf(2L), "Lee");
    map.put(Long.valueOf(3L), "Tom");
    map.put(Long.valueOf(4L), "Peter");
    map.put(Long.valueOf(5L), "Steve");
  }

  /**
   * @throws java.lang.Exception
   */
  @After
  public void tearDown() throws Exception
  {
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider#KnownDataStructureTypeProcessorDecider()}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public final void testKnownDataStructureTypeProcessorDecider() throws IllegalArgumentException, JsonStathamException,
      IllegalAccessException
  {
    final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
      new KnownDataStructureTypeProcessorDecider();

    final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider =
      mock(KnownObjectReferenceTypeProcessorDecider.class);
    when(knownObjectReferenceTypeProcessorDecider.decide(Mockito.any(Class.class))).thenReturn(null);

    final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);
    when(oneProcessorForKnownTypeDecider.decide(Mockito.any(Class.class))).thenReturn(
        new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
          @Override
          public <T> Object process(
              @SuppressWarnings("unused") final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
              final Class<T> valueType, final Object source) throws IllegalArgumentException, IllegalAccessException,
              JsonStathamException
          {
            return source;
          }
        });

    // final JsonStathamInAction jsonStatham =
    // new JsonStathamInAction(new ReflectionJavaToJsonConverter(new OrgJsonOrderedJsonObjectCreator(),
    // new OrgJsonJsonArrayCreator(), knownDataStructureTypeProcessorDecider,
    // knownObjectReferenceTypeProcessorDecider, oneProcessorForKnownTypeDecider), new
    // ReflectionJsonToJavaConverter());

    assertThat(knownDataStructureTypeProcessorDecider.decide(strings.getClass())).isNotNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(ints.getClass())).isNotNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(integers.getClass())).isNotNull();

    assertThat(knownDataStructureTypeProcessorDecider.decide(collection1.getClass())).isNotNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(collection2.getClass())).isNotNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable1.getClass())).isNotNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable2.getClass())).isNotNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator1.getClass())).isNotNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator2.getClass())).isNotNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(map.getClass())).isNotNull();
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider#KnownDataStructureTypeProcessorDecider(java.util.Map)}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public final void testKnownDataStructureTypeProcessorDeciderMapOfClassOfQKnownTypeProcessor()
      throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final Map<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter> knownDataStructuresProcessorMap =
      new HashMap<Class<?>, KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter>();
    knownDataStructuresProcessorMap.put(NavigableSet.class, new KnownTypeProcessorWithReflectionJavaToJsonConvertableConverter() {
      @SuppressWarnings("unchecked")
      @Override
      public <T> JsonConvertible process(
          @SuppressWarnings("unused") final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
          final Class<T> valueType, final Object source) throws IllegalArgumentException, IllegalAccessException,
          JsonStathamException
      {
        final JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
        for (final Object each : (NavigableSet<Object>) source)
        {
          jsonArray.put(each);
        }
        return jsonArray;
      }
    });
    final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
      new KnownDataStructureTypeProcessorDecider(knownDataStructuresProcessorMap);

    final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider =
      mock(KnownObjectReferenceTypeProcessorDecider.class);
    when(knownObjectReferenceTypeProcessorDecider.decide(Mockito.any(Class.class))).thenReturn(null);

    final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);
    when(oneProcessorForKnownTypeDecider.decide(Mockito.any(Class.class))).thenReturn(
        new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
          @Override
          public <T> Object process(
              @SuppressWarnings("unused") final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
              final Class<T> valueType, final Object source) throws IllegalArgumentException, IllegalAccessException,
              JsonStathamException
          {
            return source;
          }
        });

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
      new ReflectionJavaToJsonConverter(new OrderedJsonObjectCreator(), new JsonArrayWithOrderedJsonObjectCreator(),
          knownDataStructureTypeProcessorDecider, knownObjectReferenceTypeProcessorDecider,
          oneProcessorForKnownTypeDecider);
    // final JsonStathamInAction jsonStatham = new JsonStathamInAction(reflectionJavaToJsonConverter, new
    // ReflectionJsonToJavaConverter());

    final NavigableSet<String> testSet = new TreeSet<String>();
    testSet.add("Hello");
    testSet.add("Kevin");
    testSet.add("Lee");

    final JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (final String each : testSet)
    {
      jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(testSet.getClass())
        .process(reflectionJavaToJsonConverter, testSet.getClass(), testSet)
        .toString()).isEqualTo(jsonArray.toString());

    assertThat(knownDataStructureTypeProcessorDecider.decide(strings.getClass())).isNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(ints.getClass())).isNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(integers.getClass())).isNull();

    assertThat(knownDataStructureTypeProcessorDecider.decide(collection1.getClass())).isNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(collection2.getClass())).isNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable1.getClass())).isNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable2.getClass())).isNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator1.getClass())).isNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator2.getClass())).isNull();
    assertThat(knownDataStructureTypeProcessorDecider.decide(map.getClass())).isNull();
  }

  /**
   * Test method for
   * {@link org.elixirian.jsonstatham.core.reflect.java2json.KnownDataStructureTypeProcessorDecider#decide(java.lang.Class)}
   * .
   * 
   * @throws IllegalAccessException
   * @throws JsonStathamException
   * @throws IllegalArgumentException
   */
  @Test
  public final void testDecide() throws IllegalArgumentException, JsonStathamException, IllegalAccessException
  {
    final KnownDataStructureTypeProcessorDecider knownDataStructureTypeProcessorDecider =
      new KnownDataStructureTypeProcessorDecider();

    final KnownObjectReferenceTypeProcessorDecider knownObjectReferenceTypeProcessorDecider =
      mock(KnownObjectReferenceTypeProcessorDecider.class);
    when(knownObjectReferenceTypeProcessorDecider.decide(Mockito.any(Class.class))).thenReturn(null);

    final OneProcessorForKnownTypeDecider oneProcessorForKnownTypeDecider = mock(OneProcessorForKnownTypeDecider.class);
    when(oneProcessorForKnownTypeDecider.decide(Mockito.any(Class.class))).thenReturn(
        new KnownTypeProcessorWithReflectionJavaToJsonConverter() {
          @Override
          public <T> Object process(
              @SuppressWarnings("unused") final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter,
              final Class<T> valueType, final Object source) throws IllegalArgumentException, IllegalAccessException,
              JsonStathamException
          {
            return source;
          }
        });

    final ReflectionJavaToJsonConverter reflectionJavaToJsonConverter =
      new ReflectionJavaToJsonConverter(new OrderedJsonObjectCreator(), new JsonArrayWithOrderedJsonObjectCreator(),
          knownDataStructureTypeProcessorDecider, knownObjectReferenceTypeProcessorDecider,
          oneProcessorForKnownTypeDecider);
    // final JsonStathamInAction jsonStatham =
    // new JsonStathamInAction(reflectionJavaToJsonConverter, new ReflectionJsonToJavaConverter());

    JsonArray jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (final String each : strings)
    {
      jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(strings.getClass())
        .process(reflectionJavaToJsonConverter, strings.getClass(), strings)
        .toString()).isEqualTo(jsonArray.toString());

    jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (final int each : ints)
    {
      jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(ints.getClass())
        .process(reflectionJavaToJsonConverter, ints.getClass(), ints)
        .toString()).isEqualTo(jsonArray.toString());

    jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (final Integer each : integers)
    {
      jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(integers.getClass())
        .process(reflectionJavaToJsonConverter, integers.getClass(), integers)
        .toString()).isEqualTo(jsonArray.toString());

    jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (final String each : collection1)
    {
      jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(collection1.getClass())
        .process(reflectionJavaToJsonConverter, collection1.getClass(), collection1)
        .toString()).isEqualTo(jsonArray.toString());

    jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (final Integer each : collection2)
    {
      jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(collection2.getClass())
        .process(reflectionJavaToJsonConverter, collection2.getClass(), collection2)
        .toString()).isEqualTo(jsonArray.toString());

    jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (final String each : iterable1)
    {
      jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable1.getClass())
        .process(reflectionJavaToJsonConverter, iterable1.getClass(), iterable1)
        .toString()).isEqualTo(jsonArray.toString());

    jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (final Integer each : iterable2)
    {
      jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(each));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterable2.getClass())
        .process(reflectionJavaToJsonConverter, iterable2.getClass(), iterable2)
        .toString()).isEqualTo(jsonArray.toString());

    jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (final Iterator<String> it = iterable1.iterator(); it.hasNext();)
    {
      jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(it.next()));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator1.getClass())
        .process(reflectionJavaToJsonConverter, iterable1.getClass(), iterator1)
        .toString()).isEqualTo(jsonArray.toString());

    jsonArray = reflectionJavaToJsonConverter.newJsonArrayConvertible();
    for (final Iterator<Integer> it = iterable2.iterator(); it.hasNext();)
    {
      jsonArray.put(reflectionJavaToJsonConverter.createJsonValue(it.next()));
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(iterator2.getClass())
        .process(reflectionJavaToJsonConverter, iterator2.getClass(), iterator2)
        .toString()).isEqualTo(jsonArray.toString());

    final JsonObject jsonObject = reflectionJavaToJsonConverter.newJsonObjectConvertible();
    for (final Entry<Long, String> entry : map.entrySet())
    {
      jsonObject.put(String.valueOf(entry.getKey()), entry.getValue());
    }
    assertThat(knownDataStructureTypeProcessorDecider.decide(map.getClass())
        .process(reflectionJavaToJsonConverter, map.getClass(), map)
        .toString()).isEqualTo(jsonObject.toString());
  }
}
