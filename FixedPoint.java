
public class FixedPoint {

  // Each FixedPoint object gets
  // Whole value
  int w;
  // Fractional
  int f;
  // Sign
  boolean isNegative;

  // Constructors:
  public FixedPoint(/*FixedPoint this, */ int whole, int fraction) {
    this.w = whole;
    this.f = fraction;
    if (fraction > 9 || fraction < 0) {
      throw new IllegalArgumentException("You idiot: Only pass in 0-9 for fractional values");
    }
    if (whole < 0) {
      this.isNegative = true;
      this.w = -whole;
    } else {
      this.isNegative = false;
    }
  }

  public FixedPoint(/*FixedPoint this, */ boolean isNeg, int whole, int fraction) {
    this.w = whole;
    this.f = fraction;
    this.isNegative = isNeg;
    if (fraction > 9 || fraction < 0) {
      throw new IllegalArgumentException("You idiot: Only pass in 0-9 for fractional values");
    }
    if (whole < 0) {
      this.isNegative = !this.isNegative;
      this.w = -whole;
    }
  }

  public FixedPoint(/* FixedPoint this, */ int whole) {
    this.w = Math.abs(whole);
    this.f = 0;
    this.isNegative = (whole < 0);
  }

  public FixedPoint(/* FixedPoint this, */ double d) {
    // w & f should always be positive
    this.isNegative = (d < 0.0);
    d = Math.abs(d);
    // This is to deal with double's inaccuracy
    d += 0.001;
    this.w = (int)d;
    double d10 = (d - (double)this.w) * 10.0;
    this.f = (int)d10;
  }

  public boolean equals(/* FixedPoint this, */ FixedPoint b) {
    return (this.w == b.w) && (this.f == b.f) && (this.isNegative == b.isNegative);
  }

  public FixedPoint add(/* FixedPoint this, */ FixedPoint b) {
    // Handle negatives up here
    if (this.isNegative) {
      return b.sub(this.neg());
    }
    if (b.isNegative) {
      return this.sub(b.neg());
    }
    // This code only works for *positive* values
    int whole, fraction, carry;
    fraction = this.f + b.f;
    if (fraction > 9) {
      fraction = fraction % 10;
      carry = 1;
    } else {
      carry = 0;
    }
    whole = carry + this.w + b.w;
    return new FixedPoint(whole, fraction);
  }

  public FixedPoint sub(FixedPoint b) {
    // Handle negatives up here
    if (b.isNegative) {
      return this.add(b.neg());
    }
    if (this.isNegative) {
      return this.neg().add(b).neg();
    }
    //   3.0
    // - 2.9
    // ------
    //   1.-9
    int newF = this.f - b.f;
    int borrow = 0;
    if (newF < 0) {
      newF = newF + 10;
      borrow = 1;
    }
    int newW = this.w - b.w - borrow;
    return new FixedPoint(newW, newF);
  }

  public FixedPoint mul(FixedPoint b) {
    // 6.6
    // 1.1
    //
    int AwBw = this.w * b.w; // 6
    int AwBf = this.w * b.f; // 6
    int AfBw = this.f * b.w; // 6
    int AfBf = this.f * b.f; // 6
    // Round AfBf to the nearest 10, then NewF is 
    // the tens digit
    // 23 => 2
    // 25 => 3
    // (23 + 5) / 10
    int newF = (AfBf + 5) / 10; // (6 + 5) / 10 == 1
    newF = newF + AwBf % 10; // 1 + 6 % 10 = 7
    newF = newF + AfBw % 10; // 7 + 6 % 10 = 13
    int carry = 0;
    if (newF > 9) {
      carry = newF / 10;
      newF = newF % 10;
    }
    int newW = AwBw + carry; // 6
    newW = newW + AwBf / 10; // 6 + 0
    newW = newW + AfBw / 10; // 6 + 0
    return new FixedPoint(this.isNegative != b.isNegative ,newW, newF); // 6.13
  }

  public FixedPoint div(FixedPoint b) {
    // This is a total cheat, because math is hard :D
    double thisNum = this.toDouble();
    double bNum = b.toDouble();
    return new FixedPoint(thisNum / bNum);
  }

  public FixedPoint neg() {
    return new FixedPoint(!this.isNegative, this.w, this.f);
  }

  public FixedPoint abs() {
    return new FixedPoint(this.w, this.f);
  }

  public boolean lessThan(FixedPoint b) {
    if (this.isNegative != b.isNegative) {
      return this.isNegative;
    }
    boolean res = this.isNegative && b.isNegative;
    if (this.w == b.w) {
      return this.f < b.f ? !res : res;
    } else {
      return this.w < b.w ? !res : res;
    }
  }

  public boolean greaterThan(FixedPoint b) {
    return !this.lessThan(b) && !this.equals(b);
  }

  public String toString() {
    if (this.isNegative){
      return "-" + Integer.toString(w) + "." + Integer.toString(f);
    }
    return Integer.toString(w) + "." + Integer.toString(f);
  }

  public double toDouble() {
    return (double)this.w + (double)this.f / 10.0;
  }
}
