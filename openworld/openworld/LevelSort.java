package openworld;

import java.util.Comparator;

import openworld.entityTypes.WorldEntity;
import openworld.monsters.Monster;

// Task 3.1
public class LevelSort implements Comparator<WorldEntity>{

    @Override
    public int compare(WorldEntity o1, WorldEntity o2) {
        if (o1 instanceof Monster && o2 instanceof Monster) {
            Monster m1 = (Monster) o1;
            Monster m2 = (Monster) o2;

            if (m1.getLevel() == m2.getLevel()) {
                return m1.getName().compareTo(m2.getName());
            }
                return m2.getLevel() - m1.getLevel();
            }
        return 0;    
    }
}