import java.awt.Color;

import javalib.worldimages.*;

class CartPt extends Posn {
    public CartPt(int x, int y) {
        super(x, y);
    }
    
    // return a CartPt with both its coordinates multiplied by scalar
    CartPt scale(int scalar) {
        return new CartPt(this.x * scalar, this.y * scalar);
    }
    
    // return a CartPt translated down and right by factor
    CartPt translate(int factor) {
        return new CartPt(this.x + factor, this.y + factor);
    }
    
    // return a CartPt scaled by the given factor, 
    // then translated by half the factor
    CartPt toPixel(int factor) {
        return this.scale(factor).translate(factor / 2);
    }
    
    CartPt midPoint(CartPt other) {
        return new CartPt((this.x + other.x) / 2,
                          (this.y + other.y) / 2);
    }
    
    WorldImage draw(Color c) {
        return new RectangleImage(this.toPixel(Maze.CELL_SIZE), Maze.CELL_SIZE, Maze.CELL_SIZE, c);
    }
    
 // Meant to be used on adjacent points to find the first point
    // on a wall between them when they're in pixel format.
    CartPt findPerpPoint(CartPt pt) {
        if (this.x > pt.x) {
             return new CartPt(this.x, this.y + 1);
        }
        else if (this.x < pt.x) {
            return new CartPt(pt.x, pt.y + 1);
        }
        if (this.y > pt.y) {
            return new CartPt(this.x + 1, this.y);
       }
       else {
           return new CartPt(pt.x + 1, pt.y);
       }
    }
    
    // Meant to be used on adjacent points to find the second point
    // on a wall between them when they're in pixel format.
    CartPt findBiggerPoint(CartPt pt) {
        if (this.x > pt.x) {
             return this;
        }
        else if (this.x < pt.x) {
            return pt;
        }
        if (this.y > pt.y) {
            return this;
       }
       else {
           return pt;
       }
    }
}