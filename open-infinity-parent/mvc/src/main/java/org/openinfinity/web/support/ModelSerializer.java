package org.openinfinity.web.support;

import java.lang.reflect.Field;

import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

public class ModelSerializer<T> {
	
	public void serializeScript(final T runtimeObject, final String[] exclusionPaths, final String[] hiddenFields) {
		final StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/x-handlebars\">");
		ReflectionUtils.doWithFields(runtimeObject.getClass(),new FieldCallback() {		
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				for (String exclusion : exclusionPaths) {
					if (field.getName().equalsIgnoreCase(exclusion)) {
						if (! field.isAccessible()) {
							field.setAccessible(Boolean.TRUE);
						}
						builder.append("{{#view App.").append(runtimeObject.getClass().getName().replace("Model", "View")).append("}}");
						builder.append(field.getName()).append(": {{").append(field.getName()).append("}}");
						builder.append("{{/view}}");
					}
				}
			}			
		});
		builder.append("</script>");
	}
	
	public void serializeTable(final T runtimeObject, final String[] exclusionPaths, final String[] hiddenFields) {
		final StringBuilder builder = new StringBuilder();
		builder.append("<script type=\"text/x-handlebars\">");
		ReflectionUtils.doWithFields(runtimeObject.getClass(),new FieldCallback() {		
			public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
				for (String exclusion : exclusionPaths) {
					if (field.getName().equalsIgnoreCase(exclusion)) {
						if (! field.isAccessible()) {
							field.setAccessible(Boolean.TRUE);
						}
						builder.append("{{#view App.").append(runtimeObject.getClass().getName().replace("Model", "View")).append("}}");
						builder.append(field.getName()).append(": {{").append(field.getName()).append("}}");
						builder.append("{{/view}}");
					}
				}
			}			
		});
		builder.append("</script>");
	}

}