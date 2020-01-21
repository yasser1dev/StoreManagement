package com.DAO;

import java.util.List;

public interface DAO<T>{
	public T find(long id);
	public void create(T p);
	public void delete(long id);
	public boolean update(T p);
	public List<T> findAll();
	public List<T> finDAll(String Key);
	public long maxId();
}
