package zombie;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class ZombieModel {
	private Tree tree;
	private River river;
	private Rock rock;
	private ArrayList<Human> humans;
	private ArrayList<Zombie> zombies;
	private AlphaZombie alphaZombie; //later on changes to alpha zombie
	
	private int numOfTrees = 40;
	private int numOfRocks = 6;
	private int numHumans = 30;
	private int startOfRiver = 0;
	
	private final Color[][] matrix;
	private final int width;
	private final int height;
	private final int dotSize;
	
	public ZombieModel(int widthArg, int heightArg, int dotSizeArg) {
		width = widthArg;
		height = heightArg;
		dotSize = dotSizeArg;
		matrix = new Color[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				matrix[i][j] = Color.BLACK;
			}
		}
		
		tree = new Tree(this);
		river = new River(this);
		rock = new Rock(this);
		zombies = new ArrayList<>();
		alphaZombie = new AlphaZombie(this); //later on change it to alphaZombie class
		
		humans = new ArrayList<>();
		for(int i = 0; i<numHumans; i++){
			humans.add(new Human(this));
		}
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	public int getDotSize() { return dotSize; }
	public Color getColor(int x, int y) { return matrix[x][y]; }
	public void setColor(int x, int y, Color color) { matrix[x][y] = color; }
	
	public void initialize() {
		for(int i = 0; i < numOfTrees; i++)tree.init(randomNum(width), randomNum(height));//Trees
		for(int i = 0; i < numOfRocks; i++)rock.init(randomNum(width), randomNum(height), randomNum(5)); //Rocks
		startOfRiver = randomNum(width-5);
		river.init(startOfRiver); //river
		while(!alphaZombie.init(randomNum(width), randomNum(height)));
		for(int i = 0; i< humans.size(); i++) while(!humans.get(i).init(randomNum(width), randomNum(height)));//Confused :)
		
	}
	
	public void update() {
		checkAdjacent();
		
		for(int i = 0; i< humans.size(); i++){
			humans.get(i).update();
		}
		for(int i = 0; i<zombies.size(); i++){
			zombies.get(i).update();
		}
		alphaZombie.update();
	}
	
	public ArrayList<Human> getHumans(){
		return humans;
	}
	
	public ArrayList<Zombie> getZombies(){
		return zombies;
	}
	
	public AlphaZombie getAlphaZombie(){
		return alphaZombie;
	}
	
	public void checkAdjacent(){
		for(int i = 0; i<zombies.size(); i++){
			for(int j = 0; j<humans.size(); j++){
				if(humans.get(j).getX() == zombies.get(i).getX()-1 && humans.get(j).getY() == zombies.get(i).getY()){
					Zombie temp = new Zombie(this);
					setColor(humans.get(j).getX(), humans.get(j).getY(), Color.BLACK);
					temp.init(humans.get(j).getX(), humans.get(j).getY());
					zombies.add(temp);
					humans.remove(j);
				}
				else if(humans.get(j).getX() == zombies.get(i).getX()+1 && humans.get(j).getY() == zombies.get(i).getY()){
					Zombie temp = new Zombie(this);
					setColor(humans.get(j).getX(), humans.get(j).getY(), Color.BLACK);
					temp.init(humans.get(j).getX(), humans.get(j).getY());
					zombies.add(temp);
					humans.remove(j);
				}
				else if(humans.get(j).getX() == zombies.get(i).getX() && humans.get(j).getY() == zombies.get(i).getY()-1){
					Zombie temp = new Zombie(this);
					setColor(humans.get(j).getX(), humans.get(j).getY(), Color.BLACK);
					temp.init(humans.get(j).getX(), humans.get(j).getY());
					zombies.add(temp);
					humans.remove(j);
				}
				else if(humans.get(j).getX() == zombies.get(i).getX() && humans.get(j).getY() == zombies.get(i).getY()+1){
					Zombie temp = new Zombie(this);
					setColor(humans.get(j).getX(), humans.get(j).getY(), Color.BLACK);
					temp.init(humans.get(j).getX(), humans.get(j).getY());
					zombies.add(temp);
					humans.remove(j);
				}
			}
		}
		
		
		for(int j = 0; j<humans.size(); j++){
			if(humans.get(j).getX() == alphaZombie.getX()-1 && humans.get(j).getY() == alphaZombie.getY()){
				Zombie temp = new Zombie(this);
				setColor(humans.get(j).getX(), humans.get(j).getY(), Color.BLACK);
				temp.init(humans.get(j).getX(), humans.get(j).getY());
				zombies.add(temp);
				humans.remove(j);
			}
			else if(humans.get(j).getX() == alphaZombie.getX()+1 && humans.get(j).getY() == alphaZombie.getY()){
				Zombie temp = new Zombie(this);
				setColor(humans.get(j).getX(), humans.get(j).getY(), Color.BLACK);
				temp.init(humans.get(j).getX(), humans.get(j).getY());
				zombies.add(temp);
				humans.remove(j);
			}
			else if(humans.get(j).getX() == alphaZombie.getX() && humans.get(j).getY() == alphaZombie.getY()-1){
				Zombie temp = new Zombie(this);
				setColor(humans.get(j).getX(), humans.get(j).getY(), Color.BLACK);
				temp.init(humans.get(j).getX(), humans.get(j).getY());
				zombies.add(temp);
				humans.remove(j);
			}
			else if(humans.get(j).getX() == alphaZombie.getX() && humans.get(j).getY() == alphaZombie.getY()+1){
				Zombie temp = new Zombie(this);
				setColor(humans.get(j).getX(), humans.get(j).getY(), Color.BLACK);
				temp.init(humans.get(j).getX(), humans.get(j).getY());
				zombies.add(temp);
				humans.remove(j);
			}
		}
	}
	
	public int getStartRiver(){
		return startOfRiver;
	}
	
	//bound is exclusive means if bounds = 30 then rand will return between 0-29
	private int randomNum(int bound){
		return new Random().nextInt(bound);
	}
}