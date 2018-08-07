package dev.app;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BanqueAppTest {
	private EntityManagerFactory emf;
	private EntityManager em;

	@Before
	// Demande d'une connexion à notre EntityManagerFactory
	// Ouverture de la connexion
	public void setUp() {
		this.emf = Persistence.createEntityManagerFactory("banque-app");
		this.em = emf.createEntityManager();
	}

	@Test
	public void test_initialisation_Bdd() {
		Adresse adresse = new Adresse();
		Client client = new Client();
		Banque banque = new Banque();
		Operation operation = new Operation();
		Compte compte = new Compte();

		// Generation de ma liste client et operation
		ArrayList<Operation> operations = new ArrayList<>();
		ArrayList<Client> clients = new ArrayList<>();

		// incrementation de mes listes avec des clients et des opérations
		clients.add(client);
		operations.add(operation);

		// Creation banque :
		// Génération du nom
		banque.setNom("CIC");

		// Creation Client :
		// Génération du nom, prenom, adresse, date de naissance et attribution
		// de la banque
		client.setBanque(banque);
		client.setNom("JEANMICHEL");
		client.setPrenom("CRAPAUX");
		client.setAdresse(adresse);
		client.setDateNaissance(LocalDate.now());

		// Creation Adresse pour le client :
		// Remplissage des variable de l'adresse du client
		adresse.setCodePostal(44000);
		adresse.setNumero(12);
		adresse.setRue("rue de la faille");
		adresse.setVille("Nantes");

		// Creation Compte :
		// Générations du numero de compte et du solde
		// attribution du compte à une liste de clients et une liste d'opération
		// au compte
		compte.setClient(clients);
		compte.setOperation(operations);
		compte.setNumero("123456789");
		compte.setSolde(2000);

		// Creation Operation
		// attribution d'une date d'un montant et d'un motif.
		// Attribution d'une opération à un compte
		operation.setCompte(compte);
		operation.setDate(LocalDateTime.now());
		operation.setMontant(5000);
		operation.setMotif("Virement du mois");

		// Sauvegarde dans une base de donnée creation d'un merge / commit
		EntityTransaction tx = this.em.getTransaction();
		tx.begin();
		this.em.persist(client);
		this.em.persist(banque);
		this.em.persist(operation);
		this.em.persist(compte);

		tx.commit();

	}

	@After
	// cloture de la connexion
	public void tearDown() {
		em.close();
		emf.close();

	}

}
