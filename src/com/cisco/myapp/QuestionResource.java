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

@Path("/question")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
public class QuestionResource {
	
	@POST
	public Question postQuestion(Question q) throws Exception{
		Session ses = HibernateUtil.currentSession();
		Transaction tx=ses.beginTransaction();
		Integer id=1;
		try{
			q.setqid(id);
			ses.save(q);
			tx.commit();
		} catch (Exception e){
		     e.printStackTrace();
		     tx.rollback();
		} finally {
			//ses.close();
			HibernateUtil.closeSession();
		}
		return q;
	}
	
	@GET
	@Path("/questions")
	@Produces({MediaType.APPLICATION_JSON})
	public List<Question> getQuestions() throws Exception {
		Session ses = HibernateUtil.currentSession();
		Transaction tx=ses.beginTransaction();
		try {
			return ses.createQuery("select q from Question q").list();
		} finally {
			//ses.close();
			HibernateUtil.closeSession();
		}
	}
}