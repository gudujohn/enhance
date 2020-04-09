package com.enhance.web.util;

import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.enhance.common.util.Detect;
import com.enhance.common.util.InternalAssertion;
import com.enhance.common.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SettingsLoadUtil {

	public static void loadSettings(ApplicationContext applicationContext) {
		log.info("settings load begin.");
		StopWatch watch = new StopWatch();
		watch.start();

		Environment environment = applicationContext.getEnvironment();
		loadAllSettings(environment);

		watch.stop();
		log.info("settings load end. cost {} ms", watch.getTotalTimeMillis());
	}

	private static void loadAllSettings(Environment environment) {
		String[] settingKeys = getAllSettingsKeys(environment);
		load(environment, settingKeys);
	}

	private static void load(Environment environment, String[] settingKeys, String... filter) {
		try {
			if (Detect.notEmpty(settingKeys)) {
				for (String settingKey : settingKeys) {
					if (Arrays.asList(filter).contains(settingKey)) {
						continue;
					}
					String settingValue = environment.getProperty(settingKey);
					System.setProperty(settingKey, settingValue);
				}
			}
		} catch (Exception e) {
			log.error("load settings error: " + e.getMessage(), e);
			throw e;
		}
	}

	private static String[] getAllSettingsKeys(Environment environment) {
		// 1:加载配置文件
		Resource app = new ClassPathResource("application.yml");
		String[] activeProfiles = environment.getActiveProfiles();
		Resource appEnv = null;
		Resource configApp = null;
		Resource configAppEnv = null;
		if (Detect.notEmpty(activeProfiles)) {
			appEnv = new ClassPathResource("application-" + activeProfiles[0] + ".yml");
			configApp = new ClassPathResource("config/application.yml");
			configAppEnv = new ClassPathResource("config/application-" + activeProfiles[0] + ".yml");
		}
		YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
		// 2:将加载的配置文件交给 YamlPropertiesFactoryBean
		Resource[] resources = { app };
		resources = Detect.notNull(appEnv) ? ArrayUtils.add(resources, appEnv) : resources;
		resources = Detect.notNull(configApp) ? ArrayUtils.add(resources, configApp) : resources;
		resources = Detect.notNull(configAppEnv) ? ArrayUtils.add(resources, configAppEnv) : resources;
		yamlPropertiesFactoryBean.setResources(resources);
		// 3：将yml转换成 key：val
		Properties properties = yamlPropertiesFactoryBean.getObject();
		// 4: 获取所有的key
		InternalAssertion.notNull(properties, "读取系统启动yml配置出错!!!");
		String[] values = ArrayUtils.EMPTY_STRING_ARRAY;
		for (Object key : properties.keySet()) {
			String keyStr = Objects.toString(key);
			values = ArrayUtils.add(values, keyStr);
		}

		return values;
	}

}
