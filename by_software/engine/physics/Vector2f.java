public class Vector2f
{
  private double x;
  private double y;

  //constructor
  public Vector2f(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  //copy constructor
  public Vector2f(Vector2f another)
  {
    this.x = another.getX();
    this.y = another.getY();
  }

  //setters
  public void set(double x, double y)
  {
    this.x = x;
    this.y = y;
  }

  public void set(Vector2f newVec)
  {
    this.set(newVec.getX(), newVec.getY());
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
  public void add(final Vector2f that)
  {
    this.x += that.x;
    this.y += that.y;
  }

  public void sub(final Vector2f that)
  {
    this.x -= that.x;
    this.y -= that.y;
  }

  public void mul(final Vector2f that)
  {
    this.x *= that.x;
    this.y *= that.y;
  }

  public void div(final Vector2f that)
  {
    this.x /= that.x;
    this.y /= that.y;
  }


  //add, sub, mul, div (ALL STATIC)
  public static Vector2f add(Vector2f vec1, Vector2f vec2)
  {
    Vector2f retVec = new Vector2f(vec1.getX(), vec1.getY());
    retVec.add(vec2);
    return retVec;
  }

  public static Vector2f sub(Vector2f vec1, Vector2f vec2)
  {
    Vector2f retVec = new Vector2f(vec1.getX(), vec1.getY());
    retVec.sub(vec2);
    return retVec;
  }

  public static Vector2f mul(Vector2f vec1, Vector2f vec2)
  {
    Vector2f retVec = new Vector2f(vec1.getX(), vec1.getY());
    retVec.mul(vec2);
    return retVec;
  }

  public static Vector2f div(Vector2f vec1, Vector2f vec2)
  {
    Vector2f retVec = new Vector2f(vec1.getX(), vec1.getY());
    retVec.div(vec2);
    return retVec;
  }


  //to String
  public String toString()
  {
    return "x = " + x + ", y = " + y;
  }


  //getters
  public double getX(){return x;}
  public double getY(){return y;}

  /*=========TEST===========*/
  public static void main(String[] args)
  {
    final Vector2f origin = new Vector2f(0f, 0f);
    final Vector2f vecA   = new Vector2f(2f, 5f);
    System.out.println("orig: " + origin);
    System.out.println("vecA: " + vecA);
    System.out.println("orig + vecA: " + Vector2f.sub(vecA, vecA));
    System.out.println("orig: " + origin);
    System.out.println("vecA: " + vecA);
  }
}