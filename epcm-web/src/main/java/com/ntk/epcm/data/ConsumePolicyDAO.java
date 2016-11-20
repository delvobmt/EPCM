package com.ntk.epcm.data;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.ConsumePolicyConstant;
import com.ntk.epcm.model.ConsumePolicy;

@Component
public class ConsumePolicyDAO implements IConsumePolicyDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConsumePolicyDAO.class);

	@Inject
	SessionFactory factory;

	@Override
	public int insert(ConsumePolicy consumePolicy) {
		int id = -1;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			id = (int) session.save(consumePolicy);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("ERROR while insert new ConsumePolicy", e);
			session.getTransaction().rollback();
		}
		session.close();
		return id;
	}

	@Override
	public boolean save(ConsumePolicy consumePolicy) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.update(consumePolicy);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("ERROR while save ConsumePolicy", e);
			session.getTransaction().rollback();
		}
		session.close();
		return !error;
	}

	@Override
	public boolean remove(List<ConsumePolicy> consumePolicy) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			consumePolicy.forEach(session::remove);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("ERROR while remove ConsumePolicy", e);
			session.getTransaction().rollback();
		}
		session.close();
		return !error;
	}

	@Override
	public ConsumePolicy findById(int id) {
		ConsumePolicy consumePolicy = null;
		Session session = factory.openSession();
		try {
			session.load(ConsumePolicy.class, id);
		} catch (Exception e) {
			LOGGER.error("ERROR while findById ConsumePolicy", e);
		}
		session.close();
		return consumePolicy;
	}

	@Override
	public List<ConsumePolicy> findAll() {
		List<ConsumePolicy> list = Collections.emptyList();
		Session session = factory.openSession();
		try {
			list = session.createQuery(String.format("from %s", ConsumePolicyConstant.TABLE), ConsumePolicy.class)
					.getResultList();
		} catch (Exception e) {
			LOGGER.error("ERROR while findAll ConsumePolicy", e);
		}
		return list;
	}

}
