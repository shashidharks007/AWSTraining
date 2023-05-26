package com.es.security.repository;

import com.es.security.entity.Certification;
import com.es.security.entity.EmployeeDetails;
import com.es.security.entity.Skills;
import com.es.security.model.EmployeeCertificationDTO;
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
        name = "employee_details_certification_list",
        classes = @ConstructorResult(
                targetClass = EmployeeCertificationDTO.class,
                columns = {
                        @ColumnResult(name = "employeeCode"),
                        @ColumnResult(name = "certificationId"),
                        @ColumnResult(name = "certificationName"),
                        @ColumnResult(name = "certificateId")
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
        name = "certification",
        classes = @ConstructorResult(
                targetClass = Certification.class,
                columns = {
                        @ColumnResult(name = "certificationId"),
                        @ColumnResult(name = "certificateId"),
                        @ColumnResult(name = "certification_name")
                }
        ))
@Repository
public interface CertificationRepository extends JpaRepository<Certification, Integer> {

    Optional<Certification> findByCertificationName(String certificationName);
    @Query(nativeQuery = true, value = "SELECT * FROM certification WHERE certificationid " +
            "IN (SELECT DISTINCT(certification_list_certificationid) FROM employee_details_certification_list " +
            "WHERE employee_details_employee_code IN (SELECT employee_code FROM employee_details " +
            "WHERE reporting_email_id = ?1 OR email_id = ?1));")
    List<Certification> findCertificateByReportingEmail(String Email);
//    @Query(nativeQuery = true, value = "SELECT * FROM employee_details WHERE employee_code " +
//            "IN (SELECT employee_details_employee_code FROM employee_details_certification_list " +
//            "WHERE certification_list_certificationid =(SELECT certificationid FROM certification WHERE certification_name = ?1));")
//    List<EmployeeDetails> findEmployeeByCertification(String certification);
}