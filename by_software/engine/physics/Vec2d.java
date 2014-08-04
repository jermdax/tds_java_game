package by_software.engine.physics;

public class Vec2d
{
  public double x;
  public double y;

  //constructor
  public Vec2d(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  //copy constructor
  public Vec2d(Vec2d another)
  {
    this.x = another.x;
    this.y = another.x;
  }

  //setters
  public void set(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public void set(Vec2d newVec)
  {
    this.set(newVec.x, newVec.y);
  }

  public void setX(double newX)
  {
    x = newX;
  }

  public void setY(double newY)
  {
    y = newY;
  }


  //add, sub, mul, div
  public void add(final Vec2d that)
  {
    this.x += that.x;
    this.y += that.y;
  }

  public void sub(final Vec2d that)
  {
    this.x -= that.x;
    this.y -= that.y;
  }

  public void mul(final Vec2d that)
  {
    this.x *= that.x;
    this.y *= that.y;
  }

  public void div(final Vec2d that)
  {
    this.x /= that.x;
    this.y /= that.y;
  }


  //add, sub, mul, div (ALL STATIC)
  public static Vec2d add(Vec2d vec1, Vec2d vec2)
  {
    Vec2d retVec = new Vec2d(vec1.x, vec1.y);
    retVec.add(vec2);
    return retVec;
  }

  public static Vec2d sub(Vec2d vec1, Vec2d vec2)
  {
    Vec2d retVec = new Vec2d(vec1.x, vec1.y);
    retVec.sub(vec2);
    return retVec;
  }

  public static Vec2d mul(Vec2d vec1, Vec2d vec2)
  {
    Vec2d retVec = new Vec2d(vec1.x, vec1.y);
    retVec.mul(vec2);
    return retVec;
  }

  public static Vec2d div(Vec2d vec1, Vec2d vec2)
  {
    Vec2d retVec = new Vec2d(vec1.x, vec1.y);
    retVec.div(vec2);
    return retVec;
  }


  //to String
  public String toString()
  {
    return "x = " + x + ", y = " + y;
  }


  //getters
  public Vec2d copy(){return new Vec2d(this);}
  /*=========TEST===========*/
  public static void main(String[] args)
  {
    final Vec2d origin = new Vec2d(0f, 0f);
    final Vec2d vecA   = new Vec2d(2f, 5f);
    System.out.println("orig: " + origin);
    System.out.println("vecA: " + vecA);
    System.out.println("orig + vecA: " + Vec2d.sub(vecA, vecA));
    System.out.println("orig: " + origin);
    System.out.println("vecA: " + vecA);
  }
}