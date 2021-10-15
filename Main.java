public class Main {
  private static void Check(String expr, FixedPoint val, int whole, int fraction) {
    FixedPoint check = new FixedPoint(whole, fraction);
    System.out.println(expr);
    if (val.equals(check)) {
      System.out.println("Correct!");
    } else {
      System.out.println("Wrong: Got this value instead:");
      System.out.print(val);
      System.out.println();
    }
  }

  public static void main(String[] args) {
    try {
      FixedPoint zero = new FixedPoint(0); // 0.0
      FixedPoint one = new FixedPoint(1); // 1.0
      FixedPoint two = new FixedPoint(2); // 2.0
      FixedPoint v11 = new FixedPoint(1, 1); // 1.1
      FixedPoint v34 = new FixedPoint(3, 4); // 3.4
      FixedPoint v66 = new FixedPoint(6.6); // 6.6
      FixedPoint v30 = new FixedPoint(3);
      FixedPoint v29 = new FixedPoint(2.9);
      FixedPoint neg34 = new FixedPoint(-3, 4);
      FixedPoint v37 = new FixedPoint(3, 7);
      FixedPoint v49 = new FixedPoint(4, 9);
      FixedPoint negOne = one.neg();
      FixedPoint negTwo = two.neg();
      FixedPoint v86 = v37.add(v49);
      FixedPoint elevenPoint5 = v86.add(v29);
      FixedPoint negativeOne = new FixedPoint(true, 1, 0);
      if (zero.equals(one)) {
        System.out.println("The universe is broken!");
        return;
      }

      // Check equals for basic functioning
      if (!v34.equals(v34) || !one.equals(one) || v34.equals(one) || one.equals(v34)) {
        System.out.println("equals seems broken. Without that working, nothing else will work properly.");
        return;
      }
      if (v34.equals(v34.neg())) {
        System.out.println("neg seems broken.");
        return;
      }
      if (one.equals(negativeOne)) {
        System.out.println("neg seems pretty broken.");
        return;
      }
      if (!negativeOne.equals(one.neg())) {
        System.out.println("neg seems quite broken.");
        return;
      }
      
      // Easy Add/Subtract:
      Check("3.4 + 0.0 = 3.4", v34.add(zero), 3, 4);
      Check("3.4 - 0.0 = 3.4", v34.sub(zero), 3, 4);
      Check("0.0 + 3.4 = 3.4", zero.add(v34), 3, 4);
      // Slightly harder
      Check("3.4 + 1.0 = 4.4", v34.add(one), 4, 4);
      Check("3.4 - 1.0 = 2.4", v34.sub(one), 2, 4);
      // Harder still
      Check("3.4 + 1.1 = 4.5", v34.add(v11), 4, 5);
      Check("3.4 + 6.6 = 10.0", v34.add(v66), 10, 0);
      Check("3.0 - 2.9 = 0.1", v30.sub(v29), 0, 1);
      // Easy Multiply tests
      Check("3.4 * 0.0 = 3.4", v34.mul(zero), 0, 0);
      Check("3.4 * 1.0 = 3.4", v34.mul(one), 3, 4);
      // A little harder multiply
      Check("2.0 * 2.0 = 4.0", two.mul(two), 4, 0);
      Check("1.1 * 2.0 = 2.2", v11.mul(two), 2, 2);
      // Rounding?
      Check("3.4 * 1.1 = 3.7", v34.mul(v11), 3, 7);
      Check("6.6 * 1.1 = 7.3", v66.mul(v11), 7, 3);
      // Divide is arguably complicated
      Check("3.4 / 1.0 = 3.4", v34.div(one), 3, 4);
      Check("6.6 / 2.0 = 3.3", v66.div(two), 3, 3);
      Check("6.6 / 1.1 = 6.0", v66.div(v11), 6, 0);
      // FixedPoint bigVal = new FixedPoint(2147000000,0);
      // Check("large / 1.0", bigVal.div(one), 2147000000, 0);

      // Negative stuff:
      Check("3.4 + -2.0 = 1.4", v34.add(negTwo), 1, 4);
      Check("3.4 - -2.0 = 5.4", v34.sub(negTwo), 5, 4);
      Check("-2.0 + -2.0 = -4.0", negTwo.add(negTwo), -4, 0);
      Check("-2.0 - -2.0 = 0.0", negTwo.sub(negTwo), 0, 0);
      Check("6.6 * -1.1 = -7.3", v66.mul(v11.neg()), -7, 3);
      Check("-6.6 * 1.1 = -7.3", v66.neg().mul(v11), -7, 3);
      Check("-6.6 * -1.1 = 7.3", v66.neg().mul(v11.neg()), 7, 3);

    } catch (Exception e) {
      System.out.println("Your program crashed.");
      System.out.println(e.toString());
    }
  }
}
