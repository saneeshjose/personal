package com.personal.policy.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.personal.policy.User;

public class UserDAO extends DAO {
	
	public static String createUser( User user ) throws Exception {
		saveObject( user );
		
		return  KeyFactory.keyToString(user.getKey());
	}
	
	public static String updateUser( User user ) throws Exception {
		updateObject( user );
		
		return  KeyFactory.keyToString(user.getKey());
	}
	
	public static void delete() throws Exception {
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query q = em.createQuery("select u from User u");
			List<User> users = (List<User>)q.getResultList();
			for (int i = 0; i < users.size(); i++) {
				User taxExpert = users.get(i);
				em.remove(taxExpert);
			}
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		em.close();
	}
	
	public static List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query q = em.createQuery("select u from User u");

			try {
				for ( Object o : q.getResultList()) {
					users.add( (User) o);
					em.detach(o);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			em.close();
		}
		return users;
	}
	
	public static User getUser ( String userKey ) {
		
		User user = null ; 
		
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query q = em.createQuery("select u from User u where u.key ='"+userKey+"'");

			try {
				user = ( User ) q.getSingleResult();
				em.detach(user);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			em.close();
		}
		return user;
	}
	
	public static User getUserByEmail ( String email ) {
		
		User user = null ; 
		
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query q = em.createQuery("select u from User u where u.email ='"+email+"'");

			try {
				user = ( User ) q.getSingleResult();
				em.detach(user);
			} 
			catch (Exception e) {
				System.out.println("User does not exist for email '"+email+"'");
			}
		} finally {
			em.close();
		}
		return user;
	}
}
