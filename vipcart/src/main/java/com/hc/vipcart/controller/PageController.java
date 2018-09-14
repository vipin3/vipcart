package com.hc.vipcart.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hc.vipcart.exception.ProductNotFoundException;
import com.hc.vipcartbackend.dao.CategoryDAO;
import com.hc.vipcartbackend.dao.ProductDAO;
import com.hc.vipcartbackend.dto.Category;
import com.hc.vipcartbackend.dto.Product;

@Controller
public class PageController {

	private static final Logger logger = LoggerFactory.getLogger(PageController.class);
	
	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private ProductDAO productDao;
	
	@RequestMapping(value = {"/","/index","/home"})
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("page");
		logger.info("Inside PageController index method - INFO");
		logger.debug("Inside PageController index method - DEBUG");
		
		mv.addObject("userClickHome", true);
		mv.addObject("title", "Home");
		List<Category> categories = null;
		try {
			categories = categoryDAO.list();
		} catch (Exception e) {
			System.out.println("In PageController");
			e.printStackTrace();
		}	
		mv.addObject("categories", categories);
		return mv;
	}
	
	@RequestMapping(value = {"/about"})
	public ModelAndView about() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("userClickAbout", true);
		mv.addObject("title", "About Us"); 
		return mv;
	}
	
	@RequestMapping(value = {"/contact"})
	public ModelAndView contact() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("userClickContact", true);
		mv.addObject("title", "Contact Us"); 
		return mv;
	}
	
	/*
	 * Method to load all the products and based on category
	 */
	@RequestMapping(value = {"/show/all/products",})
	public ModelAndView showAllPro() {
		ModelAndView mv = new ModelAndView("page");
		mv.addObject("userClickAllProducts", true);
		mv.addObject("title", "All Products");
		mv.addObject("categories", categoryDAO.list());
		return mv;
	}
	
	@RequestMapping(value = {"/show/category/{id}/products"})
	public ModelAndView showCategoryProducts(@PathVariable(name="id") int id) {
		ModelAndView mv = new ModelAndView("page");
		
		
		mv.addObject("userClickCategoryProducts", true);
		// categoryDAO to fetch a single category
		Category category = null;
		category = categoryDAO.get(id);		
		mv.addObject("category", category);
		mv.addObject("title", category.getName());
		mv.addObject("categories", categoryDAO.list());
		
		return mv;
	}
	
	/*
	 * Viewing a single product
	 */
	 @RequestMapping(value = "/show/{id}/product")
	 public ModelAndView showSingleProduct(@PathVariable int id) throws ProductNotFoundException {
		 ModelAndView mv = new ModelAndView("page");
		 Product product = productDao.get(id);
		 
		 if(product == null)
			 throw new ProductNotFoundException();
		 
		 // update the view count
		 product.setViews(product.getViews() +1);
		 productDao.update(product);
		 
		 mv.addObject("title", product.getName());
		 mv.addObject("product", product);
		 mv.addObject("userClickShowProduct", true);
		 
		 return mv;
	 }
	 
	
}
