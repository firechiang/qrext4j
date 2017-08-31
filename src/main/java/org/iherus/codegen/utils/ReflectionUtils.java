package org.iherus.codegen.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Reflection Utility.
 *
 * @author Bosco.Liao
 * @since 1.3.0
 */
public class ReflectionUtils {

	private ReflectionUtils() {

	}
	
	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 */
	public static Object invokeMethod(final Object obj, final String methodName) {
		return invokeMethod(obj, methodName, new Class[] {}, new Object[] {});
	}
	
	/**
	 * 直接调用对象方法, 无视private/protected修饰符.
	 */
	public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes,
			final Object[] args) {
		Method method = getAccessibleMethod(obj, methodName, parameterTypes);
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + obj + "]");
		}
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
	 */
	public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
		notNull(obj, "Parameter 'obj' can't be null.");
		notBlank(fieldName, "Parameter 'fieldName' can't be blank.");
		Field field = getAccessibleField(obj, fieldName);
		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + obj + "]");
		}
		try {
			field.set(obj, value);
		} catch (IllegalAccessException e) {
			// ignore
		}
	}

	/**
	 * 不断向上从对象中搜寻目标字段
	 * 
	 * @param obj 指定对象
	 * @param fieldName 目标字段名称
	 * @return 字段对象
	 */
	public static Field getAccessibleField(final Object obj, final String fieldName) {
		notNull(obj, "Parameter 'obj' can't be null.");
		notBlank(fieldName, "Parameter 'fieldName' can't be blank.");
		for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				Field field = superClass.getDeclaredField(fieldName);
				makeAccessible(field);
				return field;
			} catch (NoSuchFieldException e) {
				// ignore
			}
		}
		return null;
	}
	
	/**
	 * 不断向上从对象中搜寻目标方法
	 * 
	 * @param obj 指定对象
	 * @param methodName 目标方法名称
	 * @param parameterTypes 方法中参数类型
	 * @return 方法对象
	 */
	public static Method getAccessibleMethod(final Object obj, final String methodName,
			final Class<?>... parameterTypes) {
		notNull(obj, "Parameter 'obj' can't be null.");
		notBlank(methodName, "Parameter 'methodName' can't be blank.");
		for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
				.getSuperclass()) {
			try {
				Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
				makeAccessible(method);
				return method;
			} catch (NoSuchMethodException e) {
				// ignore
			}
		}
		return null;
	}
	
	/**
	 * 设置可访问方法
	 * 
	 * @param method 目标方法
	 */
	public static void makeAccessible(Method method) {
		if ((!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
				&& !method.isAccessible()) {
			method.setAccessible(true);
		}
	}

	/**
	 * 设置可访问字段
	 * 
	 * @param field 目标字段
	 */
	public static void makeAccessible(Field field) {
		if ((!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
				|| Modifier.isFinal(field.getModifiers())) && !field.isAccessible()) {
			field.setAccessible(true);
		}
	}

	/**
	 * 获取对象中指定字段的值
	 * 
	 * @param obj 指定对象
	 * @param fieldName 目标字段名称
	 * @return 值对象
	 */
	public static Object getFieldValue(final Object obj, final String fieldName) {
		Field field = getAccessibleField(obj, fieldName);
		try {
			return field.get(obj);
		} catch (Exception e) {
			// ignore
		}
		return null;
	}

	/**
	 * Copy form "commons-lang3"
	 */
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Copy form "commons-lang3"
	 */
	public static <T extends CharSequence> T notBlank(final T chars, final String message, final Object... values) {
		if (chars == null) {
			throw new NullPointerException(String.format(message, values));
		}
		if (isBlank(chars)) {
			throw new IllegalArgumentException(String.format(message, values));
		}
		return chars;
	}

	/**
	 * Copy form "commons-lang3"
	 */
	public static <T> T notNull(final T object, final String message, final Object... values) {
		if (object == null) {
			throw new NullPointerException(String.format(message, values));
		}
		return object;
	}

}
