package cmpesweng2014.group1.nutty.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserServiceTest {


	private UserService us;

	@Test
	public final void testCanLogin() {
		
		if(us.canLogin("mehmetalparslanbal@gmail.com", "mabal67")==null){
			fail("cannot login");
		}else{
			assertEquals("Can Login",1,1);
		}
		
	}

	@Test
	public final void testGetNumberOfFollowers() {
		assertEquals("Followers must be 6",6,us.getNumberOfFollowers(21));
	}

	@Test
	public final void testGetNumberOfFollowing() {
		assertEquals("Followers must be 6",6,us.getNumberOfFollowing(21));
	}

	@Test
	public final void testGetFollowStatus1() {
		assertEquals("3 must follow 1","true",us.getFollowStatus(3,1));
	}
	public final void testGetFollowStatus2() {
		assertEquals("3 should not follow 5","false",us.getFollowStatus(3,5));
	}
	@Test
	public final void testIsFollower1() {
		assertEquals("3 must follow 1",true,us.isFollower(3,1));
	}

	@Test
	public final void testIsFollower2() {
		assertEquals("3 should not follow 5",false,us.isFollower(3,5));
	}
	

	@Test
	public final void testGetBadge() {
		assertEquals("21 must be beginner","Beginner",us.getBadge(21));
	}

	@Test
	public final void testGetScore() {
		assertEquals("Score of the 21 must be zero",0,us.getScore(21));
	}

}
