import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe principale pour la gestion interactive des comptes bancaires via une interface console.
 * Permet de créer, consulter, modifier, supprimer des comptes et demander des prêts.
 */
public class Main {
    // Liste immuable pour stocker tous les comptes bancaires
    private static final List<CompteBancaire> comptes = new ArrayList<>();
    // Scanner pour lire les entrées utilisateur depuis la console
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Point d'entrée principal de l'application.
     * Affiche un message de bienvenue et lance une boucle pour interagir avec l'utilisateur via un menu.
     *
     * @param args Arguments de la ligne de commande (non utilisés)
     */
    public static void main(String[] args) {
        System.out.println("Gestion simple des comptes bancaires");

        // Boucle principale pour afficher le menu et traiter les choix jusqu'à ce que l'utilisateur quitte
        while (true) {
            afficherMenu();
            int choix = lireChoix();
            if (choix == 0) {
                System.out.println("Au revoir !");
                break;
            }
            traiterChoix(choix);
        }
        // Ferme le scanner pour libérer les ressources
        scanner.close();
    }

    /**
     * Affiche le menu interactif avec les options disponibles pour gérer les comptes.
     */
    private static void afficherMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Créer un compte");
        System.out.println("2. Consulter tous les comptes");
        System.out.println("3. Consulter un compte par IBAN");
        System.out.println("4. Modifier un compte");
        System.out.println("5. Supprimer un compte");
        System.out.println("6. Demander un prêt");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    /**
     * Lit le choix de l'utilisateur depuis la console.
     *
     * @return L'entier correspondant au choix, ou -1 si l'entrée est invalide
     */
    private static int lireChoix() {
        try {
            // Convertit l'entrée utilisateur en entier
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            // Retourne -1 si l'entrée n'est pas un entier valide
            return -1;
        }
    }

    /**
     * Lit une valeur décimale (double) depuis la console.
     *
     * @return La valeur décimale entrée, ou 0.0 si l'entrée est invalide
     */
    private static double lireDouble() {
        try {
            // Convertit l'entrée utilisateur en double
            return Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            // Retourne 0.0 si l'entrée n'est pas un nombre valide
            return 0.0;
        }
    }

    /**
     * Traite le choix de l'utilisateur en appelant la méthode appropriée.
     *
     * @param choix Le numéro de l'option choisie par l'utilisateur
     */
    private static void traiterChoix(int choix) {
        switch (choix) {
            case 1 -> creerCompte();
            case 2 -> consulterTousComptes();
            case 3 -> consulterCompteParIban();
            case 4 -> modifierCompte();
            case 5 -> supprimerCompte();
            case 6 -> demanderPret();
            default -> System.out.println("Choix invalide. Veuillez réessayer.");
        }
    }

    /**
     * Crée un nouveau compte bancaire en demandant le titulaire et le solde initial.
     */
    private static void creerCompte() {
        System.out.print("Titulaire : ");
        String titulaire = scanner.nextLine();
        System.out.print("Solde initial (€) : ");
        double solde = lireDouble();

        // Vérifie que le solde initial est positif
        if (solde >= 0) {
            CompteBancaire compte = new CompteBancaire(titulaire, solde);
            comptes.add(compte);
            System.out.println("Compte créé avec succès ! " + compte);
        } else {
            System.out.println("Le solde initial doit être positif.");
        }
    }

    /**
     * Affiche la liste de tous les comptes bancaires.
     */
    private static void consulterTousComptes() {
        if (comptes.isEmpty()) {
            System.out.println("Aucun compte disponible.");
            return;
        }
        System.out.println("\n=== Liste des comptes ===");
        // Affiche chaque compte avec un numéro d'index
        for (int i = 0; i < comptes.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, comptes.get(i));
        }
    }

    /**
     * Consulte un compte spécifique en fonction de son IBAN.
     */
    private static void consulterCompteParIban() {
        System.out.print("IBAN du compte : ");
        String iban = scanner.nextLine();
        CompteBancaire compte = trouverCompteParIban(iban);
        if (compte != null) {
            System.out.println(compte);
        } else {
            System.out.println("Compte introuvable.");
        }
    }

    /**
     * Modifie le titulaire ou le solde d'un compte existant en fonction de son IBAN.
     */
    private static void modifierCompte() {
        System.out.print("IBAN du compte à modifier : ");
        String iban = scanner.nextLine();
        CompteBancaire compte = trouverCompteParIban(iban);
        if (compte == null) {
            System.out.println("Compte introuvable.");
            return;
        }

        // Demande un nouveau titulaire (optionnel)
        System.out.print("Nouveau titulaire (laisser vide pour ne pas changer) : ");
        String nouveauTitulaire = scanner.nextLine();
        if (!nouveauTitulaire.isEmpty()) {
            compte.setTitulaire(nouveauTitulaire);
        }

        // Demande un nouveau solde (optionnel, -1 pour ignorer)
        System.out.print("Nouveau solde (€, entrer -1 pour ne pas changer) : ");
        double nouveauSolde = lireDouble();
        if (nouveauSolde >= 0) {
            compte.setSolde(nouveauSolde);
        }

        System.out.println("Compte modifié avec succès ! " + compte);
    }

    /**
     * Supprime un compte bancaire en fonction de son IBAN.
     */
    private static void supprimerCompte() {
        System.out.print("IBAN du compte à supprimer : ");
        String iban = scanner.nextLine();
        CompteBancaire compte = trouverCompteParIban(iban);
        if (compte != null) {
            comptes.remove(compte);
            System.out.println("Compte supprimé avec succès !");
        } else {
            System.out.println("Compte introuvable.");
        }
    }

    /**
     * Demande un prêt pour un compte bancaire spécifique.
     */
    private static void demanderPret() {
        System.out.print("IBAN du compte : ");
        String iban = scanner.nextLine();
        CompteBancaire compte = trouverCompteParIban(iban);
        if (compte == null) {
            System.out.println("Compte introuvable.");
            return;
        }

        // Vérifie si un prêt existe déjà
        if (compte.getMontantPret() > 0) {
            System.out.println("Ce compte a déjà un prêt actif.");
            return;
        }

        // Demande les détails du prêt
        System.out.print("Montant du prêt (€) : ");
        double montant = lireDouble();
        System.out.print("Taux d'intérêt annuel (%) : ");
        double taux = lireDouble();
        System.out.print("Durée du prêt (années) : ");
        double duree = lireDouble();

        // Tente de demander le prêt
        if (compte.demanderPret(montant, taux, duree)) {
            System.out.println("Prêt accordé avec succès ! " + compte);
        } else {
            System.out.println("Prêt refusé : montant, taux ou durée invalide, ou prêt déjà existant.");
        }
    }

    /**
     * Recherche un compte bancaire par son IBAN.
     *
     * @param iban L'IBAN du compte à trouver
     * @return Le compte correspondant, ou null si introuvable
     */
    private static CompteBancaire trouverCompteParIban(String iban) {
        for (CompteBancaire compte : comptes) {
            if (compte.getIban().equals(iban)) {
                return compte;
            }
        }
        return null;
    }
}
