package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modele.Pizza;


public class PizzaDAO {
	private SessionFactory sessionFactory;
	
	public PizzaDAO() {
		sessionFactory = util.HibernateUtil.getSessionFactory();
	}
	
	public boolean create(Pizza p) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean success = false;
		try {
			tx = session.beginTransaction();
			session.persist(p);
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
	
	public Pizza findById(long id) {
		Session session = sessionFactory.openSession();
		Pizza p = session.get(Pizza.class, id);
		session.close();
		return p;
	}
	
	public boolean update(Long id, String nom, String image, float prix, int archive) {
		Session session = sessionFactory.openSession();
		Pizza p = session.get(Pizza.class, id);
		boolean success = false;
		if (p != null) {
			p.setNom(nom);
			p.setImage(image);
			p.setPrix(prix);
			p.setArchive(archive);

			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.persist(p);
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
		Pizza p = session.get(Pizza.class, id);
		boolean success = false;
		if (p != null) {
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.remove(p);
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
