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
		if(this.chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef");
		}else {
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
	}
	
	private static  class Marche{
		private Etal[] etals;

		private Marche(int numEtals) {
		    this.etals = new Etal[numEtals];
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur,String produit, int nbProduit) {
			Etal etalUse = new Etal();
			this.etals[indiceEtal] = etalUse;
			etalUse.occuperEtal(vendeur, produit, nbProduit); 
		}
		
		int trouverEtalLibre() {
		    int a = -1;
		    for (int i = 0; i < etals.length && a == -1; i++) {
		        if (etals[i] == null || !etals[i].isEtalOccupe()) {
		            a = i;
		        }
		    }
		    return a;
		}
		
		Etal[] trouverEtals(String produit) {
			
			int numEtalProd = 0;
			for(int i = 0; i<etals.length && etals[i] != null; i++) {
				if(etals[i].contientProduit(produit)) {
					numEtalProd++;
				}
			}
			
			Etal[] occupe = new Etal[numEtalProd];
			
			for(int i = 0, y=0; i<etals.length && etals[i] != null; i++) {
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
				if(etals[i]==null || (etals[i] != null && !etals[i].isEtalOccupe())) {
					nbEtalVide++;
				}else {
					stringR.append(etals[i].afficherEtal());
				}
			}
			if(nbEtalVide > 0) {
				stringR.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\r\n");
			}
			return stringR.toString();
		}
		
	}

	public String rechercherVendeursProduit(String produit) {
		Etal[] etalP = marche.trouverEtals(produit);
		StringBuilder stringR = new StringBuilder();
		if(etalP.length == 0) {
			stringR.append("Il n'y a pas de vendeur qui propose des " + produit + " au marché.\r\n");
		}else if(etalP.length == 1) {
			stringR.append("Seul le vendeur " + etalP[0].getVendeur().getNom() + " propose des " + produit + " au marché.\r\n");
		}else {
			stringR.append("Les vendeurs qui proposent des " + produit + " sont : \r\n" );
			for (int i=0;i<etalP.length;i++) {
				stringR.append("-" + etalP[i].getVendeur().getNom()+ "\r\n" );
			}
		}
		return stringR.toString();
	}

	public String installerVendeur(Gaulois gaulois, String produit, int nbProduit) {
		StringBuilder stringR = new StringBuilder();
		int i  = marche.trouverEtalLibre();
		if(i==-1) {
			stringR.append("Il n'y a pas de place.\r\n");
			return stringR.toString();
		}else {
			marche.utiliserEtal(i, gaulois, produit, nbProduit);
			stringR.append( gaulois.getNom() + " cherche un endroit pour vendre " + nbProduit 
					+ " " + produit + ".\r\n" + "Le vendeur " + gaulois.getNom() + " vend des " + produit + " à l'étal n°" + (i+1) + "\r\n");
			
			return stringR.toString();
		}
		
	}

	public Etal rechercherEtal(Gaulois gaulois) {
		return marche.trouverVendeur(gaulois);
	}

	public String partirVendeur(Gaulois gaulois) {
		Etal etalV = marche.trouverVendeur(gaulois);
		return etalV.libererEtal();
	}

	public String afficherMarche() {
		StringBuilder stringR = new StringBuilder();
		stringR.append("Le marché du village " + "'" + nom + "'" + " possède plusieurs étals : \r\n" ); 
		stringR.append(marche.afficherMarche());
		return stringR.toString();
	}
	
	
	
	
}
