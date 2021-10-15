package com.scanner;

import com.annotation.Component;
import com.filter.AnnotationTypeFilter;
import com.po.BeanDefinition;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: @zyz
 */
@Slf4j
public class BeanApplicationContext {


	/**
	 * 标记遇到File时是否循环扫描
	 */
	static final boolean recursive = true;
	/**
	 * 缓存: BeanName:BeanDefinition
	 */
	static final Map <String, BeanDefinition> beanDefinitionMap=new ConcurrentHashMap<> ();

	/**
	 * 缓存所有需要解析的注解
	 */
	static Set < Class < ? extends Annotation > > cachedAnnotationTypes;

	public BeanApplicationContext () {
		cachedAnnotationTypes = AnnotationTypeFilter.getAllAnnotationTypes ();
	}

	public static void scanAnnotations(List <Class<? extends BeanApplicationContext >> annotationTypes, String dir)
	{

		dir=dir.replace (".","/");
		ClassLoader classLoader=Thread.currentThread ().getContextClassLoader ();
		Enumeration < URL > dirs;
		try {
			 dirs = classLoader.getResources (dir);
			 while (dirs.hasMoreElements ())
			 {
			 	URL url=dirs.nextElement ();
			 	String protocol=url.getProtocol ();
			 	if ("file".equals (protocol))
			    {
			    	String filePath= URLDecoder.decode (url.getFile (),"UTF-8");
			    	log.debug (url.getFile ());
				    log.debug (filePath);
					findAndAddClassesInPackageByFile( annotationTypes,dir,filePath);
			    }
			 }

		} catch (IOException e) {
			log.debug ("[{}] doesn't  find",dir);
			throw new IllegalStateException (dir+" doesn't  find");
		}

	}

	private static void findAndAddClassesInPackageByFile (List <Class<? extends BeanApplicationContext >> annotationTypes, String packageName , String filePath) {
		File dir=new File (filePath);
		if (!dir.exists ()||! dir.isDirectory ())
		{
			log.error("path does not exist ："+filePath);
			throw new IllegalArgumentException("path does not exist ："+filePath);
		}
		File[] dirFiles=dir.listFiles (file->(recursive&&file.isDirectory ())||(file.getName ().endsWith (".class")));
		for (File file:dirFiles)
		{
			if (file.isDirectory ())
			{
				findAndAddClassesInPackageByFile (annotationTypes,packageName+"."+file.getName (),file.getAbsolutePath ());
			}else{
				String className=file.getName ().substring (0,file.getName ().length ()-6);
				try {
					Class<?> clazz=	Class.forName (packageName+"."+className);
					analyzeClass(clazz);
				} catch (ClassNotFoundException e) {
					throw new RuntimeException (e);
				}
			}
		}
	}

	/**
	 * 解析类的注解
	 * @param clazz
	 */
	private static void analyzeClass (Class<?> clazz) {
		Annotation[] annotations = clazz.getAnnotations ();
		for (Class < ? extends Annotation > annotation:cachedAnnotationTypes)
		{
			if (!clazz.isAnnotationPresent (annotation)) {
				continue;
			}
//			if (annotation instanceof Component.class)
//			{
//
//			}

		}
	}

	public static void loadClassOrInterface(List <Class<? extends BeanApplicationContext >> annotationTypes, String... paths)
	{
		for (String path:paths)
		{
			scanAnnotations (annotationTypes,path);
		}
	}



}
