package com.outofcity.server.repository;

import com.outofcity.server.domain.ActivityType;
import com.outofcity.server.domain.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long> {
    List<ActivityType> findByType(Type type);
}