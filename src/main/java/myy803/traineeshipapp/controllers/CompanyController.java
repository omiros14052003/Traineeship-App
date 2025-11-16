package myy803.traineeshipapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import myy803.traineeshipapp.datamodel.Company;
import myy803.traineeshipapp.datamodel.EvaluationCompany;
import myy803.traineeshipapp.datamodel.TraineeshipOffer;
import myy803.traineeshipapp.services.CompanyService;

import java.security.Principal;

@Controller
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/dashboard")
    public String companyDashboard() {
        return "company/dashboard";
    }
    
    @RequestMapping("/profile")
    public String showCompanyProfile(Model model, Principal principal) {
    	companyService.showCompanyProfileService(principal.getName(), model);
        return "company/profile";
    }
    
    @PostMapping("/save_profile")
    public String saveCompanyProfile(@ModelAttribute("company") Company company, Principal principal) {
        companyService.saveCompanyProfileService(company, principal.getName());
        return "redirect:/company/dashboard";
    }

    @GetMapping("/open_positions")
    public String getOffersByCompany(Model model, Principal principal) {
        companyService.getOffersByCompanyService(principal, model);
        return "company/open_positions";
    }

    @GetMapping("/open_positions/new")
    public String showCreateOfferForm(Model model) {
        companyService.showCreateOfferFormService(model);
        return "company/create_offer";
    }

    @PostMapping("/open_positions/save")
    public String saveOffer(@ModelAttribute("traineeshipOffer") TraineeshipOffer offer, Principal principal) {
        companyService.saveOfferService(offer, principal);
        return "redirect:/company/open_positions";
    }

    @GetMapping("/open_positions/delete_offer")
    public String deleteOffer(@RequestParam("offer_id") int offerId) {
        companyService.deleteOfferService(offerId);
        return "redirect:/company/open_positions";
    }
    
    @GetMapping("/open_positions/delete_position")
    public String deletePosition(@RequestParam("position_id") int positionId) {
        companyService.deletePositionService(positionId);
        return "redirect:/company/open_positions";
    }

    @GetMapping("/assigned_positions")
    public String getAssignedTraineeships(Model model, Principal principal) {
        companyService.getAssignedTraineeshipsService(principal, model);
        return "company/assigned_positions";
    }

    @GetMapping("/assigned_positions/evaluate")
    public String showEvaluationForm(@RequestParam("traineeshipId") int traineeshipId, Model model, Principal principal) {
        companyService.showEvaluationsService(traineeshipId, principal, model);
        return "company/company_evaluation";
    }

    @PostMapping("/assigned_positions/evaluate/save")
    public String saveEvaluation(@ModelAttribute("evaluation") EvaluationCompany evaluation, Principal principal) {
        companyService.saveEvaluationService(evaluation, evaluation.getTraineeshipPosition().getId(), principal);
        return "redirect:/company/assigned_positions";
    }
}