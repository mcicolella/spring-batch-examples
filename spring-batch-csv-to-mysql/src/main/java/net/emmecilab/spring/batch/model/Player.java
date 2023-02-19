package net.emmecilab.spring.batch.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player {

	@Id
	private int id;
	private String firstName;
	private String lastName;
	private String team;
	private String position;

	// needed to avoid runtime error with BeanWrapperFieldSetMapper 
	public Player() {
	}
	
	public Player(String firstName, String lastName, String team, String position) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.team = team;
		this.position = position;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

}
