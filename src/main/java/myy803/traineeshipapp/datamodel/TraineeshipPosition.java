package myy803.traineeshipapp.datamodel;

import jakarta.persistence.*;

@Entity
@Table(name="traineeship_position")
public class TraineeshipPosition {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @OneToOne(mappedBy = "traineeshipPosition", cascade = CascadeType.ALL, orphanRemoval = true)
    private TraineeshipOffer offer;
    
    @OneToOne(mappedBy = "traineeshipPosition", cascade = CascadeType.ALL, orphanRemoval = true)
    private EvaluationCompany evaluationCompany;
    
    @OneToOne(mappedBy = "traineeshipPosition", cascade = CascadeType.ALL, orphanRemoval = true)
    private EvaluationProfessor evaluationProfessor;

    @ManyToOne
    @JoinColumn(name = "Company_Id", referencedColumnName = "id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "Student_Id", referencedColumnName = "id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "Professor_Id", referencedColumnName = "id")
    private Professor professor;

    @Column(name = "Mark")
    private myy803.traineeshipapp.datamodel.Mark mark;
    
    @Column(name = "Logbook", columnDefinition = "TEXT")
    private String logbook;

    public TraineeshipPosition() {
    	mark = myy803.traineeshipapp.datamodel.Mark.NOT_MARKED;
    	logbook = "";
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
    
    public TraineeshipOffer getOffer() { return offer; }

    public void setOffer(TraineeshipOffer offer) { this.offer = offer; }
    
    public EvaluationCompany getEvaluationCompany() { return evaluationCompany; }

    public void setEvaluationCompany(EvaluationCompany evaluationCompany) { this.evaluationCompany = evaluationCompany; }

    public EvaluationProfessor getEvaluationProfessor() { return evaluationProfessor; }

    public void setEvaluationProfessor(EvaluationProfessor evaluationProfessor) { this.evaluationProfessor = evaluationProfessor; }

    public Company getCompany() { return company; }

    public void setCompany(Company company) { this.company = company; }

    public Student getStudent() { return student; }

    public void setStudent(Student student) { this.student = student; }

    public Professor getProfessor() { return professor; }

    public void setProfessor(Professor professor) { this.professor = professor; }
    
    public myy803.traineeshipapp.datamodel.Mark getMark() {return mark;}
    
    public void setMark(myy803.traineeshipapp.datamodel.Mark mark) {this.mark = mark;}
    
    public String getLogbook() {return logbook;}
    
    public void setLogbook(String logbook) {this.logbook = logbook;}
    
    public void setPassed() {this.mark = myy803.traineeshipapp.datamodel.Mark.PASSED;}
    
    public void setFailed() {this.mark = myy803.traineeshipapp.datamodel.Mark.FAILED;}
}