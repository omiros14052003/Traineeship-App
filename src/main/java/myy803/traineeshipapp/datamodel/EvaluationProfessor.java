package myy803.traineeshipapp.datamodel;

import jakarta.persistence.*;

@Entity
@Table(name="evaluation_professor")
public class EvaluationProfessor {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

	@OneToOne
	@JoinColumn(name = "Traineeship_Position_Id", referencedColumnName = "id")
	private TraineeshipPosition traineeshipPosition;

	@ManyToOne
	@JoinColumn(name = "Evaluator_Id", referencedColumnName = "id")
	private Professor evaluator;

    @Column(name="Motivation_Grade")
    private int motivationGrade;

    @Column(name="Efficiency_Grade")
    private int efficiencyGrade;

    @Column(name="Effectiveness_Grade")
    private int effectivenessGrade;

    @Column(name="Facilities_Grade")
    private int facilitiesGrade;

    @Column(name="Guidance_Grade")
    private int guidanceGrade;

    public EvaluationProfessor() { }
    
    public int getId() { return id; }
    
    public void setId(int id) { this.id = id; }
    
    public int getTraineeshipPositionId() { return traineeshipPosition.getId(); }
    
    public void setTraineeshipPosition(TraineeshipPosition traineeshipPosition) { this.traineeshipPosition = traineeshipPosition; }
    
    public Professor getEvaluator() { return evaluator; }
    
    public void setEvaluator(Professor evaluator) { this.evaluator = evaluator; }
    
    public int getMotivationGrade() { return motivationGrade; }
    
    public void setMotivationGrade(int motivationGrade) { this.motivationGrade = motivationGrade; }
    
    public int getEfficiencyGrade() { return efficiencyGrade; }
    
    public void setEfficiencyGrade(int efficiencyGrade) { this.efficiencyGrade = efficiencyGrade; }
    
    public int getEffectivenessGrade() { return effectivenessGrade; }
    
    public void setEffectivenessGrade(int effectivenessGrade) { this.effectivenessGrade = effectivenessGrade; }
    
    public int getFacilitiesGrade() { return facilitiesGrade; }
    
    public void setFacilitiesGrade(int facilitiesGrade) { this.facilitiesGrade = facilitiesGrade; }
    
    public int getGuidanceGrade() { return guidanceGrade; }
    
    public void setGuidanceGrade(int guidanceGrade) { this.guidanceGrade = guidanceGrade; }
    
    public int getAvgGradeOfStudent() {return (motivationGrade + efficiencyGrade + effectivenessGrade)/3;}
}
