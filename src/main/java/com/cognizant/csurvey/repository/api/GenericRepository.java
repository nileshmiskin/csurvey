package com.cognizant.csurvey.repository.api;

import java.util.List;

public interface GenericRepository<T> {

	public List<T> findAll();

	public void save(T object);
	
	public T findById(String id);

	public void delete(String id);

}
