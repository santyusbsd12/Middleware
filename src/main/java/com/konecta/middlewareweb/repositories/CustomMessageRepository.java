package com.konecta.middlewareweb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.konecta.middlewareweb.model.CustomMessage;

public interface CustomMessageRepository extends JpaRepository<CustomMessage, Long>{

	@Query("from CustomMessage c inner join fetch c.bot b where b.botId = :idBot")
	CustomMessage findCustomMessageByBotId(@Param("idBot") String idBot);
	
}
