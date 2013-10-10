package com.personal.policy.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.personal.policy.Provider;

public class ProviderDAO extends DAO {
	
	public static String createProvider( Provider provider ) throws Exception{
		saveObject( provider );
		
		return  KeyFactory.keyToString(provider.getKey());
	}
	
	
	public static List<Provider> getProviders( String name, String address) {

		List<Provider> providers = new ArrayList<Provider>();
		EntityManager em = EMF.get().createEntityManager();
		
		if ( name == null) name="";
		if ( address == null ) address="";
		
		try {
			Query q = em.createQuery("select p from Provider p");

			try {
				for ( Object o : q.getResultList()) {
					
					Provider p = (Provider) o;
					
					if ( p.getName().toLowerCase().contains(name.toLowerCase()) 
							&& p.getAddress().toLowerCase().contains(address.toLowerCase()) ) {
						providers.add( p);
						em.detach(o);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			em.close();
		}
		return providers;
	}
	
	public static List<Provider> getProviders() {

		List<Provider> providers = new ArrayList<Provider>();
		EntityManager em = EMF.get().createEntityManager();
		
		try {
			Query q = em.createQuery("select p from Provider p");

			try {
				for ( Object o : q.getResultList()) {
					
					Provider p = (Provider) o;
					
					providers.add( p);
					em.detach(o);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			em.close();
		}
		return providers;
	}
}
