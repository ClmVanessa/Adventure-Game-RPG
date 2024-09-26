package openworld.adventurer;

import openworld.Coordinates;
import openworld.Damage;
import openworld.World;
import openworld.entityTypes.TravellingWorldEntity;
import openworld.entityTypes.WorldEntity;

public class Adventurer extends TravellingWorldEntity {

    private Damage[] attacks = new Damage[3];
    private int totalAttacks=1;


    public Adventurer(String name, Coordinates location, int maxHealth, World world, Damage attack) {
        super(name, location, maxHealth, world, attack);
        attacks[0] = attack;
    }


    public void addAttack(Damage attack)
    {
        if(totalAttacks<attacks.length)
        {
            attacks[totalAttacks] = attack;
            totalAttacks++;
        }
    }


    public void attack(WorldEntity target)
    {
        for(int i=0;i<totalAttacks;i++)
        {
            target.takeDamage(attacks[i]);
        }
    }

    public Damage[] getAttacks()
    {
        return attacks;
    }


    // Task 3.1 (done)
    public void takeTurn() {
        this.location.addCoordinates(new Coordinates(1, 1));
    }
}