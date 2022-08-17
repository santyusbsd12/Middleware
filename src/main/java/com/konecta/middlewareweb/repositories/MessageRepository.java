package com.konecta.middlewareweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.konecta.middlewareweb.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long>{

}
