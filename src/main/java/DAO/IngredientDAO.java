package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modele.Ingredient;


public class IngredientDAO {
	private SessionFactory sessionFactory;
	
	public IngredientDAO() {
		sessionFactory = util.HibernateUtil.getSessionFactory();
	}
	
	public boolean create(Ingredient i) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean success = false;
		try {
			tx = session.beginTransaction();
			session.persist(i);
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
	
	public Ingredient findById(long id) {
		Session session = sessionFactory.openSession();
		Ingredient i = session.get(Ingredient.class, id);
		session.close();
		return i;
	}
	
	public boolean update(Long id, String nom, float prix, int archive) {
		Session session = sessionFactory.openSession();
		Ingredient i = session.get(Ingredient.class, id);
		boolean success = false;
		if (i != null) {
			i.setNom(nom);
			i.setPrix(prix);
			i.setArchive(archive);

			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.persist(i);
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
		Ingredient i = session.get(Ingredient.class, id);
		boolean success = false;
		if (i != null) {
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.remove(i);
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
