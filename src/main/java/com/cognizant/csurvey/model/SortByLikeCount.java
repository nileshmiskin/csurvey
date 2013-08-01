package com.cognizant.csurvey.model;

import java.util.Comparator;

public class SortByLikeCount implements Comparator<AggregateFeedbackStats> {

	@Override
	public int compare(AggregateFeedbackStats o1, AggregateFeedbackStats o2) {
		int retVal = -1;
		if (o1.getLikeCount() == o2.getLikeCount())
			retVal = 0;
		else if (o1.getLikeCount() < o2.getLikeCount())
			retVal = 1;
		return retVal;
	}

}
