package pt.uc.dei.aor.paj.cliente;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name="UserDetail")
public class UserDetail {
	private String id;
	private String email;
	private String name;
	
	public UserDetail() {
	}
	
	
	
	public UserDetail(String email, String name) {
		super();
		this.email = email;
		this.name = name;
	}



	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
	
	@Override
	public String toString() {
		return "Utilizador:\n"
				+ "E-mail: "+email+"\n"
						+ "Nome: "+name+"\n"
								+ "(ID: "+id+")";
	}

}
