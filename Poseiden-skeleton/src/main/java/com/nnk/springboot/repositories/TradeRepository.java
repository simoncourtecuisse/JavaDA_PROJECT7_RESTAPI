package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gives access to the JPA CRUD methods on the Trade entity.
 *
 * @author SimonC.
 * @version 1.0
 * @see Trade
 */
@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
