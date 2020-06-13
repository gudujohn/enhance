package org.enhance.web.util;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ArrayUtils;
import org.enhance.common.util.Detect;
import org.enhance.common.util.InternalAssertion;
import org.enhance.common.util.StopWatch;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

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
					log.info("loading setting:{} ---> {}", settingKey, settingValue);
				}
			}
		} catch (Exception e) {
			log.error("load settings error: " + e.getMessage(), e);
			throw e;
		}
	}

	private static String[] getAllSettingsKeys(Environment environment) {
		// 1:加载配置文件
		String[] values = ArrayUtils.EMPTY_STRING_ARRAY;

		Resource app = new ClassPathResource("application.yml");
		Resource configApp = new ClassPathResource("config/application.yml");
		String[] currentValues = getAllSettingsKeys(app);
		values = Detect.notEmpty(currentValues) ? ArrayUtils.addAll(values, currentValues) : values;
		currentValues = getAllSettingsKeys(configApp);
		values = Detect.notEmpty(currentValues) ? ArrayUtils.addAll(values, currentValues) : values;

		String[] activeProfiles = environment.getActiveProfiles();
		if (Detect.notEmpty(activeProfiles)) {
			Resource appEnv = null;
			Resource configAppEnv = null;
			appEnv = new ClassPathResource("application-" + activeProfiles[0] + ".yml");
			configAppEnv = new ClassPathResource("config/application-" + activeProfiles[0] + ".yml");
			currentValues = getAllSettingsKeys(appEnv);
			values = Detect.notEmpty(currentValues) ? ArrayUtils.addAll(values, currentValues) : values;
			currentValues = getAllSettingsKeys(configAppEnv);
			values = Detect.notEmpty(currentValues) ? ArrayUtils.addAll(values, currentValues) : values;
		}
		if (Detect.notEmpty(values)) {
			List<String> valueList = Arrays.asList(values);
			valueList = valueList.stream().distinct().collect(Collectors.toList());
			values = new String[valueList.size()];
			valueList.toArray(values);
		}
		return values;
	}

	private static String[] getAllSettingsKeys(Resource resource) {
		// 1:将加载的配置文件交给 YamlPropertiesFactoryBean
		Resource[] resources = {};
		resources = ArrayUtils.add(resources, resource);
		YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
		yamlPropertiesFactoryBean.setResources(resources);
		// 2：将yml转换成 key：val
		String[] values = ArrayUtils.EMPTY_STRING_ARRAY;
		try {
			Properties properties = yamlPropertiesFactoryBean.getObject();
			// 3: 获取所有的key
			InternalAssertion.notNull(properties, "读取系统启动yml配置出错!!!");
			for (Object key : properties.keySet()) {
				String keyStr = Objects.toString(key);
				values = ArrayUtils.add(values, keyStr);
			}
		} catch (Exception e) {
			if (!e.getMessage().contains(FileNotFoundException.class.getName())) {
				log.error(e.getMessage(), e);
			}
		} finally {
			return values;
		}
	}

}
