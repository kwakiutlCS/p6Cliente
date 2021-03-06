package pt.uc.dei.aor.paj.cliente;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.beanutils.BeanUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 * Session Bean implementation class LyricsRest
 */
@Stateless
@LocalBean
public class Cliente {
	private static final String SERVER_ERROR = "Servidor indisponivel...";
	private static final String ERROR_404 = "Recurso inexistente...";
	
	private ResteasyClient reClient = new ResteasyClientBuilder().build();
	
    public Cliente() {
    }
    
    
    public String getTextPlain(String urlTarget) {
    	ResteasyWebTarget tgt = reClient.target(urlTarget);
    	Response response;
    	
    	try {
    		response = tgt.request(MediaType.TEXT_PLAIN).get();
    	}
    	catch(ProcessingException e) {
    		System.out.println(SERVER_ERROR);
    		return null;
    	}
    	
    	String total = response.readEntity(String.class);
    	if (response.getStatus() == 404) {
    		System.out.println(ERROR_404);
    		return null;
    	}
    	
    	return total;
	}
    
    
    private <T, U> T getJson(String urlTarget, T entity, String type, Class<U> classType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
    	ResteasyWebTarget tgt = reClient.target(urlTarget);
    	Response response;
    	
    	try {
    		response = tgt.request(MediaType.APPLICATION_JSON).get();
    	}
    	catch(ProcessingException e) {
    		System.out.println(SERVER_ERROR);
    		return null;
    	}
    	
    	String json = response.readEntity(String.class);
    	
    	if (response.getStatus() == 404) {
    		System.out.println(ERROR_404);
    		return null;
    	}

        if (type.equals("object"))
        	return jsonToObject(json, entity);
        else
        	return jsonToList(json, entity, classType);
    }
    
    
    private String delete(String urlTarget) {
    	ResteasyWebTarget tgt = reClient.target(urlTarget);
    	Response response;
    	try {
    		response = tgt.request().delete();
    	}
    	catch(ProcessingException e) {
    		System.out.println(SERVER_ERROR);
    		return null;
    	}
    	if (response.getStatus() == 404) {
    		System.out.println(ERROR_404);
    		return null;
    	}
    	response.readEntity(String.class);
    	return "User removido";
    }

    private String post(String urlTarget, String... args) {
    	Form form = new Form();
    	form.param("username", args[0]);
    	form.param("name", args[1]);
    	form.param("password", args[2]);
    	Entity<Form> newUser = Entity.form(form);
    	ResteasyWebTarget tgt = reClient.target(urlTarget);
    	Response response;
    	try {
    		response = tgt.request().post(newUser);
    	}
    	catch(ProcessingException e) {
    		System.out.println(SERVER_ERROR);
    		return null;
    	}
    	if (response.getStatus() == 404) {
    		System.out.println(ERROR_404);
    		return null;
    	}
    	String result = response.readEntity(String.class);
    	if (result.equals("error")) return "Não é possível";
    	return "Operação realizada com sucesso";
    }
    
    private String put(String urlTarget, String id, String password) {
    	Form form = new Form();
    	form.param("id", id);
    	form.param("password", password);
    	
    	Entity<Form> newUser = Entity.form(form);
    	ResteasyWebTarget tgt = reClient.target(urlTarget);
    	Response response;
    	try {
    		response = tgt.request().put(newUser);
    	}
    	catch(ProcessingException e) {
    		System.out.println(SERVER_ERROR);
    		return null;
    	}
    	if (response.getStatus() == 404) {
    		System.out.println(ERROR_404);
    		return null;
    	}
    	response.readEntity(String.class);
    	return "Operação realizada com sucesso";
    }
    
    private String updateMusicsFromPlaylist(String urlTarget) {
    	ResteasyWebTarget tgt = reClient.target(urlTarget);
    	Response response;
    	try {
    		response = tgt.request().post(Entity.text(""));
    	}
    	catch (ProcessingException e) {
    		System.out.println(SERVER_ERROR);
    		return null;
    	}
    	if (response.getStatus() == 404) {
    		System.out.println(ERROR_404);
    		return null;
    	}
    	return "Operação realizada com sucesso";
    }
    
    
    
    public static void main(String[] args) {
    	Cliente c = new Cliente();
    	Object answer = null;
    	
    	try {
    		if (args.length == 1 && args[0].equals("countUsers")) {
    			answer = c.getTextPlain("http://localhost:8080/p4-ws/rest/users/total");
    		}
    		else if (args.length == 2 && args[0].equals("countUsers") && args[1].equals("-l")) {
    			answer = c.getTextPlain("http://localhost:8080/p4-ws/rest/users/logged/total");
    		}
    		else if (args.length == 1 && args[0].equals("listUsers")) {
    			answer = c.getJson("http://localhost:8080/p4-ws/rest/users", new ListUserEntities(), "list", UserDetail.class);
    		}
    		else if (args.length == 2 && args[0].equals("listUsers") && args[1].equals("-l")) {
    			answer = c.getJson("http://localhost:8080/p4-ws/rest/users/logged", new ListUserEntities(), "list", UserDetail.class);
    		}
    		else if (args.length == 2 && args[0].equals("showUser")) {
    			answer = c.getJson("http://localhost:8080/p4-ws/rest/users/get/"+args[1], new UserDetail(), "object", null);
    		}
    		else if (args.length == 1 && args[0].equals("countPlaylists")) {
    			answer = c.getTextPlain("http://localhost:8080/p4-ws/rest/playlists/total");
    		}
    		else if (args.length == 1 && args[0].equals("listPlaylists")) {
    			answer = c.getJson("http://localhost:8080/p4-ws/rest/playlists", new ListPlaylists(), "list", AllPlaylists.class);
    		}
    		else if ( args.length == 3 && args[0].equals("listMusics") && args[1].equals("-p")) {
    			answer = c.getJson("http://localhost:8080/p4-ws/rest/playlists/"+args[2]+"/musics", new ListMusicEntities(), "list", MusicDetail.class);
    		}
    		else if ( args.length == 3 && args[0].equals("listPlaylists") && args[1].equals("-u")) {
    			answer = c.getJson("http://localhost:8080/p4-ws/rest/playlists/user/"+args[2], new ListPlaylists(), "list", AllPlaylists.class);
    		}
    		else if (args.length == 1 && args[0].equals("countMusics")) {
    			answer = c.getTextPlain("http://localhost:8080/p4-ws/rest/musics/total");
    		}
    		else if ( args.length == 1 && args[0].equals("listMusics")) {
    			answer = c.getJson("http://localhost:8080/p4-ws/rest/musics", new ListMusicEntities(), "list", MusicDetail.class);
    		}
    		else if ( args.length == 2 && args[0].equals("showMusic")) {
    			answer = c.getJson("http://localhost:8080/p4-ws/rest/musics/"+args[1], new MusicDetail(), "object", null);
    		}
    		else if ( args.length == 3 && args[0].equals("listMusics") && args[1].equals("-u")) {
    			answer = c.getJson("http://localhost:8080/p4-ws/rest/musics/user/"+args[2], new ListMusicEntities(), "list", MusicDetail.class);
    		}
    		else if ( args.length == 2 && args[0].equals("deleteUser")) {
    			answer = c.delete("http://localhost:8080/p4-ws/rest/users/"+args[1]);
    		}
    		else if ( args.length == 3 && args[0].equals("removeMusics") && args[1].equals("-u")) {
    			answer = c.delete("http://localhost:8080/p4-ws/rest/musics/user/"+args[2]);
    		}
    		else if ( args.length == 3 && args[0].equals("removeMusics") && args[1].equals("-p")) {
    			answer = c.updateMusicsFromPlaylist("http://localhost:8080/p4-ws/rest/playlists/"+args[2]+"/remove");
    		}
    		else if ( args.length == 5 && args[0].equals("removeMusics") && args[1].equals("-p") && args[3].equals("-m")) {
    			answer = c.updateMusicsFromPlaylist("http://localhost:8080/p4-ws/rest/playlists/"+args[2]+"/remove/"+args[4]);
    		}
    		else if ( args.length == 5 && args[0].equals("removeMusics") && args[1].equals("-m") && args[3].equals("-p")) {
    			answer = c.updateMusicsFromPlaylist("http://localhost:8080/p4-ws/rest/playlists/"+args[4]+"/remove/"+args[2]);
    		}
    		else if ( args.length == 5 && args[0].equals("addMusics") && args[1].equals("-m") && args[3].equals("-p")) {
    			answer = c.updateMusicsFromPlaylist("http://localhost:8080/p4-ws/rest/playlists/"+args[4]+"/add/"+args[2]);
    		}
    		else if ( args.length == 5 && args[0].equals("addMusics") && args[1].equals("-p") && args[3].equals("-m")) {
    			answer = c.updateMusicsFromPlaylist("http://localhost:8080/p4-ws/rest/playlists/"+args[2]+"/add/"+args[4]);
    		}
    		else if ( args.length == 5 && args[0].equals("addUser") && args[1].equals("-e") && args[3].equals("-n")) {
    			answer = addUser(args[2], args[4], c);
    		}
    		else if ( args.length == 5 && args[0].equals("addUser") && args[1].equals("-n") && args[3].equals("-e")) {
    			answer = addUser(args[4], args[2], c);
    		}
    		else if ( args.length == 2 && args[0].equals("changePassword")) {
    			java.io.Console console = System.console();
    			String newPw;
    			while (true) {
    				System.out.print("password: ");
    				newPw = new String(console.readPassword());
    				System.out.print("confirm password: ");
    				String confirm = new String(console.readPassword());

    				if (newPw.equals(confirm)) break;
    				else System.out.println("Passwords don't match");
    			}
    			answer = c.put("http://localhost:8080/p4-ws/rest/users/changepassword", args[1], newPw);
    		}
    		else if (args.length == 1 && args[0].equals("--help")) {
    			showHelp();
    		}
    		else {
    			System.out.println("usage: java -jar client.jar [args]");
    			System.out.println("details: java -jar client.jar --help");
    		}

    		if (answer != null) System.out.println(answer);

    	}
    	catch (Exception e) {
    		System.out.println("Service unavailable");
    	}
    	System.out.println();
    	System.out.println();
    }
    
    
    private static String addUser(String email, String name, Cliente c) {
    	java.io.Console console = System.console();
		String newPw;
		
		while (true) {
			System.out.print("password: ");
			newPw = new String(console.readPassword());
			System.out.print("confirm password: ");
			String confirm = new String(console.readPassword());
			
			if (newPw.equals(confirm)) break;
			else System.out.println("Passwords don't match");
		}
		return c.post("http://localhost:8080/p4-ws/rest/users", email, name, newPw);
    }
    
    
    private static <T> T jsonToObject(String s, T entity) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
    	JSONObject json = new JSONObject(s);
		Map<String, String> map = BeanUtils.describe(entity);
		
		for (String k : map.keySet()) {
			if (!("class".equals(k))) {
				String capitalized = k.substring(0,1).toUpperCase()+k.substring(1);
				
				try {
					Method setter = entity.getClass().getMethod("set"+capitalized, String.class);
					setter.invoke(entity, json.get(k).toString());
				}
				catch(NoSuchMethodException e) {
					
				}
			}
		}
		return entity;
    }
    
    
    private static <T, U> T jsonToList(String s, T entity, Class<U> classType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
    	JSONObject json = new JSONObject(s);
    	List<U> list = new ArrayList<U>();
    	
    	Map<String, String> map = BeanUtils.describe(entity);
		Method setter;
		
		for (String k : map.keySet()) {
			if (!("class".equals(k))) {
				String capitalized = k.substring(0,1).toUpperCase()+k.substring(1);
				setter = entity.getClass().getMethod("set"+capitalized, List.class);
				
				JSONArray array = json.getJSONArray(k);
				for (int i = 0; i < array.length(); i++) {
					list.add(jsonToObject(array.get(i).toString(), classType.newInstance()));
				}
				
				setter.invoke(entity, list);
			}
		}
		
		return entity;
    }
    
    
    private static void showHelp() {
    	System.out.println("options:");
    	System.out.println();
    	System.out.println("countUsers  ->  shows number of users");
    	System.out.println("extra options: -l -> only logged users");
    	System.out.println();
    	System.out.println("countPlaylists ->  shows number of playlists");
    	System.out.println();
    	System.out.println("countMusics  ->  shows number of musics");
    	System.out.println();
    	System.out.println("listUsers  ->  list the users");
    	System.out.println("extra options: -l -> only logged users");
    	System.out.println();
    	System.out.println("showUser [id]  ->  shows user details");
    	System.out.println();
    	System.out.println("listPlaylists  ->  list the playlists");
    	System.out.println("extra options: -u [id] -> filters playlists by user");
    	System.out.println();
    	System.out.println("listMusics  ->  list the musics");
    	System.out.println("extra options: -p [id] -> filters musics by playlists");
    	System.out.println("extra options: -u [id] -> filters musics by user");
    	System.out.println();
    	System.out.println("showMusic [id]  ->  shows music details");
    	System.out.println();
    	System.out.println("deleteUser [id]  ->  removes user");
    	System.out.println();
    	System.out.println("addUser  ->  adds user");
    	System.out.println("options: -e [arg] -> email");
    	System.out.println("options: -n [arg] -> username");
    	System.out.println();
    	System.out.println("removeMusics  ->  remove musics from playlists or users");
    	System.out.println("extra options: -p [id] -> filters musics by playlists");
    	System.out.println("extra options: -p [id] -m [id] -> filters musics by playlists and musicId");
    	System.out.println("extra options: -u [id] -> filters musics by user");
    	System.out.println();
    	System.out.println("changePassword  [id] ->  changes user password");
    	System.out.println();
    	System.out.println("addMusics  ->  add musics to playlists");
    	System.out.println("options: -p [id] -> playlist");
    	System.out.println();
    	System.out.println("--help  ->  this screen");
    	System.out.println();
    	
    	
    }
}
