package app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import electronique.CircuitParallele;
import electronique.CircuitSerie;
import electronique.Composant;
import electronique.Resistance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CircuitBuilder {

    public Composant construireCircuit(String cheminFichier) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode node = mapper.readTree(new File(cheminFichier)).get("circuit");
            return lireComposant(node);
        } catch (IOException e) {
            throw new RuntimeException("Erreur lecture JSON", e);
        }
    }

    private Composant lireComposant(JsonNode node) {
        String type = node.get("type").asText();

        if ("resistance".equals(type)) {
            return new Resistance(node.get("valeur").asDouble());
        }
        
        else if ("serie".equals(type) || "parallele".equals(type)) {
            List<Composant> composants = new ArrayList<>();

            for (JsonNode composantNode : node.get("composants")) {
                composants.add(lireComposant(composantNode));
            }

            if ("serie".equals(type)) {
                return new CircuitSerie(composants);
            } else {
                return new CircuitParallele(composants);
            }
        }
        throw new IllegalArgumentException("Type inconnu : " + type);
    }
}