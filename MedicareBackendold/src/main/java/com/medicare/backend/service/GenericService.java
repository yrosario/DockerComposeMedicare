package com.medicare.backend.service;

import java.util.List;

public interface GenericService<T,ID> {

	public T save(T t);
	public List<T> findAll();
	public T findById(ID id);
	public T update(T t);
	public boolean delete(ID id);
}
