package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private Marche marche;
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.marche = new Marche(nbEtal);
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	private static  class Marche{
		private Etal[] etals;

		private Marche(int numEtals) {
			this.etals = new Etal[numEtals];
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			Etal etalUse = this.etals[indiceEtal];
			etalUse.occuperEtal(vendeur, produit, nbProduit); 
		}
		
		int trouverEtalLibre() {
			int a = -1;
			for(int i=0; i<etals.length && a<=0; i++){
				if(etals[i].isEtalOccupe()) {
					a=i;
				}
			}
			if(a==-1) {
				return -1;
			}else {
				return a;
			}
		}
		
		Etal[] trouverEtals(String produit) {
			
			int numEtalProd = 0;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					numEtalProd++;
				}
			}
			
			Etal[] occupe = new Etal[numEtalProd];
			
			for(int i = 0, y=0; i<etals.length; i++) {
				if(etals[i].contientProduit(produit)) {
					occupe[y]=etals[i];
					y++;
				}
			}
			
			return occupe;
		}
		
		Etal trouverVendeur(Gaulois gaulois) {
			int indiceGaulois = -1;
			for (int i = 0; i < etals.length && indiceGaulois < 0; i++) {
				if(etals[i].getVendeur().equals(gaulois)) {
					indiceGaulois = i;
				}
			}
			if(indiceGaulois ==-1) {
				return null;
			}else {
				return etals[indiceGaulois];
			}
		}
		
		String afficherMarche() {
			StringBuilder stringR = new StringBuilder();
			int nbEtalVide =0;
			for(int i = 0; i<etals.length; i++) {
				if(etals[i].isEtalOccupe()) {
					stringR.append(etals[i].afficherEtal() + "_");
				}else {
					nbEtalVide++;
				}
			}
			if(nbEtalVide > 0) {
				stringR.append("Il reste" + nbEtalVide + "étals non utilisés dans le marché.\n");
			}
			return stringR.toString();
		}
		
	}

	public String rechercherVendeursProduit(String produit) {
		Etal[] etalP = marche.trouverEtals(produit);
		StringBuilder stringR = new StringBuilder();
		if(etalP.length == 0) {
			stringR.append("Il n'y a pas de vendeur qui propose des fleurs au marché.");
		}else if(etalP.length == 1) {
			stringR.append("Seul le vendeur " + etalP[0].getVendeur().toString() + " propose des fleurs au marché.");
		}else {
			stringR.append("Les vendeurs qui proposent des fleurs sont : " );
			for (int i=0;i<etalP.length;i++) {
				stringR.append("- " + etalP[i].getVendeur().toString()+ "\n" );
			}
		}
		return stringR.toString();
	}

	public String installerVendeur(Gaulois nom, String produit, int nbProduit) {
		StringBuilder stringR = new StringBuilder();
		int i  = marche.trouverEtalLibre();
		marche.utiliserEtal(i, nom, produit, nbProduit);
		stringR.append( nom + " cherche un endroit pour vendre " + nbProduit 
				+ produit + ".\r\n" + "Le vendeur" + nom + " vend des fleurs à l'étal n°" + i);
		
		return stringR.toString();
	}

	public Etal rechercherEtal(Gaulois nom) {
		Etal recherche = marche.trouverVendeur(nom);
		return recherche;
	}

	public String partirVendeur(Gaulois nom) {
		StringBuilder stringR = new StringBuilder();
		Etal etalV = marche.trouverVendeur(nom);
		stringR.append("Le vendeur" + nom + "quitte son étal, il a vendu" + "parmi les" + "qu'il voulait vendre.");
	
		return stringR.toString();
	}

	public String afficherMarche() {
		return marche.afficherMarche();
	}
	
	
	
}
