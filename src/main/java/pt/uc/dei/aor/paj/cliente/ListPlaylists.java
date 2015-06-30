package pt.uc.dei.aor.paj.cliente;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ListPlaylists {
	
	private List<AllPlaylists> listOfPlaylists;
	
	public ListPlaylists() {
		listOfPlaylists = new ArrayList<AllPlaylists>();
	}
	
	
	
	@Override
	public String toString() {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		StringBuilder sb = new StringBuilder();
		for (AllPlaylists ap:listOfPlaylists) {
			sb.append("Nome: "+ap.getName()+", Data: "+df.format(ap.getInsertDate())+", do utilizador com ID: "+ap.getUserOwnerID()+"\n");
		}
		return sb.toString();
	}



	public List<AllPlaylists> getListOfPlaylists() {
		return listOfPlaylists;
	}



	public void setListOfPlaylists(List<AllPlaylists> listOfPlaylists) {
		this.listOfPlaylists = listOfPlaylists;
	}
	
	

}
