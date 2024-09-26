package openworld.terrain;

import openworld.Coordinates;
import openworld.Damage;
import openworld.World;
import openworld.entityTypes.WorldEntity;

public class Terrain extends WorldEntity{

    public Terrain(String name, Coordinates location, int maxHealth, World world, Damage attack) {
        super(name, location, maxHealth, world, attack);
    }


}
