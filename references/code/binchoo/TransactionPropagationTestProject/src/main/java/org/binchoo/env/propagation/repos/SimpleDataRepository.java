package org.binchoo.env.propagation.repos;

import org.binchoo.env.propagation.entities.SimpleData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SimpleDataRepository extends JpaRepository<SimpleData, Long> {
}
