package net.javaguides.springboot;



import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;

//import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.mindtree.employeemanagerapp.model.Employee;
import com.mindtree.employeemanagerapp.repository.EmployeeRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class SpringbootBackendApplicationTests {
	
	@Autowired
	private EmployeeRepository repo;

	@Test
	 @Order(1)
	@Rollback(false)
	public void createEmployee() {
	   Employee employee=new Employee("Harsh","Gupta","guptaharsh784@gmail.com");
		repo.save(employee);
		Assertions.assertThat(employee.getId()).isGreaterThan(0);
	}
	@Test
    @Order(2)
    public void getEmployee(){

        Employee employee = repo.findById(1L).get();

        Assertions.assertThat(employee.getId()).isEqualTo(1L);

    }

    @Test
    @Order(3)
    public void getListOfEmployeesTest(){

        List<Employee> employees = repo.findAll();

        Assertions.assertThat(employees.size()).isGreaterThan(0);

    }

    @Test
    @Order(4)
    @Rollback(value = false)
    public void updateEmployeeTest(){

        Employee employee = repo.findById(1L).get();

        employee.setEmailId("rishabhsharma@1234");

        Employee employeeUpdated =  repo.save(employee);

        Assertions.assertThat(employeeUpdated.getEmailId()).isEqualTo("rishabhsharma@1234");

    }

    @Test
    @Order(5)
    @Rollback(value = false)
    public void deleteEmployeeTest(){

        Employee employee = repo.findById(1L).get();

        repo.delete(employee);

        //employeeRepository.deleteById(1L);

        Employee employee1 = null;

        Optional<Employee> optionalEmployee = repo.findByEmail("rishabhsharma@1234");

        if(optionalEmployee.isPresent()){
            employee1 = optionalEmployee.get();
        }

        Assertions.assertThat(employee1).isNull();
    }

}


