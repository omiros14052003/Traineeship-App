package myy803.traineeshipapp.mappers;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeshipapp.datamodel.Company;

public interface CompanyMapper extends JpaRepository<Company, String> {
	Company getReferenceById(String id);
}