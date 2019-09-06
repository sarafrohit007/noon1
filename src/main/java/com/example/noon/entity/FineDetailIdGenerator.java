package com.example.noon.entity;

import java.util.Comparator;

public class FineDetailIdGenerator implements Comparator<FineDetailInfo>{

	@Override
	public int compare(FineDetailInfo fineDetail1, FineDetailInfo fineDetail2) {
		return fineDetail1.getId() - fineDetail2.getId();
	}

}
