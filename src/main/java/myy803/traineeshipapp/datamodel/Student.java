package myy803.traineeshipapp.datamodel;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "student")
@Component
public class Student {

    @Id
    @JoinColumn(name = "id", referencedColumnName = "username")
    private String id;

    @Column(name = "university_id")
    private int universityId;

    @Column(name = "full_name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "looking_for_position")
    private Boolean lookingForPosition;

    @Column(name = "interests")
    private String interests;

    @Column(name = "skills")
    private String skills;
    
    public Student() { 
    	name = "";
    	location = "";
    	interests = "";
    	skills = "";
    }

    public String getId() { return id; }
    
    public void setId(String id) { this.id = id; }

    public int getUniversityId() { return universityId; }
    
    public void setUniversityId(int universityId) { this.universityId = universityId; }

    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    
    public void setLocation(String location) { this.location = location; }

    public Boolean getLookingForPosition() { return lookingForPosition; }
    
    public void setLookingForPosition(Boolean lookingForPosition) { this.lookingForPosition = lookingForPosition; }

    public String getInterests() { return interests; }
    
    public void setInterests(String interests) { this.interests = interests; }

    public String getSkills() { return skills; }
    
    public void setSkills(String skills) { this.skills = skills; }
}
