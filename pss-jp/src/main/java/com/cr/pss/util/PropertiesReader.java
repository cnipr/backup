package com.cnipr.pss.util;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

public class PropertiesReader extends PropertyPlaceholderConfigurer {
	@Override
	protected void processProperties(
			ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		String password = props.getProperty("jdbc.password");
		if (password != null) {
			StringUtils abc = new StringUtils();
			abc.setDesString(password); // 将要解密的密文传送给Encrypt.java进行解密计算。
			props.setProperty("jdbc.password", abc.getStrM());
		}
		super.processProperties(beanFactory, props);
	}
}
