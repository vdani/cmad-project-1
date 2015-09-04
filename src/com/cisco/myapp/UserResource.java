package com.cisco.myapp;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

//import org.glassfish.jersey.media.multipart.MultiPartFeature
//import org.glassfish.jersey.media.multipart.FormDataParam;


@Path("/users")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class UserResource {
	
	private Session ses;
	private Transaction tx;
	
	public UserResource() {
		this.ses = HibernateUtil.currentSession();
		this.tx=this.ses.beginTransaction();
	}

	@GET	
	public List<User> getUsers(@QueryParam("ageFrom") Integer ageFrom,
			@QueryParam("ageTo") Integer ageTo) throws Exception {
//			Session ses = HibernateUtil.currentSession();
			try {
				//return ses.createQuery("select u from User u where u.age>"+ageFrom+" and u.age<"+ageTo).list();
				return ses.createQuery("select u from User u where u.id=1").list();
			} finally {
				//ses.close();
				HibernateUtil.closeSession();
			}
	}
	
	
	@POST
	public User createUser(User u) throws Exception{
//		Session ses = HibernateUtil.currentSession();
//		Transaction tx=ses.beginTransaction();
		Integer id=null;
		try{
		id= (Integer) ses.save(u);
		u.setId(id); 
		tx.commit();
		// return u;
		} catch (Exception e){
		     e.printStackTrace();
		     tx.rollback();
		} finally {
			//ses.close();
			HibernateUtil.closeSession();
		}
		return u;
	}
	
	@POST
	@Path("/validate")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public User validateUser(User u1) throws Exception{
//		Session ses = HibernateUtil.currentSession();
//		Transaction tx=ses.beginTransaction();
		try{
			String em = u1.getEmailId();
			String hql = "select u from User u where u.emailId= '" + em + "'";
			List uList = ses.createQuery(hql).list();
			Integer aList = uList.size(); 			
			if(aList > 0) {
				String hql1 = "select u from User u where u.emailId= '" + em + "'";
				Query q = ses.createQuery(hql1);
				List uidO = q.list();
				User u2 = (User) uidO.get(0);
				Integer usid = u2.getId(); 
				u1.setId(usid);
				u1.setName(em);	
				return u1;
			} else {
				return null;
			}
		} catch (Exception e){
		    e.printStackTrace();
		}  finally {
			//ses.close();
			HibernateUtil.closeSession();
		}	
		return null;
	
	}

}


