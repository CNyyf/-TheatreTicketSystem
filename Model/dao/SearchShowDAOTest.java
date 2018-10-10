package dao;

import java.sql.Timestamp;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class SearchShowDAOTest extends TestCase {

	private SearchShowDAO dao;
	
	@Before
	public void setUp() throws Exception {
		dao = new SearchShowDAO();
	}

	@After
	public void tearDown() throws Exception {
		dao.close();
	}

	@Test
	public void test() {
		ArrayList<String> titles_expect = new ArrayList<String>();
		titles_expect.add("�Ϻ�����ӭ�����");
		titles_expect.add("�����ּ�");
		assertEquals(titles_expect, dao.searchTitlesByDay(Timestamp.valueOf("2018-10-10 00:00:00")));
	}

}
