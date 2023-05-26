package com.es.security.repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.es.security.entity.EmployeeDetails;
import com.es.security.entity.User;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@SqlResultSetMapping(
        name = "UserDetails",
        classes = @ConstructorResult(
                targetClass = User.class,
                columns = {
                        @ColumnResult(name = "userName"),
                        @ColumnResult(name = "password"),
                        @ColumnResult(name = "email")
                }
        )
)
@SqlResultSetMapping(
        name = "EmployeeDetails",
        classes = @ConstructorResult(
                targetClass = EmployeeDetails.class,
                columns = {
                        @ColumnResult(name = "employee_name"),
                        @ColumnResult(name = "employee_code"),
                        @ColumnResult(name = "email_id")
                }
        ))
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findByEmail(String email);
  @Query(nativeQuery = true, value = "INSERT INTO user_detail (user_name, password, email)" +
          "SELECT employee_name, employee_code, email_id FROM employee_details;")
  List<User> user_Employee();
}
