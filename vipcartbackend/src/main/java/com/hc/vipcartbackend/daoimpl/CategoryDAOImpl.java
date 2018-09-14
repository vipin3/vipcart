package com.hc.vipcartbackend.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hc.vipcartbackend.dao.CategoryDAO;
import com.hc.vipcartbackend.dto.Category;

@Repository("categoryDAO")
@Transactional
public class CategoryDAOImpl implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	public List<Category> list() {
		String selectActiveCategory = "FROM Category WHERE active = :active";
		Query query = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (Exception e) {
			System.out.println("In CategoryDAOImpl error in getting new session");
			e.printStackTrace();
		}
		
		try {
			query = session.createQuery(selectActiveCategory);
		} catch (Exception e) {
			System.out.println("In CategoryDAOImpl -> while creating query");
			e.printStackTrace();
		}	
		query.setParameter("active", true);
		return query.getResultList();
	}
	
	@Override
	public Category get(int id) {
		return sessionFactory.getCurrentSession().get(Category.class, Integer.valueOf(id));
	}


	@Override
	@Transactional
	public boolean add(Category category) {
		try {
			sessionFactory.getCurrentSession().persist(category);	
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean update(Category category) {
		try {
			sessionFactory.getCurrentSession().update(category);	
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public boolean delete(Category category) {
		category.setActive(false);
		try {
			sessionFactory.getCurrentSession().update(category);	
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
