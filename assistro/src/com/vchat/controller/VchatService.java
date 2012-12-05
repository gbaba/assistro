package com.vchat.controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class VchatService {
	private static final EntityManagerFactory emfInstance = Persistence.createEntityManagerFactory("transactions-optional");

	private VchatService() {
	}

	public static EntityManagerFactory get() {
		return emfInstance;
	}
}
