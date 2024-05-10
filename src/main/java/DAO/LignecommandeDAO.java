package DAO;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import modele.Lignecommande;

import java.util.List;

public class LignecommandeDAO {
	private SessionFactory sessionFactory;
	private Session session;
	public LignecommandeDAO()
	{
		sessionFactory = util.HibernateUtil.getSessionFactory();
		//session= sessionFactory.openSession();
	}
	public boolean create(Lignecommande l) {
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		boolean success = false;
		try {
			tx = session.beginTransaction();
			session.persist(l);
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
	public Lignecommande findById(long id) {
		Session session = sessionFactory.openSession();
		Lignecommande l = session.get(Lignecommande.class, id);
		session.close();
		return l;
	}
	public boolean update(Long id,int qte_lignecom) {
		Session session = sessionFactory.openSession();
		Lignecommande l = session.get(Lignecommande.class, id);
		boolean success = false;
		if (l != null) {
			l.setQte_lignecom(qte_lignecom);
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.persist(l);
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
		Lignecommande l = session.get(Lignecommande.class, id);
		boolean success = false;
		if (l != null) {
			Transaction tx = null;
			try {
				tx = session.beginTransaction();
				session.remove(l);
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
	public void updateAllLignesCommandeForCommande(int idCommande, int idClient) {
	    Session session = null;
	    Transaction tx = null;

	    try {
	        session = sessionFactory.openSession();
	        tx = session.beginTransaction();

	        int rowCount = session.createQuery(
	                "UPDATE Lignecommande SET valid_lignecom = :newValidFlag " +
	                "WHERE id_com = :idCommande")
	            .setParameter("newValidFlag", 1)
	            .setParameter("idCommande", idCommande)
	            .executeUpdate();

	        System.out.println("Nombre de lignes de commande mises à jour : " + rowCount);

	        tx.commit(); 
	    } catch (RuntimeException e) {
	        if (tx != null) {
	            tx.rollback(); 
	        }
	        throw e; 
	    } finally {
	        if (session != null) {
	            session.close(); // Fermer la session Hibernate
	        }
	    }
	}

	
}
