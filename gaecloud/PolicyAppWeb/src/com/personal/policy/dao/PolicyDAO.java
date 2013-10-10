package com.personal.policy.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.google.appengine.api.datastore.KeyFactory;
import com.personal.policy.Claim;
import com.personal.policy.Policy;

public class PolicyDAO extends DAO {
	
	public static String createPolicy( Policy policy ) throws Exception {
		saveObject( policy );
		
		return  KeyFactory.keyToString(policy.getKey());
	}
	
	
	public static String createClaim( Claim claim ) throws Exception {
		saveObject( claim );
		
		return  KeyFactory.keyToString(claim.getKey());
	}
	
	public static List<Policy> getPolicies() {
		List<Policy> policies = new ArrayList<Policy>();
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query q = em.createQuery("select p from Policy p");

			try {
				for ( Object o : q.getResultList()) {
					policies.add( (Policy) o);
					em.detach(o);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			em.close();
		}
		return policies;
	}
	
	public static List<Claim> getClaimsForDependant( String policyid, String dependent) {
		List<Claim> claims = new ArrayList<Claim>();
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query q = em.createQuery("select c from Claim c where c.policyId ='"+policyid+"' and c.dependantName='"+dependent+"'");

			try {
				for ( Object o : q.getResultList()) {
					claims.add( (Claim) o);
					em.detach(o);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			em.close();
		}
		return claims;
	}
	
	public static Policy getPolicy ( String userKey ) {
		
		Policy p = null ; 
		
		EntityManager em = EMF.get().createEntityManager();
		try {
			Query q = em.createQuery("select p from Policy p where p.userId ='"+userKey+"'");
			
			try {
				p = ( Policy ) q.getSingleResult();
				em.detach(p);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			em.close();
		}
		return p;
	}
}
