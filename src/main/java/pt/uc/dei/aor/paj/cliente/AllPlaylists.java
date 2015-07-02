package pt.uc.dei.aor.paj.cliente;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AllPlaylists {
	private int id;
	private String name;
	private Date insertDate;
	private int userOwnerID;
	private List<MusicDetail> listOfMusics;

	public AllPlaylists() {
		listOfMusics = new ArrayList<MusicDetail>();
	}

	public AllPlaylists(int id, String name, Date insertDate, int userOwnerID) {
		super();
		this.id = id;
		this.name = name;	
		this.insertDate = insertDate;
		this.userOwnerID = userOwnerID;
	}

	public int getId() {
		return id;
	}
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getInsertDate() {
		return insertDate;
	}
	public void setInsertDate(String insertDate) {
		long timestamp = Long.parseLong(insertDate);
		Timestamp ts = new Timestamp(timestamp);
		this.insertDate = new Date(ts.getTime());
	}
	public int getUserOwnerID() {
		return userOwnerID;
	}
	public void setUserOwnerID(String userOwnerID) {
		this.userOwnerID = Integer.parseInt(userOwnerID);
	}

	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return "Playlist:\n"
		+ "Id: "+id+"\n"
		+ "Nome: "+name+"\n"
		+ "Data de criação: "+df.format(insertDate)+"\n"
		+ "(Criado pelo utilizador com ID "+userOwnerID+")";
	}

	public List<MusicDetail> getListOfMusics() {
		return listOfMusics;
	}

	public void setListOfMusics(List<MusicDetail> listOfMusics) {
		this.listOfMusics = listOfMusics;
	}


	



}
