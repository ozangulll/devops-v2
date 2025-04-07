package org.sau.devopsv2.repository;
import org.sau.devopsv2.entity.Tasker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskerRepository extends JpaRepository<Tasker, Long> {
    List<Tasker> findByEmployeeId(Long employeeId);
    List<Tasker> findByTaskId(Long taskId);
}
