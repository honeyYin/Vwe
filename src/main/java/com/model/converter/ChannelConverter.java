package com.model.converter;

import com.entity.Channel;
import com.model.ChannelModel;


public class ChannelConverter {
	public ChannelModel entityToModel(Channel entity) {
		if(entity == null){
			return null;
		}
		ChannelModel channel = new ChannelModel();
		channel.setCreateTime(entity.getCreateTime());
		channel.setUpdateTime(entity.getUpdateTime());
		channel.setDisabled(entity.getDisabled());
		channel.setId(entity.getId());
		channel.setName(entity.getName());
		channel.setParentId(entity.getParentId());
		channel.setPriority(entity.getPriority());
		return channel;
	}
}
