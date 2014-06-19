public enum MobClass
{
  FAST("fast", 10, 20, 15, 5),STRONG("strong", 25, 6, 10, 15),TOUGH("tough", 40, 5, 10, 10),AGILE("agile", 10, 20, 25, 5);

  private String className;
  private int health;
  private int speed;
  private int acceleration;
  private int damage;
  
  private MobClass(String name, int health, int speed, int acceleration, int damage){
    this.className = name;
    this.health = health;
    this.speed = speed;
    this.acceleration = acceleration;
    this.damage = damage;
  }
  
  public String getClassName(){ return this.className; }
  
  public int getHealth(){ return this.health; }
  
  public int getSpeed(){ return this.speed; }
  
  public int getAcceleration(){ return this.acceleration; }
  
  public int getDamage(){ return this.damage; }
} 
