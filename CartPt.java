import javalib.worldimages.Posn;

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
}