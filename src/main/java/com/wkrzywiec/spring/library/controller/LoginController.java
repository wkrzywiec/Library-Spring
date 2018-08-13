package com.wkrzywiec.spring.library.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wkrzywiec.spring.library.dto.PasswordDTO;
import com.wkrzywiec.spring.library.dto.ResetEmailDTO;
import com.wkrzywiec.spring.library.dto.UserDTO;
import com.wkrzywiec.spring.library.entity.User;
import com.wkrzywiec.spring.library.entity.UserPasswordToken;
import com.wkrzywiec.spring.library.service.EmailService;
import com.wkrzywiec.spring.library.service.UserServiceImpl;

@Controller
public class LoginController {

	public final String basicPath = "http://localhost:8080/library-spring";
	
	@Autowired
	UserServiceImpl userService;
	
	@Autowired
	EmailService emailService;
	
	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
	@GetMapping("/loginPage")
	public String showLoginPage(){
		return "loginPage";
	}
	
	@GetMapping("/access-denied")
	public String accessDenied(){
		return "access-denied";
	}
	
	@GetMapping("/register-user")
	public String registerUser(Model model){
		
		UserDTO userDTO = new UserDTO();
		model.addAttribute("user", userDTO);
		
		return "register-user";
	}
	
	@PostMapping("/loginPage")
	public String showLoginPageAfterSignUp(){
		return "loginPage";
	}
	
	@PostMapping("/register-user")
	public String processRegisterForm(
				@Valid @ModelAttribute("user") UserDTO userDTO,
				BindingResult bindingResult,
				Model model) {
		
		String currentPrincipalName = "";
		User user = null;
		
		if (authentication != null) {
			currentPrincipalName = authentication.getName();
		}
	
		if (bindingResult.hasErrors()){
			return "register-user";
		} else {
			user = userService.saveReaderUser(userDTO, currentPrincipalName);
			emailService.sendUserRegistrationConfirmEmail(user);
			model.addAttribute("message", "You have been successfully registered as a new user. In a couple of minutes you will receive confirmation email.");
			return "success";
		}
	}
	
	@GetMapping("/forgot-password")
	public String showForgotPassword(Model model) {
		
		ResetEmailDTO email = new ResetEmailDTO();
		model.addAttribute("email", email);
		
		return "user-reset-password";
	}
	
	@PostMapping("/submit-forgot-password")
	public String submitForgotPassword(
			@ModelAttribute("email") ResetEmailDTO email,
			Model model) {
		
		boolean isEmailInDatabase = false;
		UserPasswordToken passwordResetTokenEntity = null;
		
		isEmailInDatabase = userService.isEmailAlreadyInUse(email.getEmail());
		
		if (!isEmailInDatabase) {
			model.addAttribute("message", "There is no user with such email address in our database.");
			return "user-reset-password";
		}
		
		if (userService.isUserAlreadyHasResetPasswordToken(email.getEmail())) {
			passwordResetTokenEntity = userService.updateResetPasswordTokenForEmail(email.getEmail());
		} else {
			passwordResetTokenEntity = userService.createPasswordResetTokenForEmail(email.getEmail());
		}
		emailService.sendForgotPasswordEmail(	passwordResetTokenEntity.getUser(),
				basicPath + "/changePassword",
				passwordResetTokenEntity.getToken());
		model.addAttribute("message", "Your password was reset. Check your email for further steps.");
		return "success";
	}
	
	@GetMapping("/changePassword")
	public String setNewPassword(	@RequestParam(value="userId") Integer userId,
									@RequestParam(value="token") String token,
									Model model) {
		
		User user = null;
		user = userService.getUserById(userId);
		
		if (user == null || !user.isEnable()) {
			model.addAttribute("message", "Your account has been deactivated. Please contact the admin team to fix it out. Thanks!");
		} else {
			if (userService.isUserTokenValid(userId, token)) {
				PasswordDTO passwordDTO = new PasswordDTO();
				model.addAttribute("password", passwordDTO);
				return "user-change-password";
			} else {
				model.addAttribute("message", "Your token probably expired or is invalid. Please reset your password once again. Thanks!");
			}
		}		
		return "error";
	}
	
	@PostMapping("/changePassword")
	public String updateUserPassword(	@RequestParam(value="userId") Integer userId,
										@RequestParam(value="token") String token,
										@Valid @ModelAttribute("password") PasswordDTO passwordDTO,
										BindingResult bindingResult,
										Model model) {
		
		if (bindingResult.hasErrors()){
			return "user-change-password";
		} else {
			if (userService.isUserTokenValid(userId, token)) {
				userService.updateUserPassword(userId, passwordDTO);
				userService.deleteUserPassword(userId);
				model.addAttribute("message", "Your password was succefully reset.");
				return "success";
			} else {
				return "error";
			}
		}
	}
	
}
