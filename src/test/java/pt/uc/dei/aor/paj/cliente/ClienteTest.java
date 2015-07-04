
package pt.uc.dei.aor.paj.cliente;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ClienteTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}

	@After
	public void cleanUpStreams() {
	    System.setOut(null);
	}
	
	@Test
	public void test_client_with_empty_args() {
		Cliente.main(new String[]{});
		assertEquals("usage:", outContent.toString().substring(0, 6));
	}
	
	@Test
	public void test_client_with_countUsers() {
		Cliente.main(new String[]{"countUsers"});
		assertEquals("Número de utili", outContent.toString().substring(0, 15));
	}

	@Test
	public void test_client_with_countLoggedUsers() {
		Cliente.main(new String[]{"countUsers", "-l"});
		assertEquals("Número de utili", outContent.toString().substring(0, 15));
	}

	@Test
	public void test_client_with_countMusics() {
		Cliente.main(new String[]{"countMusics"});
		assertEquals("Número total de", outContent.toString().substring(0, 15));
	}
	
	@Test
	public void test_client_with_countPlaylists() {
		Cliente.main(new String[]{"countPlaylists"});
		assertEquals("Número de playl", outContent.toString().substring(0, 15));
	}
	
	@Test
	public void test_client_with_listusers() {
		Cliente.main(new String[]{"listUsers"});
		assertFalse("usage:".equals(outContent.toString().substring(0, 6)));
	}
	
	@Test
	public void test_client_with_listuserslogged() {
		Cliente.main(new String[]{"listUsers", "-l"});
		assertFalse("usage:".equals(outContent.toString().substring(0, 6)));
	}
	
	@Test
	public void test_client_with_listPlaylists() {
		Cliente.main(new String[]{"listPlaylists"});
		assertFalse("usage:".equals(outContent.toString().substring(0, 6)));
	}
	
	@Test
	public void test_client_with_listMusics() {
		Cliente.main(new String[]{"listMusics"});
		assertFalse("usage:".equals(outContent.toString().substring(0, 6)));
	}
	
	@Test
	public void test_client_with_listMusicswithPlaylist() {
		Cliente.main(new String[]{"listMusics", "-p", "1"});
		assertFalse("usage:".equals(outContent.toString().substring(0, 6)));
	}
	
	@Test
	public void test_client_with_listMusicswithUser() {
		Cliente.main(new String[]{"listMusics", "-u", "6"});
		assertFalse("usage:".equals(outContent.toString().substring(0, 6)));
	}
	
	@Test
	public void test_client_with_showUser() {
		Cliente.main(new String[]{"showUser", "6"});
		assertFalse("usage:".equals(outContent.toString().substring(0, 6)));
	}
}