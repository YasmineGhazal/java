package com.progressoft.induction;

public interface Snack {
	int quantity ();
	void buyOne();
	
}

class Chips implements Snack { 
	static int snackQuantity = 10;
	Money price;
	
	Chips() {
		price = Money.HALF_DINAR;
	}
	public int quantity () {
		return snackQuantity;
	}
	public void buyOne() {
		snackQuantity -= 1;
	}	
}

class ChewingGum implements Snack { 
	static int snackQuantity = 10;
	Money price;

	ChewingGum() {
		price = Money.QUARTER_DINAR;
	}
	public int quantity () {
		return snackQuantity;
	}
	public void buyOne() {
		snackQuantity -= 1;
	}
}

class Chocolates implements Snack { 
	static int snackQuantity = 10;
	Money price;

	Chocolates() {
		price = Money.DINAR;
	}
	public int quantity () {
		return snackQuantity;
	}
	public void buyOne() {
		snackQuantity -= 1;
	}
}