package com.ntk.epcm.data;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.ntk.epcm.constant.ConsumeGroupConstant;
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
		try {
			session.getTransaction().begin();
			id = (int) session.save(consumeGroup);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("ERROR while insert new consumeGroup", e);
			session.getTransaction().rollback();
		}
		session.close();
		return id;
	}

	@Override
	public boolean save(ConsumeGroup consumeGroup) {
		boolean error = false;
		Session session = factory.openSession();
		try {
			session.getTransaction().begin();
			session.update(consumeGroup);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("ERROR while save consumeGroup", e);
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
		try {
			session.getTransaction().begin();
			session.remove(consumeGroup);
			session.getTransaction().commit();
		} catch (Exception e) {
			LOGGER.error("ERROR while remove consumeGroup", e);
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
		try {
			consumeGroup = session.load(ConsumeGroup.class, id);
		} catch (Exception e) {
			LOGGER.error("ERROR while find consume group by Id", e);
		}
		session.close();
		return consumeGroup;
	}

	@Override
	public List<ConsumeGroup> findAll() {
		List<ConsumeGroup> list = Collections.emptyList();
		Session session = factory.openSession();
		try {
			list = session.createQuery(String.format("from %s", ConsumeGroupConstant.TABLE), ConsumeGroup.class)
					.getResultList();
		} catch (Exception e) {
			LOGGER.error("ERROR while find all consume groups", e);
		}
		session.close();
		return list;
	}

	@Override
	public ConsumeGroup findByName(String group) {
		ConsumeGroup g = null;
		Session session = factory.openSession();
		try {
			g = session.createQuery(String.format("from %s where %s=:group", ConsumeGroupConstant.TABLE, ConsumeGroupConstant.GROUP_KEY)
					, ConsumeGroup.class).setParameter("group", group)
					.getSingleResult();
		} catch (Exception e) {
			LOGGER.error("ERROR while find consume groups by name", e);
		}
		session.close();
		return g;
	}

}
