package Mxcomplier.IR.Operand;

import java.util.ArrayList;

public class PhysicalRegister {
    public int id;
    public String name;

    public static PhysicalRegister x0 = new PhysicalRegister(0, "x0");
    public static PhysicalRegister ra = new PhysicalRegister(1, "ra");
    public static PhysicalRegister sp = new PhysicalRegister(2, "sp");
    public static PhysicalRegister gp = new PhysicalRegister(3, "gp");
    public static PhysicalRegister tp = new PhysicalRegister(4, "tp");
    public static PhysicalRegister t0 = new PhysicalRegister(5, "t0");
    public static PhysicalRegister t1 = new PhysicalRegister(6, "t1");
    public static PhysicalRegister t2 = new PhysicalRegister(7, "t2");
    public static PhysicalRegister s0 = new PhysicalRegister(8, "s0");
    public static PhysicalRegister s1 = new PhysicalRegister(9, "s1");
    public static PhysicalRegister a0 = new PhysicalRegister(10, "a0");
    public static PhysicalRegister a1 = new PhysicalRegister(11, "a1");
    public static PhysicalRegister a2 = new PhysicalRegister(12, "a2");
    public static PhysicalRegister a3 = new PhysicalRegister(13, "a3");
    public static PhysicalRegister a4 = new PhysicalRegister(14, "a4");
    public static PhysicalRegister a5 = new PhysicalRegister(15, "a5");
    public static PhysicalRegister a6 = new PhysicalRegister(16, "a6");
    public static PhysicalRegister a7 = new PhysicalRegister(17, "a7");
    public static PhysicalRegister s2 = new PhysicalRegister(18, "s2");
    public static PhysicalRegister s3 = new PhysicalRegister(19, "s3");
    public static PhysicalRegister s4 = new PhysicalRegister(20, "s4");
    public static PhysicalRegister s5 = new PhysicalRegister(21, "s5");
    public static PhysicalRegister s6 = new PhysicalRegister(22, "s6");
    public static PhysicalRegister s7 = new PhysicalRegister(23, "s7");
    public static PhysicalRegister s8 = new PhysicalRegister(24, "s8");
    public static PhysicalRegister s9 = new PhysicalRegister(25, "s9");
    public static PhysicalRegister s10 = new PhysicalRegister(26, "s10");
    public static PhysicalRegister s11= new PhysicalRegister(27, "s11");
    public static PhysicalRegister t3 = new PhysicalRegister(28, "t3");
    public static PhysicalRegister t4 = new PhysicalRegister(29, "t4");
    public static PhysicalRegister t5 = new PhysicalRegister(30, "t5");
    public static PhysicalRegister t6 = new PhysicalRegister(31, "t6");


    public static ArrayList<PhysicalRegister> callee = new ArrayList<>() {{
       // add(PhysicalRegister.sp);
       // add(PhysicalRegister.s0);
        add(PhysicalRegister.s1);
        add(PhysicalRegister.s2);
        add(PhysicalRegister.s3);
        add(PhysicalRegister.s4);
        add(PhysicalRegister.s5);
        add(PhysicalRegister.s6);
        add(PhysicalRegister.s7);
        add(PhysicalRegister.s8);
        add(PhysicalRegister.s9);
        add(PhysicalRegister.s10);
        add(PhysicalRegister.s11);
    }};

    public static ArrayList<PhysicalRegister> caller = new ArrayList<>() {{
        add(PhysicalRegister.ra);
       // add(PhysicalRegister.t0);
       // add(PhysicalRegister.t1);
       // add(PhysicalRegister.t2);
      //  add(PhysicalRegister.a0);
        add(PhysicalRegister.a1);
        add(PhysicalRegister.a2);
        add(PhysicalRegister.a3);
        add(PhysicalRegister.a4);
        add(PhysicalRegister.a5);
        add(PhysicalRegister.a6);
        add(PhysicalRegister.a7);
        add(PhysicalRegister.t3);
        add(PhysicalRegister.t4);
        add(PhysicalRegister.t5);
        add(PhysicalRegister.t6);
    }};

    public static ArrayList<PhysicalRegister> tmp = new ArrayList<>() {{
         add(PhysicalRegister.t0);
         add(PhysicalRegister.t1);
         add(PhysicalRegister.t2);
    }};

    public static ArrayList<PhysicalRegister> registers = new ArrayList<>() {{

        add(PhysicalRegister.s1);
        add(PhysicalRegister.s2);
        add(PhysicalRegister.s3);
        add(PhysicalRegister.s4);
        add(PhysicalRegister.s5);
        add(PhysicalRegister.s6);
        add(PhysicalRegister.s7);
        add(PhysicalRegister.s8);
        add(PhysicalRegister.s9);
        add(PhysicalRegister.s10);
        add(PhysicalRegister.s11);
        /*add(PhysicalRegister.a1);
        add(PhysicalRegister.a2);
        add(PhysicalRegister.a3);
        add(PhysicalRegister.a4);
        add(PhysicalRegister.a5);*/
        add(PhysicalRegister.a6);
        add(PhysicalRegister.a7);
        add(PhysicalRegister.t3);
        add(PhysicalRegister.t4);
        add(PhysicalRegister.t5);
        add(PhysicalRegister.t6);

    }};

    public PhysicalRegister(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
