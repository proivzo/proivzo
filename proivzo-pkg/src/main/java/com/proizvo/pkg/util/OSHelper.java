package com.proizvo.pkg.util;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.reflect.FieldUtils;

public class OSHelper {

	public static List<String> detectOS() {
		try {
			List<String> oss = new LinkedList<>();
			Object arch = FieldUtils.readStaticField(SystemUtils.class,
					"OS_ARCH");
			String prefix = "IS_OS_";
			for (Field field : FieldUtils.getAllFields(SystemUtils.class)) {
				if (!field.getName().startsWith(prefix))
					continue;
				boolean value = (boolean) FieldUtils.readStaticField(field);
				if (!value)
					continue;
				String simple = field.getName().replace(prefix, "");
				oss.add((simple + "_" + arch).toLowerCase());
				oss.add(simple.toLowerCase());
			}
			return oss;
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}