package openworld.monsters;

import openworld.Coordinates;
import openworld.Damage;
import openworld.World;
import openworld.entityTypes.TravellingWorldEntity;
import openworld.entityTypes.WorldEntity;


public class Monster extends TravellingWorldEntity{
    private int speed;
    private int stepTimer=0;
    protected boolean awake;
    protected int xp=0;
    protected int level =1;
    protected int nextLevelXPTarget =2;
    
    public Monster(String name, Coordinates location, int maxHealth, World world, Damage attack, int speed) {
        super(name, location, maxHealth, world, attack);
        this.speed=speed;
    }



    
    public void gainXp(int amount)
    {
        xp+=amount;
        if(xp>=nextLevelXPTarget)
        {
            levelUp();
        }
    }

    public void levelUp()
    {
        xp-=nextLevelXPTarget;
        level++;
        maxHealth+=10;
        currentHealth=maxHealth;
        nextLevelXPTarget=nextLevelXPTarget*2;
        if(xp>=nextLevelXPTarget)
        {
            levelUp();
        }
    }


    public void takeTurn()
    {
        if(awake)
        {
            stepTimer++;
            if(stepTimer==speed)
            {
                move(location.getNextStepTo(world.getAdventurer().getLocation()));
                stepTimer=0;
            }
        }
    }


    public void encounter(WorldEntity traveller)
    {
        if (awake)
        {
            world.battle(this,traveller);
        }
        else
        {
            awake=true;
            System.out.println("You encounter "+ super.getName() + ". Better RUN!");
        }
    }

    public int getSpeed() {
        return speed;
    }

    public int getStepTimer() {
        return stepTimer;
    }

    public boolean isAwake() {
        return awake;
    }

    public int getXp() {
        return xp;
    }

    public int getLevel() {
        return level;
    }

    public int getNextLevelXPTarget() {
        return nextLevelXPTarget;
    }

    public void setAwake(boolean awake) {
        this.awake=awake;
    }

    
}
