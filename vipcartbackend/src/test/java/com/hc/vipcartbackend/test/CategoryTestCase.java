package com.hc.vipcartbackend.test;

import static org.junit.Assert.assertEquals;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hc.vipcartbackend.dao.CategoryDAO;
import com.hc.vipcartbackend.dto.Category;

public class CategoryTestCase {
	private static AnnotationConfigApplicationContext context;
	private static CategoryDAO categoryDAO;
	private Category category;
	
	@BeforeClass
	public static void init() {
		context = new AnnotationConfigApplicationContext();
		context.scan("com.hc.vipcartbackend");
		context.refresh();
		categoryDAO = (CategoryDAO)context.getBean("categoryDAO");
	}
	
	@Ignore
	@Test
	public void testAddCategory() {
		category = new Category();
		category.setName("Laptop");
		category.setDescription("All types of Laptos available here");
		category.setImageURL("CAT_3.png");
		
		assertEquals("Successfully added a category inside the table!", true, categoryDAO.add(category));
	}
	
	@Ignore
	@Test
	public void testGetCategory() {
		category = categoryDAO.get(2);
		assertEquals("Successfully fetched a single category from the table!", "TV", category.getName());
	}
	
	@Ignore
	@Test
	public void testUpdateCategory() {
		category = categoryDAO.get(2);
		category.setName("TV");
		assertEquals("Successfully updated a single category in the table!", true, categoryDAO.update(category));
	}
	
	@Ignore
	@Test
	public void testDeleteCategory() {
		category = categoryDAO.get(4);
		assertEquals("Successfully deleted a single category from the table!", true, categoryDAO.delete(category));
	}
	
	@Ignore
	@Test
	public void testListCategory() {
	//	category = categoryDAO.get(4);
		assertEquals("Successfully fetched a list of category from the table!", 1, categoryDAO.list().size());
	}
	
	@Ignore
	@Test
	public void testCrudCategory() {
		category = new Category();
		category.setName("Laptop");
		category.setDescription("All types of Laptos available here");
		category.setImageURL("CAT_1.png");
		assertEquals("Successfully added a category inside the table!", true, categoryDAO.add(category));

		category = new Category();
		category.setName("Television");
		category.setDescription("All types of Television available here");
		category.setImageURL("CAT_2.png");
		assertEquals("Successfully added a category inside the table!", true, categoryDAO.add(category));
	
		
		//fetching and updating the category.
		category = categoryDAO.get(2);
		category.setName("TV");
		assertEquals("Successfully updated a single category in the table!", true, categoryDAO.update(category));

		// delete the category
		assertEquals("Successfully deleted a single category from the table!", true, categoryDAO.delete(category));
		
		// fetch list of categories
		assertEquals("Successfully fetched a list of category from the table!", 1, categoryDAO.list().size());
	}
	
	@Ignore
	@Test
	public void getValues() {
		boolean gotResult = false;
		try{  
			Class.forName("org.h2.Driver");  
			Connection con = DriverManager.getConnection(  
			"jdbc:h2:tcp://localhost/~/vipcart","vip","");  
			//here sonoo is database name, root is username and password  
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from category");  
			while(rs.next())  
			System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));  
			con.close();  
			gotResult = true;
		}catch(Exception e){
			System.out.println(e);
		}  
		
		assertEquals(true, gotResult);
	}
	
}
