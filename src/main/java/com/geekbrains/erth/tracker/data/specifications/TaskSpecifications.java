package com.geekbrains.erth.tracker.data.specifications;

import com.geekbrains.erth.tracker.entities.Task;
import com.geekbrains.erth.tracker.entities.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {
    public static Specification<Task> executorContains(String executor) {

        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(root
                        .join("executor")
                        .get("name")), "%" + executor + "%"
        );
    }

    public static Specification<Task> statusEq(String status) {
        return (Specification<Task>) (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.equal(root.get("status"), TaskStatus.valueOf(status));
    }
}
