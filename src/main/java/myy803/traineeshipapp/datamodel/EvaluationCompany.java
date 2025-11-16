package myy803.traineeshipapp.datamodel;

import jakarta.persistence.*;

@Entity
@Table(name="evaluation_company")
public class EvaluationCompany {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "Traineeship_Position_Id", referencedColumnName = "id")
    private TraineeshipPosition traineeshipPosition;

    @ManyToOne
    @JoinColumn(name = "Evaluator_Id", referencedColumnName = "id")
    private Company evaluator;

    @Column(name="Motivation_Grade")
    private int motivationGrade;

    @Column(name="Efficiency_Grade")
    private int efficiencyGrade;

    @Column(name="Effectiveness_Grade")
    private int effectivenessGrade;

    public EvaluationCompany() { }
    
    public int getId() { return id; }
    
    public void setId(int id) { this.id = id; }
    
    public TraineeshipPosition getTraineeshipPosition() { return traineeshipPosition; }
    
    public void setTraineeshipPosition(TraineeshipPosition traineeshipPosition) { this.traineeshipPosition = traineeshipPosition; }
    
    public Company getEvaluator() { return evaluator; }
    
    public void setEvaluator(Company evaluator) { this.evaluator = evaluator; }
    
    public int getMotivationGrade() { return motivationGrade; }
    
    public void setMotivationGrade(int motivationGrade) { this.motivationGrade = motivationGrade; }
    
    public int getEfficiencyGrade() { return efficiencyGrade; }
    
    public void setEfficiencyGrade(int efficiencyGrade) { this.efficiencyGrade = efficiencyGrade; }
    
    public int getEffectivenessGrade() { return effectivenessGrade; }
    
    public void setEffectivenessGrade(int effectivenessGrade) { this.effectivenessGrade = effectivenessGrade; }
    
    public int getAvgGradeOfStudent() {return (motivationGrade + efficiencyGrade + effectivenessGrade)/3;}
}
