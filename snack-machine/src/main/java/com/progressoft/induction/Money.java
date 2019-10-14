package com.progressoft.induction;

import java.math.BigDecimal;

public class Money {
	BigDecimal value;
	Money(BigDecimal amount) {
		value = amount;
	}
	
	Money add(Money valueToBeAdded) {
		return(new Money(value.add(valueToBeAdded.value)));
	}
	
	Money subtract(Money valueToBeAdded) {
		return(new Money(value.subtract(valueToBeAdded.value)));
	}
	
	Boolean isLessThan(Money valueToCompareWith) {
		return(value.compareTo(valueToCompareWith.value) < 0) ; 
	}
	
}
