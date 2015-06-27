package pt.uc.dei.aor.paj.cliente;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class MusicDetail {
	private int id;
	private String title;
	private String artist;
	private String album;
	private Date dateRecord;
	private int userOwnerID;
	private int nPlaylists;
	
	public MusicDetail() {
	}

	public MusicDetail(int id, String title, String artist, String album,
			Date dateRecord, int userOwnerID, int nPlaylists) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.album = album;
		this.dateRecord = dateRecord;
		this.userOwnerID = userOwnerID;
		this.nPlaylists = nPlaylists;
	}

	public int getId() {
		return id;
	}

	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public Date getDateRecord() {
		return dateRecord;
	}

	public void setDateRecord(String dateRecord) {
		this.dateRecord = null;
	}

	public int getUserOwnerID() {
		return userOwnerID;
	}

	public void setUserOwnerID(String userOwnerID) {
		this.userOwnerID = Integer.parseInt(userOwnerID);
	}

	public int getnPlaylists() {
		return nPlaylists;
	}

	public void setnPlaylists(int nPlaylists) {
		this.nPlaylists = nPlaylists;
	}
	
	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String nP = "";
		if (nPlaylists == 0) nP="(ainda não foi adicionada a qualquer playlist)";
		else nP="(adicionada a "+nPlaylists+" playlists)";
		return "Nome: "+title+", Artista: "+artist+", Album: "+album+", Data: "+null+", submetida pelo utilizador com id "+userOwnerID+" "+nP;
	}

}