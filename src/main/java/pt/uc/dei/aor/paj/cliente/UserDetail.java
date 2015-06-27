package pt.uc.dei.aor.paj.cliente;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class UserDetail {
	private String id;
	private String email;
	private String name;
	private String password;
	
	public UserDetail() {
	}
	
	
	
	public UserDetail(String email, String name, String password) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
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



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



}
