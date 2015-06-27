package pt.uc.dei.aor.paj.cliente;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement
public class UserDetail {
	private int id;
	private String email;
	private String name;
	@XmlTransient
	private String password;
	
	public UserDetail() {
	}
	
	
	
	public UserDetail(String email, String name, int id) {
		super();
		this.email = email;
		this.name = name;
		this.id = id;
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
	public int getId() {
		return id;
	}
	public void setId(String id) {
		this.id = Integer.parseInt(id);
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
