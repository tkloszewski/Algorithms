package com.smart.tkl.dynamic;

import java.util.ArrayList;
import java.util.List;

public class KnapsackTest {

  public static void main(String[] args) {
    testSack1();
    testSack2();
    testSack3();
  }

  private static void testSack1() {
    List<Item> items = new ArrayList<>();
    items.add(new Item("Guitar", 1500, 1));
    items.add(new Item("Laptop", 2000, 3));
    items.add(new Item("Stereo", 3000 ,4));

    Thief thief = new Thief();
    Sack sack = thief.knapSack(items, 4);

    System.out.println("Stealing sack 1.....");
    printSack(sack);
    System.out.println("\nSack 1 stolen.");
    System.out.println("------------------------------------");
  }

  private static void testSack2() {
    List<Item> items = new ArrayList<>();
    items.add(new Item("Guitar", 1500, 1));
    items.add(new Item("Laptop", 2000, 3));
    items.add(new Item("Stereo", 3000 ,4));
    items.add(new Item("Iphone", 2000 ,1));

    Thief thief = new Thief();
    Sack sack = thief.knapSack(items, 4);

    System.out.println("Stealing sack 2.....");
    printSack(sack);
    System.out.println("\nSack 2 stolen.");
    System.out.println("------------------------------------");
  }

  private static void testSack3() {
    Item water = new Item("Guitar", 10, 3);
    Item book = new Item("Laptop", 3, 1);
    Item food = new Item("Stereo", 9 ,2);
    Item jacket = new Item("Jacket", 5, 2);
    Item camera = new Item("Camera", 6, 1);

    List<Item> items = new ArrayList<>();
    items.add(water);
    items.add(book);
    items.add(food);
    items.add(jacket);
    items.add(camera);

    Thief thief = new Thief();
    Sack sack = thief.knapSack(items, 6);

    System.out.println("Stealing sack 3.....");
    printSack(sack);
    System.out.println("\nSack 3 stolen.");
    System.out.println("------------------------------------");
  }

  private static void printSack(Sack sack) {
    System.out.println("Sack total value: " + sack.getItemsValue());
    System.out.printf("Sack items weight/sack_weight: %d/%d%n", sack.getItemsWeight(), sack.getWeight());
    System.out.print("Sack items: [");
    for(Item item : sack.getItems()) {
      System.out.print(item.name + ",");
    }
    System.out.print("]");
  }



}
