package electronique;

import java.util.List;

public class CircuitParallele extends Circuit {

    public CircuitParallele(List<Composant> composants) {
        super(composants);
    }

    @Override
    public double calculerResistance() {
        double total = 0;

        for (Composant c : composants) {
            total += 1 / c.calculerResistance();
        }

        return 1 / total;
    }
}