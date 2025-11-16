package myy803.traineeshipapp.services;

import myy803.traineeshipapp.datamodel.Company;
import myy803.traineeshipapp.datamodel.EvaluationCompany;
import myy803.traineeshipapp.datamodel.TraineeshipOffer;

import java.security.Principal;

import org.springframework.ui.Model;

public interface CompanyService {
	
    public void getOffersByCompanyService(Principal principal, Model model);

    public void saveOfferService(TraineeshipOffer offer, Principal principal);

    public void deleteOfferService(int offerId);

    public void getAssignedTraineeshipsService(Principal principal, Model model);
    
    public void showEvaluationsService(int positionId, Principal principal, Model model);

    public void saveEvaluationService(EvaluationCompany evaluation, int traineeshipId, Principal principal);
    
    void showCreateOfferFormService(Model model);
    
    public void deletePositionService(int positionId);
    
    Company showCompanyProfileService(String username, Model model);
    
    void saveCompanyProfileService(Company company, String username);
    
    public void saveCompanyAccountService(Company company, String username);
}
