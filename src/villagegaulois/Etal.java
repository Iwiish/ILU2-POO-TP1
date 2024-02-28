package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		etalOccupe = false;
		StringBuilder chaine = new StringBuilder();
		try{
			chaine.append("Le vendeur " + vendeur.getNom() + " quitte son étal, ");
			int produitVendu = quantiteDebutMarche - quantite;
			if (produitVendu > 0) {
				chaine.append(
						"il a vendu " + produitVendu + " " + produit + " parmi les " + quantiteDebutMarche + " " + produit + " qu'il avait au début.\n");
			} else {
				chaine.append("il n'a malheureusement rien vendu.\n");
			}
			
		} catch(NullPointerException e) {
			chaine.append("L'étal n'est pas occupé.");
		}
		return chaine.toString();
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) throws IllegalArgumentException, IllegalStateException{
		
		if(quantiteAcheter < 1) {
			throw new IllegalArgumentException("valeur inférieur à 1");
		}else if(!this.isEtalOccupe()) {
			throw new IllegalStateException("L'étal n'est pas occupé");
		}else {
			StringBuilder chaine = new StringBuilder();
			
			try {
				chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
						+ " " + produit + " à " + vendeur.getNom());
				if (quantite == 0) {
					chaine.append(", malheureusement il n'y en a plus !");
					quantiteAcheter = 0;
				}
				if (quantiteAcheter > quantite) {
					chaine.append(", comme il n'y en a plus que " + quantite + ", "
							+ acheteur.getNom() + " vide l'étal de "
							+ vendeur.getNom() + ".\n");
					quantiteAcheter = quantite;
					quantite = 0;
				}
				if (quantite != 0) {
					quantite -= quantiteAcheter;
					chaine.append(". " + acheteur.getNom()
							+ ", est ravi de tout trouver sur l'étal de "
							+ vendeur.getNom() + "\n");
				}
				return chaine.toString();
			}catch(NullPointerException e) {
				return chaine.toString();
			}
		}
		
	}

	public boolean contientProduit(String produit) {
		return this.produit.equals(produit);
	}

}
