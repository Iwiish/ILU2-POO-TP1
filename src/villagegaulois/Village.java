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
					etals[i].afficherEtal();
					numEtalProd++;
				}
			}
			
			Etal[] occupe = new Etal[numEtalProd];
			
			for(int i = 0; i<occupe.length; i++) {
				//A FINIR
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
	
	
	public static void main(String[] args) {
		//TEST
	}
	
}
/*


*/