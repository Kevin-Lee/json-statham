/**
 * 
 */
package com.lckymn.kevin.jsonstatham;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Set;

import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;

/**
 * @author Lee, SeongHyun (Kevin)
 * @version 0.0.1 (2010-03-07)
 */
public final class JsonObjectPojoProxyFactory
{
	public static JsonObjectPojo newJsonObjectPojo(final JsonObjectPojo jsonObjectPojo, final Long id, final String name,
			final Collection<Address> addressCollection) throws IllegalArgumentException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setSuperclass(jsonObjectPojo.getClass());
		proxyFactory.setHandler(new MethodHandler()
		{
			@Override
			public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable
			{
				final String methodName = thisMethod.getName();

				if ("getId".equals(methodName))
				{
					return id;
				}
				else if ("getName".equals(methodName))
				{
					return name;
				}
				else if ("getAddresses".equals(methodName))
				{
					return addressCollection.iterator();
				}
				else
				{
					return proceed.invoke(self, args);
				}
			}
		});
		return (JsonObjectPojo) proxyFactory.create(new Class[] { Long.class, String.class, Set.class }, new Object[] { null, null, null });
	}

	public static NestedJsonObjectWithValueAccessor newNestedJsonObjectWithValueAccessor(
			final NestedJsonObjectWithValueAccessor nestedJsonObjectWithValueAccessor, final Long primaryKey, final String name,
			final NestedJsonObjectWithValueAccessor parent) throws IllegalArgumentException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException
	{
		ProxyFactory proxyFactory = new ProxyFactory();
		proxyFactory.setSuperclass(nestedJsonObjectWithValueAccessor.getClass());
		proxyFactory.setHandler(new MethodHandler()
		{
			@Override
			public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable
			{
				final String methodName = thisMethod.getName();

				if ("getPrimaryKey".equals(methodName))
				{
					return primaryKey;
				}
				else if ("getName".equals(methodName))
				{
					return name;
				}
				else if ("getParent".equals(methodName))
				{
					return parent;
				}
				else
				{
					return proceed.invoke(self, args);
				}
			}
		});
		return (NestedJsonObjectWithValueAccessor) proxyFactory.create(new Class[] { Long.class, String.class,
				NestedJsonObjectWithValueAccessor.class }, new Object[] { null, null, null });
	}
}
