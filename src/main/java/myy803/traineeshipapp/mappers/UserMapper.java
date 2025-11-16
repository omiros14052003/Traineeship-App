package myy803.traineeshipapp.mappers;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeshipapp.datamodel.User;


public interface UserMapper extends JpaRepository<User, String> {
	User findByUsername(String username);
}