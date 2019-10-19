package com.progressoft.induction;

import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
	static Scanner sc = new Scanner(System.in);
	static SnackMachine snackMachine = new SnackMachine();
	static boolean exit = false;
	
	public static void main(String [] args) {
		while (!exit) {
			System.out.println("please insert money");
			Money money = new Money(new BigDecimal(sc.next()));
			snackMachine.insertMoney(money);
			System.out.println("money in transaction is: "+ SnackMachine.moneyInTransaction().value);
			System.out.println("for \n\tchips insert 1\n\tchocolate insert 2\n\tgum insert 3");
			int choes = sc.nextInt();
			switch(choes) {
			case 1:
				snackMachine.buySnack(SnackType.CHIPS);
				System.out.println("tha change is "+ getChange(snackMachine.chips.price));
				exit();
				break;
			case 2:
				snackMachine.buySnack(SnackType.CHOCOLATE);
				System.out.println("tha change is "+ getChange(snackMachine.chocolate.price));
				exit();
				break;
			case 3:
				snackMachine.buySnack(SnackType.CHEWING_GUM);
				System.out.println("tha change is "+ getChange(snackMachine.chewingGum.price));
				exit();
				break;
			default :
				System.out.println("it seems you've inserted invalid type, please insert money again");
			}	
		}	
		snackMachine.total = snackMachine.total.add(SnackMachine.inTransaction);

	}
	
	static double getChange(Money price) {
		return SnackMachine.moneyInTransaction().value.doubleValue();
	}
	
	static void exit() {
		System.out.println("in the machine : "+ snackMachine.chips.quantity()+" Chips, "+ snackMachine.chocolate.quantity()+" chocolates, and "+ snackMachine.chewingGum.quantity()+" chewingGum");
		System.out.println("Do you want to buy another thing? enter Y for yes otherways please press any other key...");
		if(!sc.next().equalsIgnoreCase("Y"))
			exit = true;
	}
}