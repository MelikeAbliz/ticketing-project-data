package com.cydeo.repository;

import com.cydeo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TaskRepository extends JpaRepository<Task, Long> {


    //jpql query
    @Query("SELECT COUNT(t) FROM Task t WHERE t.project.projectCode = ?1 " +//this part give all tasks belongs to this project
            "AND t.taskStatus <> 'COMPLETE'")//<>mean not equal
    int totalNonCompletedTasks(String projectCode);

    //native query
    @Query(value = "SELECT COUNT(*) FROM tasks t JOIN projects p on t.project_id=p.id " +
            "WHERE p.project_code=?1 AND t.task_status='COMPLETE'",nativeQuery = true)
    int totalCompletedTasks(String projectCode);


}
