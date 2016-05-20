package com.model;

import java.util.Comparator;

public class ChannelSorter implements Comparator<ChannelModel> {

	public int compare(ChannelModel o1, ChannelModel o2) {
		
		return (int) (o1.getPriority() - o2.getPriority());
	}

}
