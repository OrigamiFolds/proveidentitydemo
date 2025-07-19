/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package draft.dev.kycprovedemo;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.*;

@Controller
/**
 *
 * @author beart
 */
public class Kyccontroller {

    @Autowired

    IdentityService identityService;

    @GetMapping("/signup")

    public String signupForm(Model model) {

        model.addAttribute("signupdetails", new Identity());

        return "signup";

    }



    @RequestMapping(value = "/verify-identity", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE)
    public String handleHtml(@ModelAttribute Identity identity, Model model) {

        model.addAttribute("signupdetails", identity);
        model.addAttribute("authtoken", identityService.verifyIdentity(identity));
        return "signup"; // Thymeleaf template
    }

    @RequestMapping(value = "/verify-identity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> handleJson(@RequestBody Identity identity) {

        String token = identityService.verifyIdentity(identity);
        return Map.of("authtoken", token, "status", "success");
    }

    @PostMapping("/validate")
    @ResponseBody
    public ResponseEntity<?> validate(@RequestBody Map<String, String> payload) {
        String coId = payload.get("correlationID");

        return ResponseEntity.ok(Map.of("success", identityService.validate(coId)));
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute Identity signupdetails,
            @RequestHeader("X-Correlation-ID") String correlationID,
            Model model) {

        // Access form fields via signupdetails.getName(), etc.
        model.addAttribute("signupdetails", signupdetails);

        if (identityService.completeValidation(signupdetails, correlationID)) {

            model.addAttribute("title", "Authentication Complete!");
            model.addAttribute("heading", "Successful Entry");
            model.addAttribute("body", "You've been successfully entered into the competition!");
 
        }
        return "result"; // Thymeleaf view name
    }


}
