package myy803.traineeshipapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import myy803.traineeshipapp.mappers.CompanyMapper;
import myy803.traineeshipapp.mappers.EvaluationCompanyMapper;
import myy803.traineeshipapp.mappers.TraineeshipOfferMapper;
import myy803.traineeshipapp.mappers.TraineeshipPositionMapper;
import myy803.traineeshipapp.mappers.UserMapper;
import myy803.traineeshipapp.datamodel.TraineeshipOffer;
import myy803.traineeshipapp.datamodel.TraineeshipPosition;
import myy803.traineeshipapp.datamodel.User;
import myy803.traineeshipapp.datamodel.Company;
import myy803.traineeshipapp.datamodel.EvaluationCompany;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;
    
    @Autowired
    private TraineeshipOfferMapper traineeshipOfferMapper;
    @Autowired
    private TraineeshipPositionMapper traineeshipPositionMapper;
    
    @Autowired
    private EvaluationCompanyMapper evaluationCompanyMapper;
    
    @Autowired
	private UserMapper userMapper;

    @Override
    public Company showCompanyProfileService(String username, Model model) {
        Company company = companyMapper.getReferenceById(username);
        model.addAttribute("company", company);
        return companyMapper.getReferenceById(username);
    }
    
    @Override
    public void saveCompanyProfileService(Company company, String username) {
        companyMapper.save(company);
    }

    @Override
    public void saveCompanyAccountService(Company company, String username) {
        User user = userMapper.findByUsername(username);
        company.setId(user.getUsername());
        companyMapper.save(company);
    }
    
    @Override
    public void getOffersByCompanyService(Principal principal, Model model) {
    	String companyId = principal.getName();
        model.addAttribute("offers", traineeshipOfferMapper.findByCompany_Id(companyId));
        model.addAttribute("positions", traineeshipPositionMapper.findByCompany_Id(companyId));
    }

    @Override
    public void showCreateOfferFormService(Model model) {
        model.addAttribute("traineeshipOffer", new TraineeshipOffer());
    }
    
    @Override
    public void saveOfferService(TraineeshipOffer offer, Principal principal) {
    	
    	String username = principal.getName();
        Company company = companyMapper.getReferenceById(username);
        TraineeshipPosition position = new TraineeshipPosition();
        position.setCompany(company);
        offer.setCompany(company);
        offer.setTraineeshipPosition(position);
        position.setOffer(offer);
        traineeshipPositionMapper.save(position);
        traineeshipOfferMapper.save(offer);
        
    }
        
    @Override
    public void deleteOfferService(int offerId) {
		TraineeshipOffer offer = traineeshipOfferMapper.getReferenceById(offerId);
		if (offer.getTraineeshipPosition() != null) {
	        offer.getTraineeshipPosition().setOffer(null);
	    }
        traineeshipOfferMapper.delete(offer);
    }

    @Override
    public void deletePositionService(int positionId) {
        TraineeshipPosition position = traineeshipPositionMapper.getReferenceById(positionId);
        traineeshipPositionMapper.delete(position);
    }

    @Override
    public void getAssignedTraineeshipsService(Principal principal, Model model) {
    	String companyId = principal.getName();
        model.addAttribute("assignedTraineeships", traineeshipPositionMapper.findByStudentIsNotNullAndCompany_Id(companyId));
    }
    
    @Override
	public void showEvaluationsService(int positionId, Principal principal, Model model) {
    	EvaluationCompany evaluation = evaluationCompanyMapper.findByTraineeshipPosition_Id(positionId);
        TraineeshipPosition position = traineeshipPositionMapper.getReferenceById(positionId);
        List<Integer> marks = IntStream.rangeClosed(1, 5).boxed().collect(Collectors.toList());
        model.addAttribute("marks", marks);
        model.addAttribute("grade", position.getMark());
        model.addAttribute("evaluation", evaluation);
	}

    @Override
    public void saveEvaluationService(EvaluationCompany evaluation, int traineeshipId, Principal principal) {
        Company evaluator = companyMapper.getReferenceById(principal.getName());
        EvaluationCompany evaluationInDB = evaluationCompanyMapper.findByTraineeshipPosition_Id(traineeshipId);
        if (evaluator.getId().equals(principal.getName())){
        	evaluationInDB.setEffectivenessGrade(evaluation.getEffectivenessGrade());
        	evaluationInDB.setEfficiencyGrade(evaluation.getEfficiencyGrade());
        	evaluationInDB.setMotivationGrade(evaluation.getMotivationGrade());
        	evaluationCompanyMapper.save(evaluationInDB);
        }
        
    }
}
