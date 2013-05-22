package de.myreality.chronos.util;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This file is part of Chronos (Myreality Game Development Toolkit).
 * Chronos is licenced under GNU LESSER GENERAL PUBLIC LICENSE (Version 3)
 * <br /><br />
 * For more information visit http://dev.my-reality.de/chronos
 * <br /><br />
 * Determines the type and class of a template
 * 
 * @author Miguel Gonzalez <miguel-gonzalez@gmx.de>
 * @since 1.0
 */
public class ReflectionTemplate<Type> {

	@SuppressWarnings("unchecked")
	public Class<Type> getInnerClass() {
		return (Class<Type>) getTypeArguments(ReflectionTemplate.class, getClass()).get(0);
	}
	
	/**
	   * Get the underlying class for a type, or null if the type is a variable type.
	   * @param type the type
	   * @return the underlying class
	   */
	  @SuppressWarnings("unchecked")
	public static <Type> Class<?> getClass(Type type) {
	    if (type instanceof Class) {
	      return (Class<?>) type;
	    }
	    else if (type instanceof ParameterizedType) {
	      return getClass(((ParameterizedType) type).getRawType());
	    }
	    else if (type instanceof GenericArrayType) {
	      Type componentType = (Type) ((GenericArrayType) type).getGenericComponentType();
	      Class<?> componentClass = getClass(componentType);
	      if (componentClass != null ) {
	        return Array.newInstance(componentClass, 0).getClass();
	      }
	      else {
	        return null;
	      }
	    }
	    else {
	      return null;
	    }
	  }
	  
	  
	  
	  /**
	   * Get the actual type arguments a child class has used to extend a generic base class.
	   *
	   * @param baseClass the base class
	   * @param childClass the child class
	   * @return a list of the raw classes for the actual type arguments.
	   */
	  @SuppressWarnings("unchecked")
	public static <Type> List<Class<?>> getTypeArguments(
	    Class<Type> baseClass, Class<? extends Type> childClass) {
	    Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
	    Type type = (Type) childClass;
	    // start walking up the inheritance hierarchy until we hit baseClass
	    while (! getClass(type).equals(baseClass)) {
	      if (type instanceof Class) {
	        // there is no useful information for us in raw types, so just keep going.
	        type = (Type) ((Class<?>) type).getGenericSuperclass();
	      }
	      else {
	        ParameterizedType parameterizedType = (ParameterizedType) type;
	        Class<?> rawType = (Class<?>) parameterizedType.getRawType();
	  
	        Type[] actualTypeArguments = (Type[]) parameterizedType.getActualTypeArguments();
	        TypeVariable<?>[] typeParameters = rawType.getTypeParameters();
	        for (int i = 0; i < actualTypeArguments.length; i++) {
	          resolvedTypes.put((Type) typeParameters[i], actualTypeArguments[i]);
	        }
	  
	        if (!rawType.equals(baseClass)) {
	          type = (Type) rawType.getGenericSuperclass();
	        }
	      }
	    }
	  
	    // finally, for each actual type argument provided to baseClass, determine (if possible)
	    // the raw class for that type argument.
	    Type[] actualTypeArguments;
	    if (type instanceof Class) {
	      actualTypeArguments = (Type[]) ((Class<?>) type).getTypeParameters();
	    }
	    else {
	      actualTypeArguments = (Type[]) ((ParameterizedType) type).getActualTypeArguments();
	    }
	    List<Class<?>> typeArgumentsAsClasses = new ArrayList<Class<?>>();
	    // resolve types by chasing down type variables.
	    for (Type baseType: actualTypeArguments) {
	      while (resolvedTypes.containsKey(baseType)) {
	        baseType = resolvedTypes.get(baseType);
	      }
	      typeArgumentsAsClasses.add(getClass(baseType));
	    }
	    return typeArgumentsAsClasses;
	  }
	  
}
