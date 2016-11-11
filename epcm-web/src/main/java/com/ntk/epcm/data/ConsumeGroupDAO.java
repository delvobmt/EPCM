package com.ntk.epcm.data;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ntk.epcm.model.ConsumeGroup;

@Component
public class ConsumeGroupDAO implements IConsumeGroupDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumeGroupDAO.class);
	
	@Inject
	SessionFactory factory;
	
	@Override
	public int insert(ConsumeGroup consumeGroup) {
		int id = -1;
		Session session = factory.openSession();
		try{
			session.getTransaction().begin();
			id = (int) session.save(consumeGroup);
			session.getTransaction().commit();
		}catch (HibernateException e) {
			LOGGER.error("ERROR while insert new consumeGroup",e);
			session.getTransaction().rollback();
		}
		session.close();
		return id;
	}

	@Override
	public boolean save(ConsumeGroup consumeGroup) {
		boolean error = false;
		Session session = factory.openSession();
		try{
			session.getTransaction().begin();
			session.update(consumeGroup);
			session.getTransaction().commit();
		}catch (HibernateException e) {
			LOGGER.error("ERROR while save consumeGroup",e);
			session.getTransaction().rollback();
			error = true;
		}
		session.close();
		return !error;
	}

	@Override
	public boolean remove(ConsumeGroup consumeGroup) {
		boolean error = false;
		Session session = factory.openSession();
		try{
			session.getTransaction().begin();
			session.remove(consumeGroup);
			session.getTransaction().commit();
		}catch (HibernateException e) {
			LOGGER.error("ERROR while remove consumeGroup",e);
			session.getTransaction().rollback();
			error = true;
		}
		session.close();
		return !error;
	}

	@Override
	public ConsumeGroup findbyId(int id) {
		ConsumeGroup consumeGroup = null;
		Session session = factory.openSession();
		try{
			consumeGroup = session.load(ConsumeGroup.class, id);
		}catch (HibernateException e) {
			LOGGER.error("ERROR while save consumeGroup",e);
		}
		session.close();
		return consumeGroup;
	}

}
