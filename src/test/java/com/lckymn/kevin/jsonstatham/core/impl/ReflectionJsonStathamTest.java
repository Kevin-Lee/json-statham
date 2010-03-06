/**
 * 
 */
package com.lckymn.kevin.jsonstatham.core.impl;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.lckymn.kevin.jsonstatham.Address;
import com.lckymn.kevin.jsonstatham.ComplexJsonObjectWithValueAccessor;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingCollection;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingIterable;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingIterator;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingList;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingMapEntrySet;
import com.lckymn.kevin.jsonstatham.JsonObjectContainingSet;
import com.lckymn.kevin.jsonstatham.JsonObjectPojo;
import com.lckymn.kevin.jsonstatham.JsonObjectPojoImpl;
import com.lckymn.kevin.jsonstatham.JsonObjectPojoProxyFactory;
import com.lckymn.kevin.jsonstatham.JsonObjectWithDuplicateKeys;
import com.lckymn.kevin.jsonstatham.JsonObjectWithoutFieldName;
import com.lckymn.kevin.jsonstatham.NestedJsonObject;
import com.lckymn.kevin.jsonstatham.SecondSubClassWithOwnFields;
import com.lckymn.kevin.jsonstatham.SecondSubClassWithoutOwnFields;
import com.lckymn.kevin.jsonstatham.SomeImplementingClass;
import com.lckymn.kevin.jsonstatham.SomeInterface;
import com.lckymn.kevin.jsonstatham.SubClass;
import com.lckymn.kevin.jsonstatham.SubClassWithNoJsonObjectSuperClass;
import com.lckymn.kevin.jsonstatham.SubClassWithValueAccessor;
import com.lckymn.kevin.jsonstatham.SubClassWithValueAccessorWithAbstractMethod;
import com.lckymn.kevin.jsonstatham.SubClassWithValueAccessorWithOverriddenMethod;
import com.lckymn.kevin.jsonstatham.SubClassWithValueAccessorWithoutItsName;
import com.lckymn.kevin.jsonstatham.core.JSONObjectCreator;
import com.lckymn.kevin.jsonstatham.core.JsonStatham;
import com.lckymn.kevin.jsonstatham.exception.JsonStathamException;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2009-11-21)
 * @version 0.0.2 (2010-03-06) more test cases including the one testing proxy object created by javassist are added.
 */
public class ReflectionJsonStathamTest
{
	private static final List<String> streetList = Arrays.asList("ABC Street", "90/120 Swanston St");
	private static final List<String> suburbList = Arrays.asList("", "Test Suburb");
	private static final List<String> cityList = Arrays.asList("Sydney", "Melbourne");
	private static final List<String> stateList = Arrays.asList("NSW", "VIC");
	private static final List<String> postcodeList = Arrays.asList("2000", "3000");
	private static final String[] SOME_STRING_VALUE_ARRAY = { "111", "222", "aaa", "bbb", "ccc" };

	private static final Answer<JSONObject> ANSWER_FOR_NEW_JSON_OBJECT = new Answer<JSONObject>()
	{
		@Override
		public JSONObject answer(@SuppressWarnings("unused") InvocationOnMock invocation) throws Throwable
		{
			return new JSONObject(new LinkedHashMap<String, Object>());
		}
	};

	private List<Address> addressList;

	private Map<String, Address> addressMap;

	private JsonStatham jsonStatham;

	private Address address;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		System.out.println("### NonIndentedJsonStathamTest starts ###");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
		System.out.println("\n### NonIndentedJsonStathamTest ends ###");
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		final JSONObjectCreator jsonObjectCreator = mock(JSONObjectCreator.class);
		when(jsonObjectCreator.newJSONObject()).thenAnswer(ANSWER_FOR_NEW_JSON_OBJECT);

		jsonStatham = new ReflectionJsonStatham(jsonObjectCreator);
		address = new Address(streetList.get(0), suburbList.get(0), cityList.get(0), stateList.get(0), postcodeList.get(0));

		addressList = new ArrayList<Address>();
		for (int i = 0, size = streetList.size(); i < size; i++)
		{
			addressList.add(new Address(streetList.get(i), suburbList.get(i), cityList.get(i), stateList.get(i), postcodeList.get(i)));
		}

		addressMap = new LinkedHashMap<String, Address>();
		for (int i = 0, size = streetList.size(); i < size; i++)
		{
			addressMap.put("address" + i, new Address(streetList.get(i), suburbList.get(i), cityList.get(i), stateList.get(i),
					postcodeList.get(i)));
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
	}

	@Test(expected = JsonStathamException.class)
	public void testUnknownType()
	{
		class UnknownType
		{
		}
		jsonStatham.convertIntoJson(new UnknownType());
	}

	@Test
	public void testNull()
	{
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testNull()");
		final String expected = "null";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(null);
		System.out.println(result);
		assertEquals(expected.toString(), result);
	}

	private String getAddressArrayString()
	{
		final StringBuilder stringBuilder = new StringBuilder("[");
		for (Address address : addressList)
		{
			stringBuilder.append("{\"street\":\"")
					.append(address.getStreet())
					.append("\",\"suburb\":\"")
					.append(address.getSuburb())
					.append("\",\"city\":\"")
					.append(address.getCity())
					.append("\",\"state\":\"")
					.append(address.getState())
					.append("\",\"postcode\":\"")
					.append(address.getPostcode())
					.append("\"},");
		}
		if (1 < stringBuilder.length())
		{
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		stringBuilder.append("]");
		return stringBuilder.toString();
	}

	@Test
	public void testArray()
	{
		System.out.println("\nNonIndentedJsonStathamTest.testArray()");
		final String expected = getAddressArrayString();
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(addressList.toArray(new Address[addressList.size()]));
		System.out.println(result);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.ReflectionJsonStatham#convertIntoJson(java.lang.Object)} with List as
	 * the parameter object.
	 */
	@Test
	public void testList()
	{
		final String expected = getAddressArrayString();
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testList()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(addressList);
		System.out.println(result);
		assertEquals(expected, result);
	}

	private String getAddressMapString()
	{
		final StringBuilder stringBuilder = new StringBuilder("{");
		for (Entry<String, Address> entry : addressMap.entrySet())
		{
			Address address = entry.getValue();
			stringBuilder.append("\"" + entry.getKey() + "\":")
					.append("{\"street\":\"")
					.append(address.getStreet())
					.append("\",\"suburb\":\"")
					.append(address.getSuburb())
					.append("\",\"city\":\"")
					.append(address.getCity())
					.append("\",\"state\":\"")
					.append(address.getState())
					.append("\",\"postcode\":\"")
					.append(address.getPostcode())
					.append("\"},");
		}
		if (1 < stringBuilder.length())
		{
			stringBuilder.deleteCharAt(stringBuilder.length() - 1);
		}
		stringBuilder.append("}");
		return stringBuilder.toString();
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.ReflectionJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testMap()
	{
		final String expected = getAddressMapString();
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testMap()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(addressMap);
		System.out.println(result);
		assertEquals(expected, result);
	}

	@Test
	public void testNestedMap()
	{
		final String expected = "{\"test1\":" + getAddressMapString() + ",\"test2\":" + getAddressMapString() + "}";
		System.out.println("\nNonIndentedJsonStathamTest.testNestedMap()");
		System.out.println("expected: \n" + expected);
		Map<String, Object> nestedMap = new HashMap<String, Object>();
		nestedMap.put("test1", addressMap);
		nestedMap.put("test2", addressMap);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(nestedMap);
		System.out.println(result);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.ReflectionJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testSimpleJsonObject()
	{
		final String expected = "{\"street\":\"" + streetList.get(0) + "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\""
				+ cityList.get(0) + "\",\"state\":\"" + stateList.get(0) + "\",\"postcode\":\"" + postcodeList.get(0) + "\"}";
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testSimpleJsonObject()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(address);
		System.out.println(result);
		assertEquals(expected, result);
	}

	/**
	 * Test method for {@link com.lckymn.kevin.jsonstatham.core.impl.ReflectionJsonStatham#convertIntoJson(java.lang.Object)}.
	 */
	@Test
	public void testNestedJsonObject()
	{
		final long id = 1;
		final String name = "jsonObject";
		NestedJsonObject jsonObject = new NestedJsonObject();
		jsonObject.setPrimaryKey(Long.valueOf(id));
		jsonObject.setName(name);
		jsonObject.setAddress(address);

		final String expected = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":{\"street\":\"" + streetList.get(0)
				+ "\",\"suburb\":\"" + suburbList.get(0) + "\",\"city\":\"" + cityList.get(0) + "\",\"state\":\"" + stateList.get(0)
				+ "\",\"postcode\":\"" + postcodeList.get(0) + "\"}}";
		System.out.println("\nOrderedNonIndentedJsonStathamTest.testNestedJsonObject()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test(expected = JsonStathamException.class)
	public void testJsonObjectWithDuplicateKeys() throws IOException
	{
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectWithDuplicateKeys()");
		JsonObjectWithDuplicateKeys jsonObjectWithDuplicateKeys = new JsonObjectWithDuplicateKeys();
		jsonObjectWithDuplicateKeys.setUsername("kevinlee");
		jsonObjectWithDuplicateKeys.setName("Kevin");
		jsonObjectWithDuplicateKeys.setFullName("Kevin Lee");
		jsonObjectWithDuplicateKeys.setEmail("kevin@test.test");

		System.out.println("result: ");
		String result = "";
		try
		{
			result = jsonStatham.convertIntoJson(jsonObjectWithDuplicateKeys);
		}
		catch (JsonStathamException e)
		{
			System.out.println(e.getMessage());
			throw e;
		}
		System.out.println(result);
	}

	@Test
	public void testJsonObjectWithoutFieldName()
	{
		System.out.println("\nReflectionJsonStathamTest.testJsonObjectWithoutFieldName()");
		final int id = 5;
		final String name = "Kevin Lee";
		final String address = "123 ABC Street";
		final JsonObjectWithoutFieldName jsonObjectWithoutFieldName = new JsonObjectWithoutFieldName(id, name, address);
		final String expected = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"address\":\"" + address + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectWithoutFieldName);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testComplexJsonObjectWithMethodUse()
	{
		ComplexJsonObjectWithValueAccessor jsonObject = new ComplexJsonObjectWithValueAccessor();
		jsonObject.setPrimaryKey(Long.valueOf(1));
		jsonObject.setName("Kevin");
		jsonObject.setAddress(address);
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		jsonObject.setDate(date);
		jsonObject.setDateWithValueAccessor(date);
		jsonObject.setCalendar(calendar);
		jsonObject.setCalendarWithValueAccessor(calendar);

		final String expected = "{\"id\":1,\"name\":\"Kevin\"," + "\"address\":{\"street\":\"" + address.getStreet() + "\",\"suburb\":\""
				+ address.getSuburb() + "\",\"city\":\"" + address.getCity() + "\",\"state\":\"" + address.getState()
				+ "\",\"postcode\":\"" + address.getPostcode() + "\"}," + "\"date\":\"" + date.toString() + "\","
				+ "\"dateWithValueAccessor\":\"" + jsonObject.getDateString() + "\",\"calendar\":\"" + jsonObject.getCalendar()
						.getTime()
						.toString() + "\",\"calendarWithValueAccessor\":\"" + jsonObject.getCalendarString() + "\"}";
		System.out.println("\nNonIndentedJsonStathamTest.testComplexJsonObjectWithMethodUse()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	private String getExpectedJsonArray(String name, String value, String setName)
	{
		StringBuilder stringBuilder = new StringBuilder("{\"").append(name)
				.append("\":\"")
				.append(value)
				.append("\",\"")
				.append(setName)
				.append("\":[");
		for (String element : SOME_STRING_VALUE_ARRAY)
		{
			stringBuilder.append("\"")
					.append(element)
					.append("\"")
					.append(",");
		}
		return stringBuilder.deleteCharAt(stringBuilder.length() - 1)
				.append("]}")
				.toString();
	}

	private <V extends Object, T extends Collection<V>> T initialiseCollectionWithStringValues(T t, V... values)
	{
		for (V value : values)
		{
			t.add(value);
		}
		return t;
	}

	@Test
	public void testJsonObjectContainingCollection()
	{
		final String nameValue = "testJsonWithCollection";
		Collection<String> collection = initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

		JsonObjectContainingCollection jsonObjectContainingCollection = new JsonObjectContainingCollection(nameValue, collection);
		final String expected = getExpectedJsonArray("name", nameValue, "valueCollection");
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectContainingCollection()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingCollection);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingList()
	{
		final String nameValue = "testJsonWithList";
		List<String> list = initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

		JsonObjectContainingList jsonObjectContainingList = new JsonObjectContainingList(nameValue, list);
		final String expected = getExpectedJsonArray("name", nameValue, "valueList");
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectContainingList()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingList);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingSet()
	{
		final String nameValue = "testJsonWithSet";
		Set<String> set = initialiseCollectionWithStringValues(new LinkedHashSet<String>(), SOME_STRING_VALUE_ARRAY);

		JsonObjectContainingSet jsonObjectContainingSet = new JsonObjectContainingSet(nameValue, set);
		final String expected = getExpectedJsonArray("name", nameValue, "valueSet");
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectContainingSet()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingSet);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingMapEntrySetSet()
	{
		final String nameValue = "testJsonObjectContainingMapEntrySetSet";

		JsonObjectContainingMapEntrySet jsonObjectContainingSet = new JsonObjectContainingMapEntrySet(nameValue, addressMap.entrySet());

		StringBuilder stringBuilder = new StringBuilder("{\"name\":\"testJsonObjectContainingMapEntrySetSet\",\"valueMapEntrySet\":[");
		for (Entry<String, Address> entry : addressMap.entrySet())
		{
			Address address = entry.getValue();
			stringBuilder.append("{\"" + entry.getKey() + "\":")
					.append("{\"street\":\"")
					.append(address.getStreet())
					.append("\",\"suburb\":\"")
					.append(address.getSuburb())
					.append("\",\"city\":\"")
					.append(address.getCity())
					.append("\",\"state\":\"")
					.append(address.getState())
					.append("\",\"postcode\":\"")
					.append(address.getPostcode())
					.append("\"}},");
		}
		final String expected = stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "]}")
				.toString();

		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectContainingMapEntrySetSet()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingSet);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingIterator()
	{
		final String nameValue = "testJsonObjectContainingIterator";
		Collection<String> collection = initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY);

		JsonObjectContainingIterator jsonObjectContainingCollection = new JsonObjectContainingIterator(nameValue, collection.iterator());
		final String expected = getExpectedJsonArray("name", nameValue, "valueIterator");
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectContainingIterator()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingCollection);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectContainingIterable()
	{
		final String nameValue = "testJsonObjectContainingIterable";
		Iterable<String> iterable = new Iterable<String>()
		{
			@Override
			public Iterator<String> iterator()
			{
				return initialiseCollectionWithStringValues(new ArrayList<String>(), SOME_STRING_VALUE_ARRAY).iterator();
			}
		};

		JsonObjectContainingIterable jsonObjectContainingCollection = new JsonObjectContainingIterable(nameValue, iterable);
		final String expected = getExpectedJsonArray("name", nameValue, "valueIterable");
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectContainingIterator()");
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectContainingCollection);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithInterfaceInheritance()
	{
		final String name = "Kevin Lee";
		final int number = 99;
		final String email = "kevinlee@test.test";
		SomeInterface jsonObject = new SomeImplementingClass(name, number, email);
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectWithInterfaceInheritance()");
		final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritance()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClass jsonObject = new SubClass(name, number, email);
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectWithImplementationInheritance()");
		final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()
	{
		final String name = "Kevin";
		final int number = 11;
		final String email = "kevin@test.blahblah";
		SubClass jsonObject = new SecondSubClassWithoutOwnFields(name, number, email);
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectWithDoubleImplementationInheritanceAndNoOwnFieldsInSecondSubClass()");
		final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()
	{
		final String name = "Mr. Lee";
		final int number = 999;
		final String email = "kevin@another.email";
		final String comment = "Blah blah";
		SecondSubClassWithOwnFields jsonObject = new SecondSubClassWithOwnFields(name, number, email, address, comment);
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectWithDoubleImplementationInheritanceAndOwnFieldsInSecondSubClass()");
		final String expected = "{\"name\":\"" + name + "\",\"number\":" + number + ",\"email\":\"" + email + "\"," + "\"address\":"
				+ "{\"street\":\"" + address.getStreet() + "\",\"suburb\":\"" + address.getSuburb() + "\",\"city\":\"" + address.getCity()
				+ "\",\"state\":\"" + address.getState() + "\",\"postcode\":\"" + address.getPostcode() + "\"},\"comment\":\"" + comment
				+ "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClassWithNoJsonObjectSuperClass jsonObject = new SubClassWithNoJsonObjectSuperClass(name, number, email);
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectWithImplementationInheritanceWithNoJsonObjectSuperClass()");
		final String expected = "{\"email\":\"" + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessor()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClassWithValueAccessor jsonObject = new SubClassWithValueAccessor(name, number, email);
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessor()");
		final String expected = "{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number
				+ "\",\"email\":\"My email address is " + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClassWithValueAccessorWithoutItsName jsonObject = new SubClassWithValueAccessorWithoutItsName(name, number, email);
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithoutItsName()");
		final String expected = "{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number
				+ "\",\"email\":\"My email address is " + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClassWithValueAccessorWithAbstractMethod jsonObject = new SubClassWithValueAccessorWithAbstractMethod(name, number, email);
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithAbstractMethod()");
		final String expected = "{\"name\":\"My name is nobody.\",\"number\":\"The number is 100.\",\"email\":\"My email address is "
				+ email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()
	{
		final String name = "Kevin";
		final int number = 5;
		final String email = "kevin@test.test";
		SubClassWithValueAccessorWithOverriddenMethod jsonObject = new SubClassWithValueAccessorWithOverriddenMethod(name, number, email);
		System.out.println("\nNonIndentedJsonStathamTest.testJsonObjectWithImplementationInheritanceWithValueAccessorWithOverriddenMethod()");
		final String expected = "{\"name\":\"My name is " + name + "\",\"number\":\"The number is " + number
				+ "\",\"email\":\"My email address is " + email + "\"}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObject);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}

	@Test
	public void testProxiedJsonObjectPojo() throws IllegalArgumentException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		final long id = 999L;
		final String name = "ProxiedPojo";
		JsonObjectPojo jsonObjectPojo = JsonObjectPojoProxyFactory.newJsonObjectPojo(new JsonObjectPojoImpl(null, null, null), id, name,
				addressList);

		System.out.println("\nNonIndentedJsonStathamTest.testProxiedJsonObjectPojo()");
		final String expected = "{\"id\":" + id + ",\"name\":\"" + name + "\",\"addresses\":" + getAddressArrayString() + "}";
		System.out.println("expected:\n" + expected);
		System.out.println("actual: ");
		final String result = jsonStatham.convertIntoJson(jsonObjectPojo);
		System.out.println(result);
		assertThat(result, equalTo(expected));
	}
}
