package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.RuleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Gives access to the JPA CRUD methods on the RuleName entity.
 *
 * @author SimonC.
 * @version 1.0
 * @see RuleName
 */
@Repository
public interface RuleNameRepository extends JpaRepository<RuleName, Integer> {
}
