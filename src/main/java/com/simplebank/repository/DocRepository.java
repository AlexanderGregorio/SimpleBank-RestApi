package com.simplebank.repository;

import com.simplebank.model.Doc;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocRepository extends CrudRepository<Doc, Long> { }
