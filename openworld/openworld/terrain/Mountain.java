package openworld.terrain;

import openworld.Coordinates;
import openworld.Damage;
import openworld.DamageType;
import openworld.World;
import openworld.entityTypes.WorldEntity;

public class Mountain extends Terrain{

    public Mountain(String name, Coordinates location, int maxHealth, World world, Damage attack) {
        super(name, location, maxHealth, world, attack);
    }

    // Task 1.1 (done)
    @Override
    public void encounter(WorldEntity traveller)
    {
        Damage damage = new Damage(10, DamageType.ICE);
        traveller.takeDamage(damage);
    }
}
