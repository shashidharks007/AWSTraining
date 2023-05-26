package com.es.security.repository;

import com.es.security.entity.EmployeeDetails;
import com.es.security.entity.Skills;
import com.es.security.model.EmployeeSkillDTO;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@SqlResultSetMapping(
        name = "employee_details_skills_list",
        classes = @ConstructorResult(
                targetClass = EmployeeSkillDTO.class,
                columns = {
                        @ColumnResult(name = "employeeCode"),
                        @ColumnResult(name = "skillid"),
                        @ColumnResult(name = "skillName")
                }
        )
)
@SqlResultSetMapping(
        name = "employee_details",
classes = @ConstructorResult(
        targetClass = EmployeeDetails.class,
        columns = {
                @ColumnResult(name = "employee_code"),
                @ColumnResult(name = "reporting_email_id"),
                @ColumnResult(name = "email_id")
        }
))
@SqlResultSetMapping(
        name = "skills",
        classes = @ConstructorResult(
                targetClass = Skills.class,
                columns = {
                        @ColumnResult(name = "skill_id"),
                        @ColumnResult(name = "skill_name")
                }
        ))
@Repository
public interface SkillsRepository extends JpaRepository<Skills, String> {

    Optional<Skills> findBySkillName(String skillName);
    @Query(nativeQuery = true, value = "SELECT * FROM skills WHERE skillid IN (SELECT DISTINCT(skills_list_skillid) " +
            "FROM employee_details_skills_list WHERE employee_details_employee_code " +
            "IN (SELECT employee_code FROM employee_details WHERE reporting_email_id = ?1 OR email_id = ?1));")
    List<Skills> findSkillByReportingEmail(String Email);

//    @Query(nativeQuery = true, value = "SELECT * FROM employee_details WHERE employee_code " +
//            "IN (SELECT employee_details_employee_code FROM employee_details_skills_list " +
//            "WHERE skills_list_skillid =(SELECT skillid FROM skills WHERE skill_name = ?1));")
//    List<EmployeeDetails> findEmployeeBySkills(String Skill);
}
