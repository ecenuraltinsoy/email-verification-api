package com.example.verification.emailverificationapi.model;

import java.util.List;

public class ModelEmailUser {

	private List<String> emailAddresses;

	/*public ModelEmailUser(List<String> emailAddresses) {

		this.emailAddresses = emailAddresses;
	}*/

	public List<String> getEmailAddresses() {
		return emailAddresses;
	}

	public void setEmailAddresses(List<String> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}
	
}
