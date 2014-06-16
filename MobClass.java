public enum Mobclass
{
  FAST("fast", 10, 20, 15, 5),STRONG("strong", 25, 6, 10, 15),TOUGH("tough", 40, 5, 10, 10),AGILE("agile", 10, 20, 25, 5);

  private String className;
  private short health;
  private short speed;
  private short acceleration;
  private short damage;
  
  private Suit(String name, short health, short speed, short acceleration, short damage){
    this.className = name;
    this.health = health;
    this.speed = speed;
    this.acceleration = acceleration;
    this.damage = damage;
  }
  
  public String getClassName(){ return this.speed; }
  
  public short getHealth(){ return this.health; }
  
  public short getSpeed(){ return this.speed; }
  
  public short getAcceleration(){ return this.acceleration; }
  
  public short getDamage(){ return this.damage; }
} 
