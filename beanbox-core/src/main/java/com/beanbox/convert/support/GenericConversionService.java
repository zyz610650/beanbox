package com.beanbox.convert.support;


import com.beanbox.convert.converter.*;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author: @zyz
 */
public class GenericConversionService implements ConversionService, ConverterRegistry {

	private Map <GenericConverter.ConvertiblePair, GenericConverter> convertersMap=new HashMap<> ();
	@Override
	public boolean canConvert (Class < ? > sourceType , Class < ? > targetType) {

		GenericConverter converter=getConverter (sourceType,targetType);

		return converter!=null;
	}

	@Override
	public < T > T convert (Object source , Class < T > targetType) {
		Class<?> sourceType=source.getClass ();
		GenericConverter converter=getConverter (sourceType,targetType);

		return (T) converter.convert (source,sourceType,targetType);
	}

	@Override
	public void addConverter (Converter < ?, ? > converter) {
		GenericConverter.ConvertiblePair typeInfo=getRequiredTypeInfo (converter);
		// converter 需要包装成适配器 才能统一放到缓存中
		ConverterAdapter converterAdapter=new ConverterAdapter (typeInfo,converter);
		for (GenericConverter.ConvertiblePair convertiblePair:converterAdapter.getConvertibleTypes ())
		{
			convertersMap.put (convertiblePair,converterAdapter);
		}

	}

	@Override
	public void addConverter (GenericConverter converter) {
		for (GenericConverter.ConvertiblePair convertiblePair:converter.getConvertibleTypes ())
		{
			convertersMap.put (convertiblePair,converter);
		}
	}

	@Override
	public void addConverterFactory (ConverterFactory < ?, ? > converterFactory) {
		GenericConverter.ConvertiblePair typeInfo=getRequiredTypeInfo (converterFactory);
		ConverterFactoryAdapter converterFactoryAdapter=new ConverterFactoryAdapter (typeInfo,converterFactory);
		for (GenericConverter.ConvertiblePair convertiblePair: converterFactoryAdapter.getConvertibleTypes ())
		{
			convertersMap.put(convertiblePair,converterFactoryAdapter);
		}
	}

	/**
	 * 获得对象的类型转换关系对
	 * @param obj
	 * @return
	 */
	private GenericConverter.ConvertiblePair getRequiredTypeInfo(Object obj)
	{
		Type[] types=obj.getClass ().getGenericInterfaces ();
		ParameterizedType parameterizedType= (ParameterizedType) types[0];
		Type[] actualTypeArguments=parameterizedType.getActualTypeArguments ();
		Class sourceType = (Class) actualTypeArguments[0];
		Class targetType = (Class) actualTypeArguments[1];
		return new GenericConverter.ConvertiblePair (sourceType,targetType);
	}

	/**
	 * 查找能将sourceType转换成targetType的转换器
	 * @param sourceType
	 * @param targetType
	 * @return
	 */
	protected GenericConverter getConverter(Class<?> sourceType, Class<?> targetType)
	{
		List<Class<?>> sourceCandidates=getClassHierarchy (sourceType);
		List<Class<?>> targetCandidates=getClassHierarchy (targetType);
		for (Class<?> sourceCandidate:sourceCandidates)
		{
			for (Class<?> targetCandidate : targetCandidates){
				//组成类型转换对
				GenericConverter.ConvertiblePair convertiblePair=new GenericConverter.ConvertiblePair (sourceCandidate,targetCandidate);
				//通过类型转换对到缓存中查找对应的转换器
				//如果找到各自可相互转换的父类 则尝试获得他们的转换器，否则得到Null
				GenericConverter converter=convertersMap.get (convertiblePair);
				if (converter!=null) {
					return converter;
				}
			}
		}
		return null;
	}

	/**
	 * 将clazz所有存在继承关系的父类都保存在List返回
	 * @param clazz
	 * @return
	 */
	private List<Class<?>> getClassHierarchy(Class<?> clazz){
		List<Class<?>> hierarchy=new ArrayList <> ();
		while (clazz != null)
		{
			hierarchy.add (clazz);
			clazz= clazz.getSuperclass ();
		}
		return hierarchy;
	}

	private final class ConverterAdapter implements GenericConverter{

		private final ConvertiblePair typeInfo;
		private final Converter<Object,Object> converter;

		private ConverterAdapter (ConvertiblePair typeInfo , Converter < ?, ? > converter) {
			this.typeInfo = typeInfo;
			this.converter = (Converter < Object, Object >) converter;
		}


		/**
		 * 返回的集合长度为1不会造成额外的空间浪费
		 * @return
		 */
		@Override
		public Set < ConvertiblePair > getConvertibleTypes () {
			return Collections.singleton (typeInfo);
		}

		@Override
		public Object convert (Object source , Class sourceType , Class targetType) {
			return converter.convert (source);
		}
	}

	private final class ConverterFactoryAdapter implements GenericConverter{
		private final ConvertiblePair typeInfo;

		private final ConverterFactory<Object,Object> converterFactory;

		private ConverterFactoryAdapter (ConvertiblePair typeInfo , ConverterFactory < ?, ? > converterFactory) {
			this.typeInfo = typeInfo;
			this.converterFactory = (ConverterFactory < Object, Object >) converterFactory;
		}

		@Override
		public Set < ConvertiblePair > getConvertibleTypes () {
			return Collections.singleton (typeInfo);
		}

		@Override
		public Object convert (Object source , Class sourceType , Class targetType) {
			return converterFactory.getConverter (targetType).convert (source);
		}
	}
}
