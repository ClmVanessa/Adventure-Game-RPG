package openworld.entityTypes;

import openworld.Coordinates;
import openworld.Damage;
import openworld.World;

public class TravellingWorldEntity extends WorldEntity{

    public TravellingWorldEntity(String name, Coordinates location, int maxHealth, World world, Damage attack) {
        super(name, location, maxHealth, world, attack);
    }

    public void takeTurn()
    {
        System.out.println("Doing nothing!");    
    }    

    public void move(Coordinates vector)
    {
        location.addCoordinates(vector);
        world.resolveMove(this);
    }
    
}
