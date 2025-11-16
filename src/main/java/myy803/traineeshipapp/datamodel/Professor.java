package myy803.traineeshipapp.datamodel;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "professor")
@Component
public class Professor {

    @Id
    @JoinColumn(name = "id", referencedColumnName = "username")
    private String id;

    @Column(name = "full_name")
    private String name;

    @Column(name = "interests")
    private String interests;
    
    @Column(name = "supervisee_num")
    private int superviseeNum;


    public String getId() { return id; }
    
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    
    public void setName(String name) { this.name = name; }

    public String getInterests() { return interests; }
    
    public void setInterests(String interests) { this.interests = interests; }
    
    public int getSuperviseeNum() { return superviseeNum; }
    
    public void setSuperviseeNum(int superviseeNum) { this.superviseeNum = superviseeNum; }
    
    public void addSupervisee() {superviseeNum += 1;}
    
    public void subtractSupervisee() {superviseeNum -= 1;}
}