//package com.beanbox.scanner;
//
//import com.beanbox.annotation.Autowired;

//import com.beanbox.annotation.Scope;
//import com.beanbox.enums.ScopeEnum;
//import com.beanbox.filter.AnnotationTypeFilter;
//import com.beanbox.po.BeanDefinition;
//import com.beanbox.utils.ClassUtils;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.File;
//import java.io.IOException;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Field;
//import java.net.URL;
//import java.net.URLDecoder;
//import java.util.Enumeration;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//
///**
// * @author: @zyz
// */
//@Slf4j
//public class BeanApplicationContext {
//
//
//	/**
//	 * 标记遇到File时是否循环扫描
//	 */
//	static final boolean recursive = true;
//	/**
//	 * 缓存: BeanName:BeanDefinition
//	 * 保存所有注入到IOC的类 单例类专门由其它的Map保存
//	 */
//	static final Map <String, BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<> ();
//
//	/**
//	 * 缓存所有需要解析的注解
//	 */
//	static Set < Class < ? extends Annotation > > cachedAnnotationTypeSet;
//
//	/**
//	 * 缓存所有单例Beans
//	 */
//	static Map<String,Object> cachedSingletonBeanMap=new ConcurrentHashMap <> ();
//
//	public BeanApplicationContext () {
//		cachedAnnotationTypeSet = AnnotationTypeFilter.getAllAnnotationTypes ();
//	}
//
//	public static void scanAnnotations(List <Class<? extends BeanApplicationContext >> annotationTypes, String dir)
//	{
//
//		dir=dir.replace (".","/");
//		ClassLoader classLoader=Thread.currentThread ().getContextClassLoader ();
//		Enumeration < URL > dirs;
//		try {
//			 dirs = classLoader.getResources (dir);
//			 while (dirs.hasMoreElements ())
//			 {
//			 	URL url=dirs.nextElement ();
//			 	String protocol=url.getProtocol ();
//			 	if ("file".equals (protocol))
//			    {
//			    	String filePath= URLDecoder.decode (url.getFile (),"UTF-8");
//			    	log.debug (url.getFile ());
//				    log.debug (filePath);
//					findAndAddClassesInPackageByFile( annotationTypes,dir,filePath);
//			    }
//			 }
//
//		} catch (IOException e) {
//			log.debug ("[{}] doesn't  find",dir);
//			throw new IllegalStateException (dir+" doesn't  find");
//		}
//
//	}
//
//	private static void findAndAddClassesInPackageByFile (List < Class < ? extends BeanApplicationContext > > annotationTypes , String packageName , String filePath) {
//		File dir=new File (filePath);
//		if (!dir.exists ()||! dir.isDirectory ())
//		{
//			log.error("path does not exist ："+filePath);
//			throw new IllegalArgumentException("path does not exist ："+filePath);
//		}
//		File[] dirFiles=dir.listFiles (file->(recursive&&file.isDirectory ())||(file.getName ().endsWith (".class")));
//		for (File file:dirFiles)
//		{
//			if (file.isDirectory ())
//			{
//				findAndAddClassesInPackageByFile (annotationTypes,packageName+"."+file.getName (),file.getAbsolutePath ());
//			}else{
//				String className=file.getName ().substring (0,file.getName ().length ()-6);
//				try {
//					Class<?> clazz=	Class.forName (packageName+"."+className);
//					analyzeClass(clazz);
//				} catch (ClassNotFoundException e) {
//					throw new RuntimeException (e);
//				}
//			}
//		}
//	}
//
//	/**
//	 * 解析类的注解
//	 * beanName 首字母小写
//	 * @param clazz
//	 */
//	private static void analyzeClass (Class < ? > clazz) {
//
//		Annotation[] annotations = clazz.getAnnotations ();
//		for (Class < ? extends Annotation > annotation:cachedAnnotationTypeSet)
//		{
//			//处理类上的注解
//			if (!clazz.isAnnotationPresent (annotation)) {
//				continue;
//			}
//			if (annotation == ComponentScan.class) continue;
//			// 分析所有需要注入的注解
//			// 这样写是为框架使用者自定义注解留了接口
//			ScopeEnum scopeType=ScopeEnum.SINGLETON;
//			if (clazz.isAnnotationPresent (Scope.class))
//			{
//				Scope scopeAnnotation = clazz.getAnnotation (Scope.class);
//				scopeType = scopeAnnotation.value ();
//			}
//			// beanName首字母小写
//			String beanName= ClassUtils.getBeanName (clazz);
//			beanDefinitionMap.put (beanName,new BeanDefinition (clazz,scopeType));
//
//			if (scopeType==ScopeEnum.SINGLETON)
//			{
//
//			}
//		}
//	}
//
//	/**
//	 * 依赖注入
//	 * @param beanName
//	 * @param clazz
//	 */
//	public void injectBean(String beanName,Class<?> clazz)
//	{
//
//		//反射创建类
//		Object instance=ClassUtils.createBean (clazz);
//
//		Field[] declaredFields = clazz.getDeclaredFields ();
//		//处理方法上的注解
//		for(Field field: declaredFields)
//		{
//			// 处理预留给用户自定义类似于@Autowired的注解
//			Annotation[] annotations = field.getAnnotations ();
//			if (field.isAnnotationPresent (Autowired.class)||cachedAnnotationTypeSet.contains (annotations[0]))
//			{
//				Class <?> aClass = field.getType ();
//				String fieldBeanName=ClassUtils.getBeanName (aClass);
//				Object filedObj=getBean (fieldBeanName);
//				if (filedObj==null)
//				{
//					// 提前扫描
//					analyzeClass(aClass);
//
//				}
//				field.setAccessible (true);
////				field.set (instance,);
//
//			}
//		}
//
//	}
//	public  void loadClassOrInterface(List <Class<? extends BeanApplicationContext >> annotationTypes, String... paths)
//	{
//		for (String path:paths)
//		{
//			scanAnnotations (annotationTypes,path);
//		}
//	}
//
//	public Object getBean(String beanName)
//	{
//		BeanDefinition beanDefinition = beanDefinitionMap.get (beanName);
//		if (beanDefinition==null)
//		{
//			//出现需要注入的类还没有加载到IOC的情况
//			// 不能这样写  会出现循环依赖
//			// -PPPPPPPPPPPPP
//			analyzeClass (beanDefinition.getClazz ());
//
//			//throw new IllegalStateException (aClass.getCanonicalName ()+" cannot be injected without @Autowired or custom annotations annotation");
//			return null;
//		}
//		//单例
//	   	if (beanDefinition.getScope ()==ScopeEnum.SINGLETON)
//			return cachedSingletonBeanMap.get (beanName);
//		else{
//			// 原型
//			return ClassUtils.createBean (beanDefinition.getClazz ());
//		}
//
//	}
//
//
//
//}
