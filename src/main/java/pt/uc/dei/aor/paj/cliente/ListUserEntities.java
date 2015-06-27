package pt.uc.dei.aor.paj.cliente;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
public class ListUserEntities {
	
	private List<UserDetail> listOfUsers = new ArrayList<UserDetail>();
	
	public ListUserEntities() {
	}

	public List<UserDetail> getListUsers() {
		return listOfUsers;
	}

	public void setListUsers(List<UserDetail> listUsers) {
		this.listOfUsers = listUsers;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (UserDetail ue : listOfUsers) {
			sb.append("Nome: "+ue.getEmail()+" (ID="+ue.getId()+")\n");
		}
		return sb.toString();
	}
	
	

}
