package cmpesweng2014.group1.nutty.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.EventDao;
import cmpesweng2014.group1.nutty.model.Event;


@Component
public class EventService {

	@Autowired
	private EventDao eventDao;
	
	public EventDao getEventDao() {
		return eventDao;
	}

	public void setEventDao(EventDao eventDao) {
		this.eventDao = eventDao;
	}
	
	public List<Event> getAllRecentEventsOfUser(long user_id){
		long now = System.currentTimeMillis();	
		List<Event> allRecentEvents = new ArrayList<Event>();		
		allRecentEvents.addAll(eventDao.getRecentCommentEventsOfUser(now, user_id));
		allRecentEvents.addAll(eventDao.getRecentRecipeEventsOfUser(now, user_id));
		Collections.sort(allRecentEvents);
		return allRecentEvents;
	}
}