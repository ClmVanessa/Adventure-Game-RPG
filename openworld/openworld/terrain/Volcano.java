package openworld.terrain;

import openworld.Coordinates;
import openworld.Damage;
import openworld.DamageType;
import openworld.World;
import openworld.entityTypes.TravellingWorldEntity;
import openworld.entityTypes.WorldEntity;

public class Volcano extends Terrain {

    public Volcano(String name, Coordinates location, int maxHealth, World world, Damage attack) {
        super(name, location, maxHealth, world, attack);
    }

    // maybe im done
    @Override
    public void encounter(WorldEntity traveller)
    {
        TravellingWorldEntity traveller2 = (TravellingWorldEntity) traveller;
        Coordinates travellerLocation = traveller2.getLocation();
        Coordinates safeMove = travellerLocation.findSafeMove(world);
        traveller2.move(safeMove);
    }
    
}
