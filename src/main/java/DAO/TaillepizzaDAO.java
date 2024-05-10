package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;


import modele.Taillepizza;

public class TaillepizzaDAO {
	private SessionFactory sessionFactory;
	
	public TaillepizzaDAO() {
		sessionFactory = util.HibernateUtil.getSessionFactory();
	}
	
	public boolean create(Taillepizza tp) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean success = false;
		try {
			tx = session.beginTransaction();
			session.persist(tp);
			tx.commit();
			success = true;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			throw e;
		} finally {
			session.close();
		}
		return success;
	}
	
	public Taillepizza findById(long id) {
		Session session = sessionFactory.openSession();
		Taillepizza tp = session.get(Taillepizza.class, id);
		session.close();
		return tp;
	}
	
	public boolean update(Long id, String taille, float prix, int archive) {
		Session session = sessionFactory.openSession();
		Taillepizza tp = session.get(Taillepizza.class, id);
		boolean success = false;
		if (tp != null) {
			tp.setTaille(taille);
			tp.setPrix(prix);
			tp.setArchive(archive);

			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.persist(tp);
				tx.commit();
				success = true;
			} catch (Exception e) {
				if (tx != null)
					tx.rollback();
				throw e;
			} finally {
				session.close();
			}
		}
		return success;
	}
	
	public boolean delete(long id) {
		Session session = sessionFactory.openSession();
		Taillepizza tp = session.get(Taillepizza.class, id);
		boolean success = false;
		if (tp != null) {
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.remove(tp);
				tx.commit();
				success = true;
			} catch (Exception e) {
				if (tx != null)
					tx.rollback();
				throw e;
			} finally {
				session.close();
			}
		}
		return success;
	}

}
