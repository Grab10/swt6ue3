package swt6.spring.worklog.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.expression.spel.ast.OpInc;
import org.springframework.stereotype.Repository;
import swt6.spring.worklog.domain.Employee;
import swt6.spring.worklog.domain.Project;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByLastName(@Param("lastName") String lastName);

    @Query("select e from Employee e where e.lastName like %:substr%")
    List<Employee> findByLastNameContaining(@Param("substr") String substr);

    @Query("select e from Employee e where e.dateOfBirth < :date")
    List<Employee> findOlderThan(@Param("date") LocalDate date);

    List<Employee> findByProjects(Project project);

}
