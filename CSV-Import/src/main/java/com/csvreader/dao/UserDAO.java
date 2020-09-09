package com.csvreader.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.HibernateTemplate;

import com.csvreader.dto.User;

public class UserDAO implements Serializable{
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public UserDAO() {
		super();
	}
	
	@Transactional
	public boolean insertUser(List<User> ul)
	{
		boolean b = false;
		Session s = sessionFactory.getCurrentSession();
		
		try {
			for(User u : ul) { 
			s.save(u);
			}
			b = true;
		} 
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return b;
	}
	
	public User createUser(String csvline[]) {
		User u = new User();
		
		int id = Integer.parseInt(csvline[0].trim());
		String name = csvline[1].trim();
		String email = csvline[2].trim();
		String phone = csvline[3].trim();
		
		u.setId(id);
		u.setName(name);
		u.setEmail(email);
		u.setPhone(phone);
		
		return u;
	}
	
	public List<User> insertfile(File file) {
		List<User> allUsers = null;
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			
			String line = "";
			String cvsSplitBy = ",";
			
			allUsers = new ArrayList<User>();
			while((line = br.readLine()) != null) {
				String[] csvLine = line.split(cvsSplitBy);
				User u = createUser(csvLine);
				allUsers.add(u);
				}
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		return allUsers;
	}
}
