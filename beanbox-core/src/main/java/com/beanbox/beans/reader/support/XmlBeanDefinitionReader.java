package com.beanbox.beans.reader.support;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.beanbox.beans.annotation.scanner.ClassPathBeanDefinitionScanner;
import com.beanbox.beans.factory.ConfigurableBeanFactory;
import com.beanbox.beans.po.BeanDefinition;
import com.beanbox.beans.po.BeanReference;
import com.beanbox.beans.po.PropertyValue;
import com.beanbox.beans.registry.BeanDefinitionRegistry;
import com.beanbox.beans.sessions.PropertyValueSession;
import com.beanbox.io.loader.ResourceLoader;
import com.beanbox.io.loader.support.DefaultResourceLoader;
import com.beanbox.io.resource.Resource;
import com.beanbox.utils.ClassUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

/**
 * @author: @zyz
 */
@Slf4j
public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {
	public XmlBeanDefinitionReader (BeanDefinitionRegistry registry) {
		super (registry);
	}

	public XmlBeanDefinitionReader (BeanDefinitionRegistry registry , ResourceLoader resourceLoader) {
		super (registry , resourceLoader);
	}

	@SneakyThrows
	@Override
	public void loadBeanDefinitions (Resource resource) {
		doLoadBeanDefinitions (resource.getInputStream ());
	}

	@SneakyThrows
	@Override
	public void loadBeanDefinitions (Resource... resources) {
		for (Resource resource:resources)
		{
			loadBeanDefinitions (resource);
		}
	}
	@SneakyThrows
	@Override
	public void loadBeanDefinitions (String... locations) {
		for (String location:locations)
		{
			ResourceLoader resourceLoader=new DefaultResourceLoader ();
			Resource resource = resourceLoader.getResource (location);
			loadBeanDefinitions (resource);
		}
	}

	/**
	 * 根据XML文件流处理里面定义的Bean为BeanDefinition对象并添加到容器中
	 * @param inputStream
	 */
	protected void doLoadBeanDefinitions(InputStream inputStream)
	{
		BeanDefinitionRegistry registry = getRegistry ();
		Document doc = XmlUtil.readXML (inputStream);
		Element root=doc.getDocumentElement ();

		//加载带有Bean标签的类
		loadBeanElement(root,registry);

		//加载带有BeanScan标签的获得需要扫描的路径 在路径下扫描注解
		loadBeanScan(root,registry);


	}

	/**
	 * 解析 context:bean-scan 标签下的内容 用于组装BeanDefinition
	 * @param root
	 * @param registry
	 */
	protected void loadBeanScan(Element root, BeanDefinitionRegistry registry)
	{
		NodeList beanScanNodeList = root.getElementsByTagName ("bean-scan");
		String basePackage=null;
		for (int i=0;i<beanScanNodeList.getLength ();i++)
		{
			Element element= (Element) beanScanNodeList.item (i);
			 basePackage = element.getAttribute ("base-package");

		}

		if (basePackage==null||StrUtil.isEmpty (basePackage))
		{
			log.warn ("base-package is empty ");
			return;
		}
		//扫描basePackage包下的注解
		scanPackage (basePackage);
	}

	/**
	 * 扫描并加载包下的BeanDefinition
	 * @param scnPath
	 */
	private void scanPackage(String scnPath){
		String[] basePackages=StrUtil.splitToArray (scnPath,',');
		ClassPathBeanDefinitionScanner scanner=new ClassPathBeanDefinitionScanner (getRegistry ());
		scanner.doScan (basePackages);
	}
	/**
	 * 解析XML中Bean标签下的内容
	 * @param root XML中根节点元素
	 * @param registry
	 */
	protected void loadBeanElement(Element root,BeanDefinitionRegistry registry)
	{

		NodeList beanNodeList = root.getElementsByTagName ("bean");
		for (int i=0;i<beanNodeList.getLength ();i++)
		{
			Element element= (Element) beanNodeList.item (i);
			NodeList childNodes = element.getChildNodes ();
			String id=element.getAttribute ("id");
			String name=element.getAttribute ("name");
			String className=element.getAttribute ("class");
			String initMethodName=element.getAttribute ("init-method");
			String destroyMethodName=element.getAttribute ("destory-method");
			String beanScope=element.getAttribute ("scope");
			try {
				String beanName=id;
				if (beanName==null||beanName.equals (""))  beanName=name;

				Class < ? > clazz=null;

				clazz = ClassUtils.getDefaultClassLoader ().loadClass (className);

				if (beanName==null||beanName.equals (""))
					beanName=StrUtil.lowerFirst (clazz.getSimpleName ());
				//属性注入
				PropertyValueSession propertyValueSession = doLoadByPropertyValue (element);
				//id的优先级高于name
				BeanDefinition beanDefinition=new BeanDefinition (clazz,propertyValueSession);

				//注入初始化函数
				if (initMethodName!=null&&!initMethodName.equals (""))
				{
					beanDefinition.setInitMethodName (initMethodName);
				}

				//注入销毁函数
				if (destroyMethodName!=null&&!destroyMethodName.equals (""))
				{
					beanDefinition.setDestroyMethodName (destroyMethodName);
				}

				// 设置对象单例/原型
				if (StrUtil.isNotEmpty (beanScope))
				{
					if (beanScope.equals (ConfigurableBeanFactory.SCOPE_PROTOTYPE)||beanScope.equals (ConfigurableBeanFactory.SCOPE_SINGLETON))
						beanDefinition.setScope (beanScope);
					else  throw new BeanException ("property [scope] exception :"+ beanScope);
				}
				//判重
				if(registry.containsBeanDefinition (beanName))
				{
					throw new BeanException ("Duplicate beanName[ "+beanName+"] is not allowed");
				}
				//到beanDefition容器中
				registry.registerBeanDefinition (beanName,beanDefinition);

			} catch (ClassNotFoundException e) {

				throw new BeanException (className+ "can not be instantiated",e);
			}
		}
	}

	/**
	 * 处理bean的set方法注入方式
	 * @param element
	 * @return
	 */
	private PropertyValueSession doLoadByPropertyValue(Element element )
	{
		NodeList propertyList = element.getElementsByTagName ("property");
		PropertyValueSession propertyValueSession=new PropertyValueSession ();

		for (int i=0;i<propertyList.getLength ();i++)
		{
			Element e = (Element)propertyList.item (i);
			String name = e.getAttribute ("name");
			String value = e.getAttribute ("value");
			String ref=e.getAttribute ("ref");
			Object o=null;
			if (StrUtil.isNotEmpty (ref))
			{
				 o = new BeanReference (ref);
			}else{
				o=value;
			}
			propertyValueSession.addPropertyValue (new PropertyValue (name,o));
		}
		return propertyValueSession;
	}
	
}
