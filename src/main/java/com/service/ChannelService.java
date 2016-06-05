package com.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.ChannelDao;
import com.entity.Channel;
import com.model.ChannelModel;
import com.model.converter.ChannelConverter;

@Service
public class ChannelService {

	@Autowired
	private ChannelDao cateDao;
	
	private static final ChannelConverter CONVERTER = new ChannelConverter();
	public List<Channel> getAllCategories(){
		return cateDao.getAllCategory();
	}
	
	public Integer getPageSizeByChannel(Long channelId){
		if(channelId ==null){
			return 0;
		}
		return cateDao.getPageSizeByChannel(channelId);
	}
	public List<Channel> getCategoriesByParent(Long parentId){
		if(parentId != null){
			return cateDao.getCategoriesByParent(parentId);
		}else{
			return cateDao.getRootCategory();
		}
		
	}
	public ChannelModel queryByRoot() throws Exception{
		ChannelModel result = null;
		List<Channel> temp = cateDao.getAllCategory();
		if(null != temp && temp.size() > 0){
			result = CONVERTER.entityToModel(temp.get(0));
		}	
		return result;
	}
	public ChannelModel buildTree(ChannelModel temp, Map<Long, List<ChannelModel>> map){
		//得到子树
		List<ChannelModel> list = map.get(temp.getId());
		if(null != list){
			temp.setSets(list);
			for(ChannelModel cJcChannel : list){
				cJcChannel = buildTree(cJcChannel, map);
			}
		}
		return temp;

	}
	public void updateBatch(long[] ids, int[] priority){
		if (ids == null || priority == null || ids.length < 1 || priority.length < 0 || ids.length != priority.length) {
			return ;
		}
		
		int length = ids.length;
		for (int i = 0; i < length; i++) {
			updatePriority(ids[i], priority[i]);
		}
	}
	public Channel findOrAddByName(String name){
		List<Channel> channels = cateDao.findByName(name);
		if(CollectionUtils.isEmpty(channels)){
			Channel channel= new Channel();
			channel.setDisabled(0);
			channel.setIsDeploy(0);
			channel.setName(name);
			channel.setParentId(0l);
			channel.setPriority(10);
			channel.setPageSize(10);
			channel = cateDao.save(channel);
			return channel;
		}else{
			return channels.get(0);
		}
		
	}
	void updatePriority(long id, int priority){
		Channel channel = cateDao.find(id);
		channel.setPriority(priority);
		channel.setUpdateTime(new Date());
		cateDao.save(channel);
	}
}
