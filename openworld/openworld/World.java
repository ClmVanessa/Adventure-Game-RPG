package openworld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.stream.events.Characters;

import openworld.adventurer.Adventurer;
import openworld.characters.Healer;
import openworld.characters.NPC;
import openworld.characters.Wizard;

import openworld.terrain.Grasslands;
import openworld.terrain.Mountain;
import openworld.terrain.Terrain;
import openworld.terrain.Volcano;
import openworld.entityTypes.WorldEntity;
import openworld.monsters.Blob;
import openworld.monsters.Monster;
import openworld.monsters.Skeleton;

public class World {

    private int xDimension;
    private int yDimension;
    private ArrayList<Terrain> terrain = new ArrayList<>();
    private ArrayList<Monster> monsters = new ArrayList<>();

    private ArrayList<NPC> nonPlayerCharacters = new ArrayList<>();
    private Adventurer adventurer;

    public World(int xDimension, int yDimension) {
        this.xDimension = xDimension;
        this.yDimension = yDimension;
    }

    // Task 1.2
    public void initaliseWorld() {
        generateTerrain();
        generateMonsters();
        generateCharacters();
        adventurer = new Adventurer("Bob", new Coordinates(0, 0), 100, this, new Damage(10, DamageType.PHYSICAL));
    }

    // Task 3.1
    public void run() {
        adventurer.takeTurn();
        nonPlayerCharactersMove();
        monsterMove();
    }
    
    // Task 1.2 /
    public void generateMonsters() {
        boolean isSkeleton = true;
    
        for (int x = 0; x <= xDimension; x++) {
            for (int y = 0; y <= yDimension; y+=7) {
                if (isSkeleton) {
                    monsters.add(new Skeleton("Skel", new Coordinates(x, y), 100, this,
                            new Damage(10, DamageType.PHYSICAL), 10));
                } else {
                    monsters.add(new Blob("Blob", new Coordinates(x, y), 100, this,
                            new Damage(8, DamageType.FIRE), 5));
                }
                isSkeleton = !isSkeleton;
            }
        }
    }
    
    

    // Task 1.2 /
    public void generateCharacters() {
        boolean isWizard = true;
    
        for (int x = 0; x <= xDimension; x++) {
            for (int y = 0; y <= yDimension; y+=15) {
                if (isWizard) {
                    nonPlayerCharacters.add(new Wizard("Wizard", new Coordinates(x, y), 100, this,
                            new Damage(10, DamageType.PHYSICAL), new Coordinates(x+10, y+10)));
                } else {
                    nonPlayerCharacters.add(new Healer("Healer", new Coordinates(x, y), 100, this,
                            new Damage(8, DamageType.FIRE), new Coordinates(x+13, y+13)));
                }
                isWizard = !isWizard;
            }
        }
    }

    public void generateTerrain() {
        for (int x = 0; x <= xDimension; x++) {
            for (int y = 0; y <= yDimension; y++) {
                terrain.add(new Grasslands("Grass", new Coordinates(x, y), 1000, this,
                        new Damage(0, DamageType.PHYSICAL)));
            }
        }
    }

    // Task 2.1
    public void printWorld() {
        for (int y = yDimension; y >= 0; y--) {
            for (int x = 0; x <= xDimension; x++) {
                String output = "";
                boolean isAdventurer = false;
                for (Terrain terrain : terrain) {
                    if (terrain.getLocation().getX() == x && terrain.getLocation().getY() == y) {
                        output += terrain.getName() + ", ";
                    }
                }
                for (Monster monster : monsters) {
                    if (monster.getLocation().getX() == x && monster.getLocation().getY() == y) {
                        output += monster.getName() + ", ";
                    }
                }
                for (NPC character : nonPlayerCharacters) {
                    if (character.getLocation().getX() == x && character.getLocation().getY() == y) {
                        output += character.getName() + ", ";
                    }
                }
                if (adventurer.getLocation().getX() == x && adventurer.getLocation().getY() == y) {
                    isAdventurer = true;
                }
                if (output.equals("")) {
                    System.out.print("Empty, ");
                } else {
                    System.out.print(output);
                }
                if (isAdventurer) {
                    System.out.print("*ADV*, ");
                }
                System.out.print("\t\t"); // Add extra spaces here
            }
            System.out.println("\n\n"); // Add extra new lines here
        }
    }
    
    // Task 3.1
    private void monsterMove() {
        Collections.sort(monsters, new LevelSort());
        for (Monster monster : monsters) {
            monster.takeTurn();
        }
    }

    // Task 3.1 (done)
    public void nonPlayerCharactersMove() {
        Collections.sort(nonPlayerCharacters, new AlphabeticalSort());
        for (NPC npc : nonPlayerCharacters) {
            npc.takeTurn();
        }
    }

    // Task 3.2 (done)
    public void resolveMove(WorldEntity traveller) {
        Coordinates currentLocation = traveller.getLocation();
        ArrayList<WorldEntity> entitiesAtLocation = new ArrayList <>();
        for (Terrain terrainAtLocation : terrain) {
            if (terrainAtLocation.getLocation().equals(currentLocation)) {
                entitiesAtLocation.add(terrainAtLocation);
            }
        }
        for (Monster monsterAtLocation : monsters) {
            if (monsterAtLocation.getLocation().equals(currentLocation)) {
                entitiesAtLocation.add(monsterAtLocation);
            }
        }
        for (NPC charactersAtLocation : nonPlayerCharacters) {
            if (charactersAtLocation.getLocation().equals(currentLocation)) {
                entitiesAtLocation.add(charactersAtLocation);
            }
        }
        for (WorldEntity entity : entitiesAtLocation) {
            if (entity instanceof Terrain) {
                if (entity instanceof Volcano) {
                    ((Volcano) entity).encounter(traveller);
                } else if (entity instanceof Mountain) {
                    ((Mountain) entity).encounter(traveller);
                }
            }
            if (entity instanceof Monster) {
                if (entity instanceof Skeleton) {
                    ((Skeleton) entity).encounter(traveller);
                } else if (entity instanceof Blob) {
                    ((Blob) entity).encounter(traveller);
                } else {
                    ((Monster) entity).encounter(traveller);
                }
            }
            if (entity instanceof NPC) {
                if (entity instanceof Healer) {
                    ((Healer) entity).encounter(traveller);
                } else if (entity instanceof Wizard) {
                    ((Wizard) entity).encounter(traveller);
                }
            }
        }
    }

    public void battle(WorldEntity location, WorldEntity traveller) {
        while (location.getCurrentHealth() > 0 && traveller.getCurrentHealth() > 0) {
            location.attack(traveller);
            if (traveller.getCurrentHealth() > 0) {
                traveller.attack(location);
            }
        }
    }

    // Task 2.2
    public static void main(String[] args) {
        World world = new World(20,20);
        world.initaliseWorld();
        world.printWorld();
    }

    public int getxDimension() {
        return xDimension;
    }

    public int getyDimension() {
        return yDimension;
    }

    public Adventurer getAdventurer() {
        return adventurer;
    }

    public void setAdventurer(Adventurer adventurer) {
        this.adventurer = adventurer;
    }

    public ArrayList<Terrain> getTerrain() {
        return terrain;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }

    public ArrayList<NPC> getNonPlayerCharacters() {
        return nonPlayerCharacters;
    }

}
