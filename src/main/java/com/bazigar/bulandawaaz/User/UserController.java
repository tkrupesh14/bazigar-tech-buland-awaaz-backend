package com.bazigar.bulandawaaz.User;

import com.bazigar.bulandawaaz.util.Response;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(path = "/buland-awaaz/api/User")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/register_user")
    public Response registerUser(UserDataProvider user) {
        return userService.registerUser(user);
    }

    @PostMapping(path = "/username_exists")
    public Response userNameExists(String userName) {
        return userService.userNameExists(userName);
    }

    @PostMapping(path = "/login_user")
    public Response loginUser(LoginHelper user) {
        return userService.loginUser(user,null);
    }

//    @PostMapping(path = "/register_user_with_two_step_auth")
//    public Response registerUserWith2StepAuth(UserDataProvider user) throws JSONException {
//        return userService.registerUserWith2StepAuth(user);
//    }

    @PostMapping(path = "/login_user_with_two_step_auth")
    public Response loginUserWith2StepAuth(LoginHelper helper) {
        return userService.loginUserWith2StepAuth(helper);
    }

    @PostMapping(path = "/verify_registration_otp")
    public Response verifyRegistrationOTP(Long validationId, String otp) {
        return userService.verifyRegistrationOTP(validationId, otp);
    }

    @PostMapping(path = "/fetch_popular_users")
    public Response fetchPopularUsers(Long userId,String city,String state,Integer pageNo ) {
        return userService.getPopularUser(userId,city,state, pageNo);
    }
    @PostMapping(path = "/make_popular")
    public Response makePopular(Long userId) {
        return userService.makePopular(userId);
    }
    @PostMapping(path = "/make_reporter")
    public Response make_reporter(Long userId) {
        return userService.makeReporter(userId);
    }
    @PostMapping(path = "/delete")
    public Response deleteUser(Long userId) {
        return userService.deleteUser(userId);
    }

    @PostMapping(path = "/verify_login_OTP")
    public Response loginAfterVerifying(Long userId, String otp) {
        return userService.loginAfterVerifying(userId, otp);
    }
    @PostMapping(path = "/verify_OTP")
    public Response verifyOtp(String email, String otp) {
        return userService.verifyOtp(email, otp);
    }

    @PostMapping(path = "/sendOTPToEmailOrPhone")
    public Response sendOTPToEmailOrPhone(String emailOrPhone) throws IOException {
        return userService.sendOTPToEmailOrPhone(emailOrPhone);
    }
    @PostMapping(path = "/verification_otp")
    public Response sendOtpToEmail(String emailOrPhone) throws IOException {
        return userService.sendOtpToEmail(emailOrPhone);
    }

    @PostMapping(path = "/receiveNotification")
    public Response receiveNotification(Long userId) {
        return userService.receiveNotification(userId);
    }


    @PostMapping(path = "/get_user_profile")
    public Object getUserProfile(Long userId,Long requestingUserId) {
        return userService.getUserProfile(userId,requestingUserId);
    }

    @PostMapping(path = "/change_password")
    public Response changePassword(Long userId, String oldPassword, String newPassword) {
        return userService.changePassword(userId, oldPassword, newPassword);
    }


    @PostMapping(path = "/edit_personal_info")
    public Object editPersonalInfo(String phoneNo, String email, String gender, String dob, Long userId) {
        return userService.editPersonalInfo(phoneNo, email, gender, dob, userId);
    }

    @PostMapping(path = "edit_profile")
    public Object editProfile(String userName, String fullName, String website, Long userId, String bio, MultipartFile profileUrl) throws JSONException, IOException {
        return userService.editProfile(userName, fullName, website, userId, bio);
    }

    @PostMapping(path = "edit_profile_image_or_video")
    public Object editProfileImageAndVideo(Long userId, MultipartFile image, MultipartFile video) throws JSONException, IOException {
        return userService.editProfileImage(userId, image, video);
    }

    @PostMapping(path = "/reset_password")
    public Response resetPassword(String email, String password) {
        return userService.resetPassword(email, password);
    }

    @PostMapping(path = "/update_password_with_otp")
    public Response updatePasswordWithOtp(Long userId, String OTP, String newPassword) {
        return userService.updatePasswordWithOtp(userId, OTP, newPassword);
    }

    @PostMapping(path = "/update_password")
    public Response updatePassword(Long userId, String oldPassword, String newPassword) {
        return userService.updatePassword(userId, oldPassword, newPassword);
    }

    @PostMapping(path = "/updateToken")
    public Response updateToken(Long userId,String token) {
        return userService.updateToken(userId, token);
    }

    @PostMapping(path = "/getPersonalDetails")
    public Response getPersonalDetails(String nameEmailOrPhone) {
        return userService.getPersonalDetails(nameEmailOrPhone);
    }@PostMapping(path = "/updateLocation")
    public Response updateLocation(Long userId,Double latitude, Double longitude,String city, String state, String country){
        return userService.updateLocation(userId,latitude,longitude,city,state,country);
    }


}
