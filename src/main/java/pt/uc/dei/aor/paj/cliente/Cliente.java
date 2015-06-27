package pt.uc.dei.aor.paj.cliente;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
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
	
	private ResteasyClient reClient = new ResteasyClientBuilder().build();
	
    public Cliente() {
    }
    
    
    public String getTextPlain(String urlTarget) {
    	ResteasyWebTarget tgt = reClient.target(urlTarget);

    	Response response = tgt.request(MediaType.TEXT_PLAIN).get();

    	String total = response.readEntity(String.class);
    	return total;
	}
    
    
    public <T, U> T getJson(String urlTarget, T entity, String type, Class<U> classType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
    	ResteasyWebTarget tgt = reClient.target(urlTarget);
    	Response response = tgt.request(MediaType.APPLICATION_JSON).get();

        String json = response.readEntity(String.class);

        if (type.equals("object"))
        	return jsonToObject(json, entity);
        else
        	return jsonToList(json, entity, classType);
    }

    
    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
    	Cliente c = new Cliente();
    	System.out.println("Exercicio 1");
    	System.out.println(c.getTextPlain("http://localhost:8080/p4-ws/rest/users/total"));
    	System.out.println();
    	
    	System.out.println("Exercicio 2");
    	System.out.println(c.getJson("http://localhost:8080/p4-ws/rest/users", new ListUserEntities(), "list", UserDetail.class));
    	System.out.println();
    	
    	System.out.println("Exercicio 3");
    	System.out.println(c.getJson("http://localhost:8080/p4-ws/rest/users/10", new UserDetail(), "object", null));
    	System.out.println();
    	
    	System.out.println("Exercicio 6");
    	System.out.println(c.getTextPlain("http://localhost:8080/p4-ws/rest/playlists/total"));
    	System.out.println();
    	
    	System.out.println("Exercicio 7");
    	System.out.println(c.getJson("http://localhost:8080/p4-ws/rest/playlists", new ListPlaylists(), "list", AllPlaylists.class));
    	System.out.println();
    	
    	System.out.println("Exercicio 8");
    	System.out.println(c.getJson("http://localhost:8080/p4-ws/rest/playlists/1/musics", new ListMusicEntities(), "list", MusicDetail.class));
    	System.out.println();
    	
    	System.out.println("Exercicio 9");
    	System.out.println(c.getJson("http://localhost:8080/p4-ws/rest/playlists/user/10", new ListPlaylists(), "list", AllPlaylists.class));
    	System.out.println();
    	
    	System.out.println("Exercicio 10");
    	System.out.println(c.getTextPlain("http://localhost:8080/p4-ws/rest/musics/total"));
    	System.out.println();
    	
    	System.out.println("Exercicio 11");
    	System.out.println(c.getJson("http://localhost:8080/p4-ws/rest/musics", new ListMusicEntities(), "list", MusicDetail.class));
    	System.out.println();
    	
    	System.out.println("Exercicio 12");
    	System.out.println(c.getJson("http://localhost:8080/p4-ws/rest/musics/1", new MusicDetail(), "object", null));
    	System.out.println();
    	
    	System.out.println("Exercicio 13");
    	System.out.println(c.getJson("http://localhost:8080/p4-ws/rest/musics/user/10", new ListMusicEntities(), "list", MusicDetail.class));
    	System.out.println();
    }
    
    
    public static <T> T jsonToObject(String s, T entity) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
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
    
    
    public static <T, U> T jsonToList(String s, T entity, Class<U> classType) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException {
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
}
