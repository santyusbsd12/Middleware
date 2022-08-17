package com.konecta.middlewareweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.konecta.middlewareweb.model.Bot;

public interface BotRepository extends JpaRepository<Bot, Long>{
	
	
//	@Query("from Bot b inner join fetch b.customMessage c inner join fetch b.sendConfig se where b.botId = :idBot")
	
	
	@Query("from Bot b inner join fetch b.customMessage c inner join fetch b.sendConfig se inner join fetch b.closingMessages where b.botId = :idBot")
	Bot findByBotId(@Param("idBot") String idBot);

}
