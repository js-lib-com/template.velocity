package com.jslib.template.velocity;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import js.util.Strings;
import junit.framework.TestCase;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class VelocityConceptInception extends TestCase {
	private VelocityEngine engine;
	private VelocityContext context;

	@Override
	protected void setUp() throws Exception {
		engine = new VelocityEngine();
		engine.init();
		context = new VelocityContext();
	}

	public void testStringVariable() throws IOException {
		String template = "Hello ${user}!";
		context.put("user", "John Doe");

		assertEquals("Hello John Doe!", (evaluate(template)));
		assertEquals("Hello John Doe!", (merge(template)));
	}

	public void testStringArray() throws IOException {
		String template = "Hello ${user[0]}!";
		context.put("user", new String[] { "Baby Doe" });

		assertEquals("Hello Baby Doe!", (evaluate(template)));
		assertEquals("Hello Baby Doe!", (merge(template)));
	}

	public void testStringList() throws IOException {
		String template = "Hello ${user[0]}!";
		context.put("user", Arrays.asList("Lady Doe"));

		assertEquals("Hello Lady Doe!", (evaluate(template)));
		assertEquals("Hello Lady Doe!", (merge(template)));
	}

	public void testStringMap() throws IOException {
		String template = "Hello ${user.name}!";
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", "Papa Doe");
		context.put("user", map);

		assertEquals("Hello Papa Doe!", (evaluate(template)));
		assertEquals("Hello Papa Doe!", (merge(template)));
	}

	public void testObject() throws IOException {
		String template = "Hello ${user.name}!";
		context.put("user", new User("Jane Doe"));

		assertEquals("Hello Jane Doe!", (evaluate(template)));
		assertEquals("Hello Jane Doe!", (merge(template)));
	}

	public void testObjectArray() throws IOException {
		String template = "Hello ${user[0].name}!";
		context.put("user", new User[] { new User("Jane Doe") });

		assertEquals("Hello Jane Doe!", (evaluate(template)));
		assertEquals("Hello Jane Doe!", (merge(template)));
	}

	public void testObjectList() throws IOException {
		String template = "Hello ${user[0].name}!";
		context.put("user", Arrays.asList(new User("Mama Bear")));

		assertEquals("Hello Mama Bear!", (evaluate(template)));
		assertEquals("Hello Mama Bear!", (merge(template)));
	}

	public void testObjectMap() throws IOException {
		String template = "Hello ${account.user.name}!";
		Map<String, User> map = new HashMap<String, User>();
		map.put("user", new User("Papa Bear"));
		context.put("account", map);

		assertEquals("Hello Papa Bear!", (evaluate(template)));
		assertEquals("Hello Papa Bear!", (merge(template)));
	}

	public void testInnerObject() throws IOException {
		String template = "Hello ${account.user.name}!";
		context.put("account", new Account(new User("Jane Doe")));

		assertEquals("Hello Jane Doe!", (evaluate(template)));
		assertEquals("Hello Jane Doe!", (merge(template)));
	}

	public void testObjectPropertyPath() throws IOException {
		String template = "Hello ${account.user.name}!";

		VelocityContext userContext = new VelocityContext();
		userContext.put("name", "Baby Bear");
		
		VelocityContext accountContext = new VelocityContext();
		accountContext.put("user", userContext);
		
		context.put("account", accountContext);

		assertEquals("Hello Baby Bear!", (evaluate(template)));
		assertEquals("Hello Baby Bear!", (merge(template)));
	}

	private String evaluate(String templateString) throws IOException {
		Writer writer = new StringWriter();
		engine.evaluate(context, writer, "test", templateString);
		return writer.toString();
	}

	private String merge(String templateString) throws IOException {
		File file = new File("fixture/template.tmp");
		file.deleteOnExit();
		Strings.save(templateString, file);

		Writer writer = new StringWriter();
		Template template = engine.getTemplate(file.getPath());
		template.merge(context, writer);
		return writer.toString();
	}

	public static class User {
		private String name;

		public User(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	public static class Account {
		private User user;

		public Account(User user) {
			super();
			this.user = user;
		}

		public User getUser() {
			return user;
		}
	}
}
