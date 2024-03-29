package cmpesweng2014.group1.nutty.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cmpesweng2014.group1.nutty.dao.EventDao;
import cmpesweng2014.group1.nutty.model.Event;
import cmpesweng2014.group1.nutty.model.User;


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
		allRecentEvents.addAll(eventDao.getRecentCommentEventsOfUser(now, 7, user_id));
		allRecentEvents.addAll(eventDao.getRecentRecipeEventsOfUser(now, 7, user_id));
		allRecentEvents.addAll(eventDao.getRecentShareEventsOfUser(now, 7, user_id));
		allRecentEvents.addAll(eventDao.getRecentEatEventsOfUser(now, 7, user_id));
		allRecentEvents.addAll(eventDao.getRecentLikeEventsOfUser(now, 7, user_id));
		allRecentEvents.addAll(eventDao.getRecentRateEventsOfUser(now, 7, user_id));
		allRecentEvents.addAll(eventDao.getRecentFollowEventsOfUser(now, 7, user_id));
		allRecentEvents.addAll(eventDao.getRecentBadgeEventsOfUser(now, 7, user_id));
		Collections.sort(allRecentEvents, Collections.reverseOrder());
		return allRecentEvents;
	}
	
	public List<Event> getAllRecentEventsOfUserList(User[] users){
		long now = System.currentTimeMillis();	
		List<Event> allRecentEvents = new ArrayList<Event>();
		for(int i=0; i<users.length; i++){
			long user_id = users[i].getId();
			allRecentEvents.addAll(eventDao.getRecentCommentEventsOfUser(now, 7, user_id));
			allRecentEvents.addAll(eventDao.getRecentRecipeEventsOfUser(now, 7, user_id));
			allRecentEvents.addAll(eventDao.getRecentShareEventsOfUser(now, 7, user_id));
			allRecentEvents.addAll(eventDao.getRecentEatEventsOfUser(now, 7, user_id));
			allRecentEvents.addAll(eventDao.getRecentLikeEventsOfUser(now, 7, user_id));
			allRecentEvents.addAll(eventDao.getRecentRateEventsOfUser(now, 7, user_id));
			allRecentEvents.addAll(eventDao.getRecentFollowEventsOfUser(now, 7, user_id));
			allRecentEvents.addAll(eventDao.getRecentBadgeEventsOfUser(now, 7, user_id));
		}
		Collections.sort(allRecentEvents, Collections.reverseOrder());
		return allRecentEvents;
	}
}