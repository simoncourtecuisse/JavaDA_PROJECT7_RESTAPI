package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.BidList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gives access to the JPA CRUD methods on the BidList entity.
 *
 * @author SimonC.
 * @version 1.0
 * @see BidList
 */
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
