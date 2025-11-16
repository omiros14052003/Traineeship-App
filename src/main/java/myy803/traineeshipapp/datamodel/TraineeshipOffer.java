package myy803.traineeshipapp.datamodel;

import jakarta.persistence.*;
@Entity
@Table(name="traineeship_offer")
public class TraineeshipOffer {

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@OneToOne
    @JoinColumn(name = "Traineeship_Position_Id", referencedColumnName = "id")
    private TraineeshipPosition traineeshipPosition;

    @Column(name="Start")
    private String start;

    @Column(name="End")
    private String end;

    @Column(name="Description")
    private String description;

    @Column(name="Skills")
    private String skills;

    @Column(name="Topics")
    private String topics;

    @ManyToOne
    @JoinColumn(name = "Company_Id", referencedColumnName = "id")
    private Company company;

    public TraineeshipOffer() { 
    	start = "";
    	end = "";
    	description = "";
    	skills = "";
    	topics = "";
    }
    
    public int getId() {return id;}
    
    public void setId(int id) { this.id = id; }

    public TraineeshipPosition getTraineeshipPosition() { return traineeshipPosition; }

    public void setTraineeshipPosition(TraineeshipPosition traineeshipPosition) { this.traineeshipPosition = traineeshipPosition; }
    
    public String getStart() { return start; }
    
    public void setStart(String start) { this.start = start; }
    
    public String getEnd() { return end; }
    
    public void setEnd(String end) { this.end = end; }
    
    public String getDescription() { return description; }
    
    public void setDescription(String description) { this.description = description; }
    
    public String getSkills() { return skills; }
    
    public void setSkills(String skills) { this.skills = skills; }
    
    public String getTopics() { return topics; }
    
    public void setTopics(String topics) { this.topics = topics; }

    public String getCompanyId() { return company.getId(); }

    public Company getCompany() { return company; }

    public void setCompany(Company company) { this.company = company; }
    
    public String getLocation() {return company.getLocation();}
}
