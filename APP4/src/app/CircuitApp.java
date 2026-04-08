package app;

import electronique.Composant;

import java.io.File;
import java.util.Scanner;

public class CircuitApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CircuitBuilder builder = new CircuitBuilder();

        while (true) {
            try {
                File dossier = new File("src/donnees/fichiers_json");
                File[] fichiers = dossier.listFiles((_, name) -> name.endsWith(".json"));

                if (fichiers == null || fichiers.length == 0) {
                    System.out.println("Aucun fichier JSON trouvé.");
                    return;
                }

                System.out.println("Choisissez un fichier :");
                for (int i = 0; i < fichiers.length; i++) {
                    System.out.println("[" + (i + 1) + "] " + fichiers[i].getName());
                }

                System.out.print("-> ");
                int choix = parse(scanner.nextLine());

                if (choix < 1 || choix > fichiers.length) {
                    System.out.println("Choix invalide.");
                    continue;
                }

                String chemin = fichiers[choix - 1].getPath();
                Composant circuit = builder.construireCircuit(chemin);
                double resultat = circuit.calculerResistance();
                System.out.printf("Résistance équivalente : %.2f Ω\n", resultat);

            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }

            boolean choice = false;
            while (!choice) {
                System.out.println("[R] Recommencer | [Q] Quitter");
                String choix = scanner.nextLine().toUpperCase();

                if (choix.equals("Q")) {
                    System.out.println("Fermeture du programme.");
                    return;
                } else if (!choix.equals("R")) {
                    System.out.println("Choix invalide.");
                }
                if (choix.equals("R")) {
                    choice = true;
                }
            }
        }
    }

    public static int parse(String str) {
        int i = 0;
        if (str == null) {
            return i;
        }
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return i;
        }
    }
}