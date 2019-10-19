package com.progressoft.induction;

public class SnackMachine {
	final static int DEFAULT_QUANTITY = 10;
	Money total;
	static Money inTransaction;
	ChewingGum chewingGum;
	Chips chips;
	Chocolates chocolate;

	SnackMachine() {
		total = Money.ZERO;
		chewingGum = new ChewingGum();
		chips = new Chips();
		chocolate = new Chocolates();
		inTransaction = Money.ZERO;
	}
	
	double moneyInside() {
		return total.value.doubleValue();
	}

	void insertMoney(Money insertedMoney) {
		inTransaction = inTransaction.add(insertedMoney);
	}
	
	static Money moneyInTransaction() {
		return inTransaction;
	}
	
	Money buySnack(SnackType snack) {
		Money price = Money.ZERO;
		switch(snack) {
			case CHEWING_GUM: {
				if(!inTransaction.isLessThan(chewingGum.price)) {
					if(chewingGum.quantity() > 0) {
						price = chewingGum.price;
						chewingGum.buyOne();
					}
					else 
						displayMsgQuantityIs0();
				}  else  displayMsgInsertedMoneyIsNotEnogh();
				break;
			}
			case CHIPS: {
				if(!inTransaction.isLessThan(chips.price)) {
					if(chips.quantity() > 0) {
						price = chips.price;
						chips.buyOne();
					}
					else 
						displayMsgQuantityIs0();
				}  else  displayMsgInsertedMoneyIsNotEnogh();
				break;
			}
			case CHOCOLATE: {
				if(!inTransaction.isLessThan(chocolate.price)) {
					if(chocolate.quantity() > 0) {
						price = chocolate.price;
						chocolate.buyOne();
					}
					else 
						displayMsgQuantityIs0();
				}  else  displayMsgInsertedMoneyIsNotEnogh();
				break;
			}
		}
		inTransaction = inTransaction.subtract(price);
		return inTransaction;
	}
	
	Snack chewingGums() {
		return chewingGum;
	}
	
	Snack chips() {
		return chips;
	}
	
	Snack chocolates() {
		return chocolate;
	}
	
	void displayMsgInsertedMoneyIsNotEnogh() {
		System.err.println("inserted money isn't enogh to buy this item please insert money again");
	}
	
	void displayMsgQuantityIs0() {
		System.err.println("sorry, please try to choose another type");
	}
}
