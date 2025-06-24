import java.time.LocalDateTime;
import java.util.Random;

/**
 * Représente un compte bancaire avec un titulaire, un solde, un IBAN, une date d'ouverture et un prêt optionnel.
 */
public class CompteBancaire {
    private String titulaire;
    private double solde;
    private final String iban;
    private final LocalDateTime dateOuverture;
    private double montantPret; // Montant du prêt (0 si aucun prêt)
    private double tauxInteret; // Taux d'intérêt annuel en % (ex. : 5 pour 5%)
    private double dureePret; // Durée du prêt en années

    /**
     * Constructeur pour créer un compte bancaire sans prêt.
     *
     * @param titulaire Le nom du titulaire du compte
     * @param solde Le solde initial du compte
     */
    public CompteBancaire(String titulaire, double solde) {
        this.titulaire = titulaire;
        this.solde = solde;
        this.iban = genererIban();
        this.dateOuverture = LocalDateTime.now();
        this.montantPret = 0.0;
        this.tauxInteret = 0.0;
        this.dureePret = 0.0;
    }

    /**
     * Génération d'un IBAN simplifié commençant par "FR" suivi de 14 chiffres aléatoires.
     *
     * @return L'IBAN généré
     */
    private String genererIban() {
        Random random = new Random();
        StringBuilder iban = new StringBuilder("FR");
        for (int i = 0; i < 14; i++) {
            iban.append(random.nextInt(10));
        }
        return iban.toString();
    }

    /**
     * Demande un prêt et crédite le solde du compte.
     *
     * @param montant Montant du prêt
     * @param taux Taux d'intérêt annuel en pourcentage
     * @param duree Durée du prêt en années
     * @return true si le prêt est accepté, false sinon
     */
    public boolean demanderPret(double montant, double taux, double duree) {
        if (montant <= 0 || taux <= 0 || taux > 20 || duree <= 0 || montantPret > 0) {
            return false; // Prêt refusé si montant/durée/taux invalides ou prêt existant
        }
        this.montantPret = montant;
        this.tauxInteret = taux;
        this.dureePret = duree;
        this.solde += montant; // Crédite le montant du prêt au solde
        return true;
    }

    /**
     * Calcule les intérêts simples du prêt actif.
     *
     * @return Les intérêts totaux, ou 0 si aucun prêt
     */
    public double calculerInterets() {
        if (montantPret == 0) {
            return 0.0;
        }
        return montantPret * (tauxInteret / 100) * dureePret; // Intérêts simples
    }

    // Getters et Setters
    @SuppressWarnings("unused")
    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }
    @SuppressWarnings("unused")
    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public String getIban() {
        return iban;
    }
    @SuppressWarnings("unused")
    public LocalDateTime getDateOuverture() {
        return dateOuverture;
    }

    public double getMontantPret() {
        return montantPret;
    }
    @SuppressWarnings("unused")
    public double getTauxInteret() {
        return tauxInteret;
    }

    @SuppressWarnings("unused")
    public double getDureePret() {
        return dureePret;
    }

    /**
     * Affiche les informations du compte, incluant les détails du prêt si applicable.
     *
     * @return Une chaîne représentant le compte
     */
    @Override
    public String toString() {
        String baseInfo = String.format("IBAN: %s, Titulaire: %s, Solde: %.2f€, Ouverture: %s",
                iban, titulaire, solde, dateOuverture);
        if (montantPret > 0) {
            baseInfo += String.format(", Prêt: %.2f€ (Taux: %.2f%%, Durée: %.1f ans, Intérêts: %.2f€)",
                    montantPret, tauxInteret, dureePret, calculerInterets());
        }
        return baseInfo;
    }
}
