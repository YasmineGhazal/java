package com.progressoft.induction;

import java.math.BigDecimal;
import java.util.Arrays;

public class Money {
	BigDecimal value = new BigDecimal(0);
	float [] validMoneyAmounts = {.25f, .5f, 1f, 5f, 10f};
	final static Money ZERO = new Money(new BigDecimal(0), true);
	final static Money QUARTER_DINAR = new Money(new BigDecimal(0.25), true);
	final static Money HALF_DINAR = new Money(new BigDecimal(0.5), true);
	final static Money DINAR = new Money(new BigDecimal(1), true);
	final static Money FIVE_DINARS = new Money(new BigDecimal(5), true);
	final static Money TEN_DINARS = new Money(new BigDecimal(10), true);
	
	Money(BigDecimal amount) throws IllegalArgumentException {
		int i;
		for(i = 0 ; i < validMoneyAmounts.length; i++)
			if(amount.equals(new BigDecimal(validMoneyAmounts[i]))) {
				value = amount;
				break;
			}
		if(i == validMoneyAmounts.length) throw new IllegalArgumentException("invalid value");
	}
	Money(BigDecimal amount, Boolean fromInside) {
		value = amount;
	}
	
	Money add(Money valueToBeAdded) {
		return(new Money(value.add(valueToBeAdded.value), true));
	}
	
	Money subtract(Money valueToSubtract) throws IllegalArgumentException {
		Money change = new Money(value.subtract(valueToSubtract.value), true);
		if(!change.isLessThan(ZERO))
			return change;
		else {
			throw new IllegalArgumentException("invalid value"); 
		}
	}
	
	Boolean isLessThan(Money valueToCompareWith) {
		return(value.compareTo(valueToCompareWith.value) < 0) ; 
	}
	
}
