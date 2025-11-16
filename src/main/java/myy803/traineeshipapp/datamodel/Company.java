package myy803.traineeshipapp.datamodel;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "company")
@Component
public class Company {

    @Id
    @JoinColumn(name = "id", referencedColumnName = "username")
    private String id;

    @Column(name = "Company_Name")
    private String name;

    @Column(name = "Location")
    private String location;

    public String getId() { return id; }
    
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }
    
    public String getLocation() { return location; }
    
    public void setLocation(String location) { this.location = location; }    
}