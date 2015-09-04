package com.cisco.myapp;

import java.io.InputStream;
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

@Path("/response")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class ResponseResource {
	
	public ResponseResource() {
		//this.ses = HibernateUtil.currentSession();
		//this.tx=this.ses.beginTransaction();
	}
	
	@POST
	public Response postResponse(Response r) throws Exception{
		Session ses = HibernateUtil.currentSession();
		Transaction tx=ses.beginTransaction();
		Integer id=1;
		try{
			r.setrid(id);
			ses.save(r);
			tx.commit();
		} catch (Exception e){
		     e.printStackTrace();
		     tx.rollback();
		} finally {
			//ses.close();
			HibernateUtil.closeSession();
		}
		return r;
	}
	
	@GET
	@Path("/responses")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Response> geResponses(@QueryParam("sesqid") Integer sesqid) throws Exception {
		Session ses = HibernateUtil.currentSession();
		Transaction tx=ses.beginTransaction();
		try {
			return ses.createQuery("select r from Response r where r.qid='"+sesqid+"'").list();
		} finally {
			//ses.close();
			HibernateUtil.closeSession();
		}
	}
}