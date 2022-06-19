package com.bazigar.bulandawaaz.User;

import com.bazigar.bulandawaaz.email.EmailOTPSender;
import com.bazigar.bulandawaaz.following.FollowingRepository;
import com.bazigar.bulandawaaz.otp.OTP;
import com.bazigar.bulandawaaz.otp.OTPRepository;
import com.bazigar.bulandawaaz.otp.TemporaryOTPRepository;
import com.bazigar.bulandawaaz.search_users.SearchResult;
import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.uploads.posts.PostUploadService;
import com.bazigar.bulandawaaz.following.Following;
import com.bazigar.bulandawaaz.following.FollowingService;
import com.bazigar.bulandawaaz.location.Location;
import com.bazigar.bulandawaaz.location.LocationRepository;
import com.bazigar.bulandawaaz.util.BunnyCDN;
import com.bazigar.bulandawaaz.util.PagedObject;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import com.bazigar.bulandawaaz.otp.*;
import com.bazigar.bulandawaaz.phone.PhoneOTPService;
import com.bazigar.bulandawaaz.settings.Setting;
import com.bazigar.bulandawaaz.settings.SettingsRepository;
import com.bazigar.bulandawaaz.settings.SettingsService;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.util.*;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private OTPRepository otpRepository;

    @Autowired
    private TemporaryOTPRepository temporaryOTPRepository;

    @Autowired
    private PhoneOTPService phoneOTPService;

    @Autowired
    private EmailOTPSender emailOTPSender;

    @Autowired
    private FollowingService followingService;

    @Autowired
    private PostUploadService postUploadService;

    @Autowired
    private FollowingRepository followingRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private SettingsService settingsService;


    public Response registerUser(UserDataProvider user) {

        if (user.getSignUpType().equals("facebook_login")){
            Optional<User> facebookLoginToken = userRepository.findUserByFacebookLogInToken(user.getFacebookSignInToken());
            if (facebookLoginToken.isPresent()) {
                LoginHelper loginHelper=new LoginHelper();
                loginHelper.setFacebookSignInToken(user.getFacebookSignInToken());
                loginHelper.setNameEmailOrPhone(user.getEmail());
              return   loginUser(loginHelper,facebookLoginToken.get());
            }
        }
        if (user.getSignUpType().equals("google_login")){
            Optional<User> googleLogInToken = userRepository.findUserByGoogleLogInToken(user.getGoogleSignInToken());
            if (googleLogInToken.isPresent()) {
                LoginHelper loginHelper=new LoginHelper();
                loginHelper.setGoogleSignInToken(user.getGoogleSignInToken());
                loginHelper.setNameEmailOrPhone(user.getEmail());
           return  loginUser(loginHelper,googleLogInToken.get());
            }
        }
        if (user.getUserName() != null
                && (user.getPassword() != null|| user.getSignUpType().equals("google_login"))||user.getSignUpType().equals("facebook_login")&&
                user.getFullName() != null) {
            Optional<User> userName = userRepository.findUserByUserName(user.getUserName());
            if (userName.isPresent()) {
                return new Response(
                        "Username already exist",
                        new ResponseStatus(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase()
                        )
                );
            }
            if (!user.getEmail().isEmpty()) {
                Optional<User> emailUser = userRepository.findUserByEmail(user.getEmail());
                if (emailUser.isPresent()) {
                    return new Response(
                            "A user with this email already exists!",
                            new ResponseStatus(
                                    HttpStatus.CONFLICT.value(),
                                    HttpStatus.CONFLICT.getReasonPhrase()
                            )
                    );
                }
            }
            User saveUser = new User();
            Timestamp timer = Timestamp.from(Instant.now());
            saveUser.setUserName(user.getUserName());
            saveUser.setFullName(user.getFullName());
            saveUser.setPassword(user.getPassword());
            saveUser.setDob(user.getDob());
            saveUser.setBio(user.getBio());
            saveUser.setGender(user.getGender());
            saveUser.setToken(randomTokenGenerator());
            saveUser.setFirebaseToken(user.getFirebaseToken());
            saveUser.setAccess(user.getAccess());
            saveUser.setVerified(true);
            saveUser.setFlag(user.getFlag());
            saveUser.setUpdatedAt(timer.getTime());
            saveUser.setCreatedAt(timer.getTime());
            saveUser.setEmail(user.getEmail());
            saveUser.setPhoneNo(user.getPhoneNo());
            saveUser.setFullPhoneNo(user.getCountryCode()+user.getPhoneNo());
            saveUser = userRepository.save(saveUser);
            saveUser.setDeviceName(user.getDeviceName());
            saveUser.setSignUpType(user.getSignUpType());
            saveUser.setGoogleSignInToken(user.getGoogleSignInToken());
            saveUser.setFacebookSingInToken(user.getFacebookSignInToken());
            saveUser.setFollowerCount(0L);

            Location userLocation = new Location(
                    saveUser,
                    user.getLatitude(),
                    user.getLongitude(),
                    user.getIp(),
                    user.getCountry(),
                    user.getDeviceName(),
                    user.getAddress(),
                    user.getCity(),
                    timer.getTime()
            );
            locationRepository.save(userLocation);
            Setting userSettings = new Setting(saveUser.getId());
            settingsRepository.save(userSettings);
            return new Response(
                    "User successfully registered!!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    new UserDataWithoutPassword(
                            saveUser.getId(),
                            saveUser.getToken(),
                            saveUser.getUserName(),
                            saveUser.getFullName(),
                            saveUser.getEmail(),
                            saveUser.getPhoneNo(),
                            saveUser.getProfileUrl(),
                            saveUser.getBio(),
                            saveUser.getDob(),
                            saveUser.getCreatedAt(),
                            saveUser.getUpdatedAt(),
                            saveUser.getGender(),
                            saveUser.getFirebaseToken(),
                            saveUser.getFlag(),
                            saveUser.getAccess(),
                            saveUser.getVerified(),
                            saveUser.getDeviceName(),
                            saveUser.getSignUpType(),
                            saveUser.getGoogleSignInToken(),
                            saveUser.getReporter(),
                            saveUser.getFacebookSingInToken()
                    )
            );
        } else {
            return new Response(
                    "Username,  password and full name fields are mandatory",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public String randomTokenGenerator() {
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = upper.toLowerCase(Locale.ROOT);
        String digits = "0123456789";
        String alphanum = upper + lower + digits;
        ArrayList<String> alnum = new ArrayList<String>(Arrays.asList(alphanum.split("")));
        ArrayList<String> token = new ArrayList<String>();
        for (int i = 0; i <= 32; i++) {
            token.add(alnum.get(new Random().nextInt(alnum.size())));
        }
        return String.join("", token);
    }

    public Response loginUser(LoginHelper user,User tokenUser) {
        if (tokenUser!=null) {
                Timestamp timer = Timestamp.from(Instant.now());
                Location currentLoc = new Location(
                        tokenUser,
                        user.getLatitude(),
                        user.getLongitude(),
                        user.getIp(),
                        user.getCountry(),
                        user.getDeviceName(),
                        user.getAddress(),
                        user.getCity(),
                        0L
                );
                currentLoc.setCreatedAt(timer.getTime());
                locationRepository.save(currentLoc);
            userRepository.updateUserFirebaseToken(tokenUser.getId(), user.getFirebaseToken());
                return new Response(
                        "You have been successfully logged in!",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        ),
                        new UserDataWithoutPassword(
                                tokenUser.getId(),
                                tokenUser.getToken(),
                                tokenUser.getUserName(),
                                tokenUser.getFullName(),
                                tokenUser.getEmail(),
                                tokenUser.getPhoneNo(),
                                tokenUser.getProfileUrl(),
                                tokenUser.getBio(),
                                tokenUser.getDob(),
                                tokenUser.getCreatedAt(),
                                tokenUser.getUpdatedAt(),
                                tokenUser.getGender(),
                                tokenUser.getFirebaseToken(),
                                tokenUser.getFlag(),
                                tokenUser.getAccess(),
                                tokenUser.getVerified(),
                                tokenUser.getDeviceName(),
                                tokenUser.getSignUpType(),
                                tokenUser.getGoogleSignInToken(),
                                tokenUser.getReporter(),
                                tokenUser.getFacebookSingInToken()
                        )
                );
            }

            if (user.getNameEmailOrPhone() == null || user.getPassword() == null) {
                return new Response(
                        "nameEmailOrPhone and password is required!",
                        new ResponseStatus(
                                HttpStatus.BAD_REQUEST.value(),
                                HttpStatus.BAD_REQUEST.getReasonPhrase()
                        )
                );
            }
            if (user.getNameEmailOrPhone().contains("@")) {
                Optional<User> emailUser = userRepository.findUserByEmail(user.getNameEmailOrPhone());
                if (emailUser.isEmpty()) {
                    return new Response(
                            "No user found with this email!",
                            new ResponseStatus(
                                    HttpStatus.NOT_FOUND.value(),
                                    HttpStatus.NOT_FOUND.getReasonPhrase()
                            )
                    );
                }
            }
        if (user.getNameEmailOrPhone().contains("@")) {
            Optional<User> emailUser = userRepository.findUserByEmail(user.getNameEmailOrPhone());
            if (emailUser.isEmpty()) {
                return new Response(
                        "No user found with this email!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            else {
                // correct password
                if (Objects.equals(emailUser.get().getPassword(), user.getPassword())) {
                    Timestamp timer = Timestamp.from(Instant.now());
                    Location currentLoc = new Location(
                            emailUser.get(),
                            user.getLatitude(),
                            user.getLongitude(),
                            user.getIp(),
                            user.getCountry(),
                            user.getDeviceName(),
                            user.getAddress(),
                            user.getCity(),
                            0L
                    );
                    currentLoc.setCreatedAt(timer.getTime());
                    locationRepository.save(currentLoc);
                    userRepository.updateUserFirebaseToken(emailUser.get().getId(), user.getFirebaseToken());
                    return new Response(
                            "You have been successfully logged in!",
                            new ResponseStatus(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase()
                            ),
                            new UserDataWithoutPassword(
                                    emailUser.get().getId(),
                                    emailUser.get().getToken(),
                                    emailUser.get().getUserName(),
                                    emailUser.get().getFullName(),
                                    emailUser.get().getEmail(),
                                    emailUser.get().getPhoneNo(),
                                    emailUser.get().getProfileUrl(),
                                    emailUser.get().getBio(),
                                    emailUser.get().getDob(),
                                    emailUser.get().getCreatedAt(),
                                    emailUser.get().getUpdatedAt(),
                                    emailUser.get().getGender(),
                                    emailUser.get().getFirebaseToken(),
                                    emailUser.get().getFlag(),
                                    emailUser.get().getAccess(),
                                    emailUser.get().getVerified(),
                                    emailUser.get().getDeviceName(),
                                    emailUser.get().getSignUpType(),
                                    emailUser.get().getGoogleSignInToken(),
                                    emailUser.get().getReporter(),
                                    emailUser.get().getFacebookSingInToken()
                            )
                    );
                }
            }
        }
        if (user.getNameEmailOrPhone() == null || user.getPassword() == null) {
            return new Response(
                    "nameEmailOrPhone and password is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (user.getNameEmailOrPhone().contains("@")) {
            Optional<User> emailUser = userRepository.findUserByEmail(user.getNameEmailOrPhone());
            if (emailUser.isEmpty()) {
                return new Response(
                        "No user found with this email!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            } else {
                // correct password
                if (Objects.equals(emailUser.get().getPassword(), user.getPassword())) {
                    Timestamp timer = Timestamp.from(Instant.now());
                    Location currentLoc = new Location(
                            emailUser.get(),
                            user.getLatitude(),
                            user.getLongitude(),
                            user.getIp(),
                            user.getCountry(),
                            user.getDeviceName(),
                            user.getAddress(),
                            user.getCity(),
                            0L
                    );
                    currentLoc.setCreatedAt(timer.getTime());
                    locationRepository.save(currentLoc);
                    userRepository.updateUserFirebaseToken(emailUser.get().getId(), user.getFirebaseToken());
                    return new Response(
                            "You have been successfully logged in!",
                            new ResponseStatus(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase()
                            ),
                            new UserDataWithoutPassword(
                                    emailUser.get().getId(),
                                    emailUser.get().getToken(),
                                    emailUser.get().getUserName(),
                                    emailUser.get().getFullName(),
                                    emailUser.get().getEmail(),
                                    emailUser.get().getPhoneNo(),
                                    emailUser.get().getProfileUrl(),
                                    emailUser.get().getBio(),
                                    emailUser.get().getDob(),
                                    emailUser.get().getCreatedAt(),
                                    emailUser.get().getUpdatedAt(),
                                    emailUser.get().getGender(),
                                    emailUser.get().getFirebaseToken(),
                                    emailUser.get().getFlag(),
                                    emailUser.get().getAccess(),
                                    emailUser.get().getVerified(),
                                    emailUser.get().getDeviceName(),
                                    emailUser.get().getSignUpType(),
                                    emailUser.get().getGoogleSignInToken(),
                                    emailUser.get().getReporter(),
                                    emailUser.get().getFacebookSingInToken()
                            )
                    );
                }
                // wrong password
                else {
                    return new Response(
                            "Wrong password!!",
                            new ResponseStatus(
                                    HttpStatus.UNAUTHORIZED.value(),
                                    HttpStatus.UNAUTHORIZED.getReasonPhrase()
                            )
                    );
                }
            }
        } else if (Pattern.matches("[0-9]+", user.getNameEmailOrPhone())) {
            Optional<User> phoneUser = userRepository.findUserByPhoneNo(user.getNameEmailOrPhone());
            if (phoneUser.isEmpty()) {
                return new Response(
                        "No user found for given phone number!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            } else {
                // password entered is correct
                if (Objects.equals(phoneUser.get().getPassword(), user.getPassword())) {
                    Timestamp timer = Timestamp.from(Instant.now());
                    Location currentLoc = new Location(
                            phoneUser.get(),
                            user.getLatitude(),
                            user.getLongitude(),
                            user.getIp(),
                            user.getCountry(),
                            user.getDeviceName(),
                            user.getAddress(),
                            user.getCity(),
                            0L
                    );
                    currentLoc.setCreatedAt(timer.getTime());
                    locationRepository.save(currentLoc);
                    userRepository.updateUserFirebaseToken(phoneUser.get().getId(), user.getFirebaseToken());
                    return new Response(
                            "You have been successfully logged in!",
                            new ResponseStatus(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase()
                            ),
                            new UserDataWithoutPassword(
                                    phoneUser.get().getId(),
                                    phoneUser.get().getToken(),
                                    phoneUser.get().getUserName(),
                                    phoneUser.get().getFullName(),
                                    phoneUser.get().getEmail(),
                                    phoneUser.get().getPhoneNo(),
                                    phoneUser.get().getProfileUrl(),
                                    phoneUser.get().getBio(),
                                    phoneUser.get().getDob(),
                                    phoneUser.get().getCreatedAt(),
                                    phoneUser.get().getUpdatedAt(),
                                    phoneUser.get().getGender(),
                                    phoneUser.get().getFirebaseToken(),
                                    phoneUser.get().getFlag(),
                                    phoneUser.get().getAccess(),
                                    phoneUser.get().getVerified(),
                                    phoneUser.get().getDeviceName(),
                                    phoneUser.get().getSignUpType(),
                                    phoneUser.get().getGoogleSignInToken(),
                                    phoneUser.get().getReporter(),
                                    phoneUser.get().getFacebookSingInToken()
                            )
                    );
                }
                // password entered is wrong
                else {
                    return new Response(
                            "Wrong password!!",
                            new ResponseStatus(
                                    HttpStatus.UNAUTHORIZED.value(),
                                    HttpStatus.UNAUTHORIZED.getReasonPhrase()
                            )
                    );
                }
            }
        } else {
            Optional<User> user1 = userRepository.findUserByUserName(user.getNameEmailOrPhone());
            if (user1.isEmpty()) {
                return new Response(
                        "No user found with this userName!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            } else {
                if (Objects.equals(user1.get().getPassword(), user.getPassword())) {
                    Timestamp timer = Timestamp.from(Instant.now());
                    Location currentLoc = new Location(
                            user1.get(),
                            user.getLatitude(),
                            user.getLongitude(),
                            user.getIp(),
                            user.getCountry(),
                            user.getDeviceName(),
                            user.getAddress(),
                            user.getCity(),
                            0L
                    );
                    currentLoc.setCreatedAt(timer.getTime());
                    locationRepository.save(currentLoc);
                    userRepository.updateUserFirebaseToken(user1.get().getId(), user.getFirebaseToken());
                    return new Response(
                            "You have been successfully logged in!",
                            new ResponseStatus(
                                    HttpStatus.OK.value(),
                                    HttpStatus.OK.getReasonPhrase()
                            ),
                            new UserDataWithoutPassword(
                                    user1.get().getId(),
                                    user1.get().getToken(),
                                    user1.get().getUserName(),
                                    user1.get().getFullName(),
                                    user1.get().getEmail(),
                                    user1.get().getPhoneNo(),
                                    user1.get().getProfileUrl(),
                                    user1.get().getBio(),
                                    user1.get().getDob(),
                                    user1.get().getCreatedAt(),
                                    user1.get().getUpdatedAt(),
                                    user1.get().getGender(),
                                    user1.get().getFirebaseToken(),
                                    user1.get().getFlag(),
                                    user1.get().getAccess(),
                                    user1.get().getVerified(),
                                    user1.get().getDeviceName(),
                                    user1.get().getSignUpType(),
                                    user1.get().getGoogleSignInToken(),
                                    user1.get().getReporter(),
                                    user1.get().getFacebookSingInToken()
                            )
                    );
                } else {
                    return new Response(
                            "Wrong password!!",
                            new ResponseStatus(
                                    HttpStatus.UNAUTHORIZED.value(),
                                    HttpStatus.UNAUTHORIZED.getReasonPhrase()
                            )
                    );
                }
            }
        }
    }

    public Object getUserProfile(Long userId,Long requestingUserId) {
        if (userId == null) {
            return new Response(
                    "userId is required and cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        List<Following> followingUsersList = null;
        if (requestingUserId!=null)
            followingUsersList = followingRepository.findUsersFollowedByUserId(requestingUserId);
        boolean followingOrNot=false;

        if (followingUsersList!=null)
        for (Following i :followingUsersList){
            if (Objects.equals(i.getFollowingUserId(), userId)){
                followingOrNot=true;
                break;
            }
        }
        int postCount = postUploadService.getPostCount(userId);
        int followersCount = followingService.getFollowersCount(userId);
        int followingCount = followingService.getFollowingCount(userId);
        UserDataWithoutPassword data = new UserDataWithoutPassword(
                userOptional.get().getId(),
                userOptional.get().getToken(),
                userOptional.get().getUserName(),
                userOptional.get().getFullName(),
                userOptional.get().getEmail(),
                userOptional.get().getPhoneNo(),
                userOptional.get().getProfileUrl(),
                userOptional.get().getBio(),
                userOptional.get().getDob(),
                userOptional.get().getCreatedAt(),
                userOptional.get().getUpdatedAt(),
                userOptional.get().getGender(),
                userOptional.get().getFirebaseToken(),
                userOptional.get().getFlag(),
                userOptional.get().getAccess(),
                userOptional.get().getVerified(),
                userOptional.get().getDeviceName(),
                userOptional.get().getSignUpType(),
                userOptional.get().getGoogleSignInToken(),
                userOptional.get().getReporter(),
                userOptional.get().getFacebookSingInToken()
        );

        data.setFollowersCont(String.valueOf(followersCount));
        data.setFollowingCount(String.valueOf(followingCount));
        data.setPostCount(String.valueOf(postCount));
        data.setFollowingOrNot(followingOrNot);
        data.setReporter(userOptional.get().getReporter());
        data.setPopular(userOptional.get().getPopular());
        data.setReceiveNotification(userOptional.get().getReceiveNotification());

        return new Response(
                "Obtained User data",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                data
        );
    }

    public Object editPersonalInfo(String phoneNo, String email, String gender,
                                   String dob, Long userId) {
        if (userId == null) {
            return "userId is required and cannot be null!";
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        ArrayList<String> ss = new ArrayList<>();
        if (phoneNo != null) {
            Optional<User> phoneUser = userRepository.findOtherUserByPhoneNo(phoneNo, userId);
            if (phoneUser.isPresent()) {
                return new Response(
                        "This phone number is already taken by someone else!!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            userRepository.updateUserPhoneNo(userId, phoneNo);
            ss.add("phoneNo");
        }
        if (email != null) {
            Optional<User> emailUser = userRepository.findOtherUserByEmail(email, userId);
            if (emailUser.isPresent()) {
                return new Response(
                        "This email has already been taken!!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            userRepository.updateEmail(userId, email);
            ss.add("email");
        }
        if (gender != null) {
            userRepository.updateGender(userId, gender);
            ss.add("gender");
        }
        if (dob != null) {
            userRepository.updateDob(userId, dob);
            ss.add("dob");
        }
        if (ss.isEmpty()) {
            return new Response(
                    "No updation done!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        ss.add("have been updated!");
        return new Response(
                String.join(", ", ss),
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Object editProfile(String userName, String fullName, String website,
                              Long userId, String bio) throws JSONException, IOException {
        if (userId == null) {
            return new Response(
                    "userId is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        ArrayList<String> ss = new ArrayList<>();
        if (userName != null) {
            Optional<User> userByUserName = userRepository.findUserByUserName(userName);
            if (userByUserName.isPresent() && !Objects.equals(userByUserName.get().getId(), userId)) {
                return new Response(
                        "User with this userName already exists!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            userRepository.updateUsername(userId, userName);
            ss.add("userName");
        }
        if (fullName != null) {
            userRepository.updateFullname(userId, fullName);
            ss.add("fullName");
        }
        if (bio != null) {
            userRepository.updateBio(userId, bio);
            ss.add("bio");
        }
        if (ss.isEmpty()) {
            return new Response(
                    "No updation done!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        ss.add("have been updated!");
        return new Response(
                String.join(", ", ss),
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Object editProfileImage(Long userId, MultipartFile image, MultipartFile video) throws JSONException, IOException {
        if (userId == null) {
            return new Response(
                    "userId is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        BunnyCDN bunny = new BunnyCDN();
        if (image == null && video == null) {
            return new Response(
                    "Either image or video or both is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (image == null && video != null) {
            Object value = bunny.uploadProfileVideoFile(video.getOriginalFilename(),
                    userId.toString(), video, "profile", "USER");
            if (value == "Failed to create video for uploading") {
                return new Response(
                        "video could not be uploaded",
                        new ResponseStatus(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                        )
                );
            }
        }
        if (image != null && video == null) {
            Object value = bunny.uploadFile(image.getOriginalFilename(), "profile",
                    userId.toString(), "USER", image);
            if (value == "Failed to upload image") {
                return new Response(
                        "Profile picture could not be updated!",
                        new ResponseStatus(
                                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
                        )
                );
            }
            String imageUrl = (String) value;
            userRepository.updateProfilePic(userId, imageUrl);
            return new Response(
                    "Profile picture updated successfully!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        // If both image and video are given for uploading!
        ArrayList<String> wasNotUpdated = new ArrayList<>();
        Object imgVal = bunny.uploadFile(image.getOriginalFilename(), "profile",
                userId.toString(), "USER", image);
        Object videoVal = bunny.uploadFile(video.getOriginalFilename(), "profile",
                userId.toString(), "USER", video);
        if (imgVal == "") {
            wasNotUpdated.add("profile picture");
        }
        if (videoVal == "") {
            wasNotUpdated.add("profile video");
        }
        String imageUrl = (String) imgVal;
        userRepository.updateProfilePic(userId, imageUrl);
        // If image was not successfully uploaded but video was, then
        if (wasNotUpdated.contains("profile picture") && !wasNotUpdated.contains("profile video")) {
            return new Response(
                    "profile picture was updated but profile video was not!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        // Video was successfully uploaded but image was not
        else if (!wasNotUpdated.contains("profile picture") && wasNotUpdated.contains("profile video")) {
            return new Response(
                    "profile video was updated but profile picture was not!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        } else if (wasNotUpdated.contains("profile picture") && wasNotUpdated.contains("profile video")) {
            return new Response(
                    "Both profile picture and video updations failed!",
                    new ResponseStatus(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED.getReasonPhrase()
                    )
            );
        } else {
            return new Response(
                    "Both profile picture and profile video updated successfully!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
    }

    public Response updatePassword(Long userId, String oldPassword, String newPassword) {
        if (userId == null || oldPassword == null || newPassword == null) {
            return new Response(
                    "userId, oldPassword and newPassword is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            return new Response(
                    "oldPassword or new password cannot be empty!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No userData found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        if (Objects.equals(user.get().getPassword(), oldPassword)) {
            Optional<User> userData = userRepository.findById(userId);
            if (userData.isEmpty()) {
                return new Response(
                        "No userData found with the given userId",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            userRepository.updatePassword(userId, newPassword);
            return new Response(
                    "Password successfully updated!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "oldPassword is incorrect!!",
                new ResponseStatus(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                )
        );
    }

    public Response updateToken(Long userId, String token) {
        if (userId == null || token==null) {
            return new Response(
                    "userId, token are required",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No userData found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
                    Optional<User> userData = userRepository.findById(userId);
            if (userData.isEmpty()) {
                return new Response(
                        "No userData found with the given userId",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }

            userRepository.updateUserFirebaseToken(userId, token);
            return new Response(
                    "Password successfully updated!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
    }


    public Response updatePasswordWithOtp(Long userId, String OTP, String newPassword) {
        if (userId == null || OTP == null || newPassword == null) {
            return new Response(
                    "userId, OTP and newPassword is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (OTP.isEmpty()) {
            return new Response(
                    "OTP cannot be empty!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<com.bazigar.bulandawaaz.otp.OTP> otpForUser = otpRepository.findOTPByUserId(userId);
        if (otpForUser.isEmpty()) {
            return new Response(
                    "It seems that you have this user has not yet requested for OTP",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        if (Objects.equals(otpForUser.get().getOneTimePassword(), OTP)) {
            Optional<User> user = userRepository.findById(userId);
            if (user.isEmpty()) {
                return new Response(
                        "No user found with the given userId",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            userRepository.updatePassword(userId, newPassword);
            return new Response(
                    "Password successfully updated!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "OTP invalid!!",
                new ResponseStatus(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                )
        );
    }

    public Response loginUserWith2StepAuth(LoginHelper helper) {
        if (helper.getNameEmailOrPhone() == null || helper.getPassword() == null) {
            return new Response(
                    "nameEmailOrPhone and password is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (helper.getNameEmailOrPhone().contains("@")) {
            Optional<User> emailUser = userRepository.findUserByEmail(helper.getNameEmailOrPhone());
            if (emailUser.isEmpty()) {
                return new Response(
                        "No user found with this email!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            } else {
                // correct password
                if (Objects.equals(emailUser.get().getPassword(), helper.getPassword())) {
                    return emailOTPSender.sendOTPEmailForLogin(emailUser.get().getId(), helper.getNameEmailOrPhone());
                }
                // wrong password
                else {
                    return new Response(
                            "Wrong password!!",
                            new ResponseStatus(
                                    HttpStatus.UNAUTHORIZED.value(),
                                    HttpStatus.UNAUTHORIZED.getReasonPhrase()
                            )
                    );
                }
            }
        } else if (Pattern.matches("\\+[0-9]+", helper.getNameEmailOrPhone())) {
            Optional<User> phoneUser = userRepository.findUserByPhoneNo(helper.getNameEmailOrPhone());
            if (phoneUser.isEmpty()) {
                return new Response(
                        "No user found for given phone number!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            } else {
                // password entered is correct
                if (Objects.equals(phoneUser.get().getPassword(), helper.getPassword())) {
                    return phoneOTPService.sendOTPPhoneForLogin(phoneUser.get().getId(), helper.getNameEmailOrPhone());
                }
                // password entered is wrong
                else {
                    return new Response(
                            "Wrong password!!",
                            new ResponseStatus(
                                    HttpStatus.UNAUTHORIZED.value(),
                                    HttpStatus.UNAUTHORIZED.getReasonPhrase()
                            )
                    );
                }
            }
        } else {
            Optional<User> user1 = userRepository.findUserByUserName(helper.getNameEmailOrPhone());
            if (user1.isEmpty()) {
                return new Response(
                        "No user found with this userName!",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            } else {
                if (Objects.equals(user1.get().getPassword(), helper.getPassword())) {
                    if (user1.get().getEmail() != null && !user1.get().getEmail().isEmpty()) {
                        return emailOTPSender.sendOTPEmailForLogin(user1.get().getId(), user1.get().getEmail());
                    }
                    return phoneOTPService.sendOTPPhoneForLogin(user1.get().getId(), user1.get().getPhoneNo());
                } else {
                    return new Response(
                            "Wrong password!!",
                            new ResponseStatus(
                                    HttpStatus.UNAUTHORIZED.value(),
                                    HttpStatus.UNAUTHORIZED.getReasonPhrase()
                            )
                    );
                }
            }
        }
    }

    public Response loginAfterVerifying(Long userId, String otp) {

        if (userId == null || otp == null) {
            return new Response(
                    "userId and otp are both required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<OTP> otpUser = otpRepository.findOTPByOtpId(userId);
        if (otpUser.isPresent()) {
            if (Objects.equals(otpUser.get().getOneTimePassword(), otp)) {
                return new Response(
                        "Logged in successfully!",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        )
                );
            }
            return new Response(
                    "Invalid OTP!!",
                    new ResponseStatus(
                            HttpStatus.UNAUTHORIZED.value(),
                            HttpStatus.UNAUTHORIZED.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "This user has not yet requested for an OTP!",
                new ResponseStatus(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                )
        );
    }

    public Response verifyOtp(String email, String otp) {

        if (email == null || otp == null) {
            return new Response(
                    "email and otp are both required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given email",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<OTP> otpUser = otpRepository.findOTPByEmailAddress(email);
        if (otpUser.isPresent()) {
            if (Objects.equals(otpUser.get().getOneTimePassword(), otp)) {
                return new Response(
                        "Verified successfully!",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        )
                );
            }
            return new Response(
                    "Invalid OTP!!",
                    new ResponseStatus(
                            HttpStatus.UNAUTHORIZED.value(),
                            HttpStatus.UNAUTHORIZED.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "This user has not yet requested for an OTP!",
                new ResponseStatus(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                )
        );
    }


    public Response resetPassword(String email, String password) {
        if (email == null && password == null) {
            return new Response(
                    "Either one of email or password is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user=userRepository.findUserByEmail(email);
        if (user.isPresent()){
            userRepository.updatePassword(user.get().getId(),password);
            return new Response(
                    "Password reset successful",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Something went wrong",
                new ResponseStatus(
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase()
                )
        );
    }

    public Response sendOTPToEmailOrPhone(String emailOrPhone) throws IOException {
        if (emailOrPhone == null) {
            return new Response(
                    "emailOrPhone is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (emailOrPhone.contains("@")) {
            Optional<User> emailUser = userRepository.findUserByEmail(emailOrPhone);
            if (emailUser.isPresent()) {
                return new Response(
                        "A user with this email already exists!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            return emailOTPSender.sendOTPForRegistration(emailOrPhone);
        } else if (emailOrPhone.matches("[0-9]+")) {
            return new Response(
                    "Phone numbers should have country codes included!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        } else if (emailOrPhone.matches("\\+[0-9]+")) {
            Optional<User> phoneUser = userRepository.findUserByFullPhoneNo(emailOrPhone);
            if (phoneUser.isPresent()) {
                return new Response(
                        "A user with this phone number already exists!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            return phoneOTPService.sendOTPForRegistration(emailOrPhone);
        } else {
            return new Response(
                    "Email or PhoneNo is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response sendOtpToEmail(String emailOrPhone) throws IOException {
        if (emailOrPhone == null) {
            return new Response(
                    "emailOrPhone is required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (emailOrPhone.contains("@")) {
            Optional<User> emailUser = userRepository.findUserByEmail(emailOrPhone);
            if (!emailUser.isPresent()) {
                return new Response(
                        "Email does not exist exists!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            return emailOTPSender.sendOTPForRegistration(emailOrPhone);
        } else if (emailOrPhone.matches("[0-9]+")) {
            return new Response(
                    "Phone numbers should have country codes included!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        } else if (emailOrPhone.matches("\\+[0-9]+")) {
            Optional<User> phoneUser = userRepository.findUserByFullPhoneNo(emailOrPhone);
            if (phoneUser.isPresent()) {
                return new Response(
                        "A user with this phone number already exists!",
                        new ResponseStatus(
                                HttpStatus.CONFLICT.value(),
                                HttpStatus.CONFLICT.getReasonPhrase()
                        )
                );
            }
            return phoneOTPService.sendOTPForRegistration(emailOrPhone);
        } else {
            return new Response(
                    "Email or PhoneNo is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
    }

    public Response verifyRegistrationOTP(Long otpId, String otp) {
        if (otpId == null || otp == null) {
            return new Response(
                    "validationId and otp are both required and cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<OTP> otpData = otpRepository.findById(otpId);
        if (otpData.isPresent()) {
            if (otp.equals(otpData.get().getOneTimePassword())) {
                return new Response(
                        "You have been successfully validated. Please register using your userName",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        )
                );
            }
            return new Response(
                    "Invalid OTP!!",
                    new ResponseStatus(
                            HttpStatus.UNAUTHORIZED.value(),
                            HttpStatus.UNAUTHORIZED.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "It seems you did not request for an OTP. Please do so first!",
                new ResponseStatus(
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()
                )
        );
    }

    public Response changePassword(Long userId, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No user found with the given userId!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        String currentPassword = user.get().getPassword();
        if (!Objects.equals(currentPassword, oldPassword)) {
            return new Response(
                    "Please enter your current password correctly!",
                    new ResponseStatus(
                            HttpStatus.FORBIDDEN.value(),
                            HttpStatus.FORBIDDEN.getReasonPhrase()
                    )
            );
        }
        userRepository.updatePassword(userId, newPassword);
        return new Response(
                "Your password was successfully updated!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response getPersonalDetails(String nameEmailOrPhone) {
        if (nameEmailOrPhone == null) {
            return new Response(
                    "nameEmailOrPhone is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (nameEmailOrPhone.contains("@")) {
            Optional<User> emailUser = userRepository.findUserByEmail(nameEmailOrPhone);
            if (emailUser.isEmpty()) {
                return new Response(
                        "No user found with the given email",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            return new Response(
                    "Here are the personal details!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    new PersonalDetails(
                            emailUser.get().getId(),
                            emailUser.get().getUserName(),
                            emailUser.get().getEmail(),
                            emailUser.get().getPhoneNo(),
                            emailUser.get().getProfileUrl()
                            )
            );
        }
        else if (nameEmailOrPhone.matches("[0-9]+")) {
            Optional<User> phoneUser = userRepository.findUserByPhoneNo(nameEmailOrPhone);
            if (phoneUser.isEmpty()) {
                return new Response(
                        "No user found with the given phoneNo",
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            }
            return new Response(
                    "Here are the personal details!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    new PersonalDetails(
                            phoneUser.get().getId(),
                            phoneUser.get().getUserName(),
                            phoneUser.get().getEmail(),
                            phoneUser.get().getPhoneNo(),
                            phoneUser.get().getProfileUrl()
                            )
            );
        }
        Optional<User> user1 = userRepository.findUserByUserName(nameEmailOrPhone);
        if (user1.isEmpty()) {
            return new Response(
                    "No user found with the given username",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Here are the personal details!",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                new PersonalDetails(
                        user1.get().getId(),
                        user1.get().getUserName(),
                        user1.get().getEmail(),
                        user1.get().getPhoneNo(),
                        user1.get().getProfileUrl()
                )
        );
    }

    public Response updateLocation(Long userId, Double latitude, Double longitude, String city, String state, String country) {
        if (userId == null) {
            return new Response(
                    "userId is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (state.isEmpty()) {
            return new Response(
                    "State cannot be empty!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return new Response(
                    "No userData found with the given userId",
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }

            userRepository.updateLocation(userId,state,latitude,longitude,city,country);
            return new Response(
                    "Location successfully updated!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    )
            );

    }

    public Response userNameExists(String userName) {
        Optional<User> _userName = userRepository.findUserByUserName(userName);
        if (_userName.isPresent()) {
            return new Response(
                    "Username already exist",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        return new Response(
                "Username available",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

    public Response getPopularUser(Long userId,String city,String state, Integer pageNo) {
        if (city == null&&state==null) {
            return new Response(
                    "both city and state cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        } else {
            List<Following> followingList = followingRepository.findUsersFollowedByUserId(userId);
            Pageable paged = PageRequest.of(pageNo, 25);
            Page<User>usersPage=userRepository.getPopularUsers(city,state,true, paged);
            ArrayList<SearchResult> popularUsers=new ArrayList<>();
            Response blockedUsersResponse=settingsService.fetchBlockedUsers(userId);


            for (User user:usersPage){
                SearchResult result=new SearchResult();
                result.setUserId(user.getId().toString());
                result.setUserName(user.getUserName());
                result.setFullName(user.getFullName());
                result.setProfileUrl(user.getProfileUrl());

                for (Following following:followingList){
                   if (Objects.equals(following.getFollowingUserId(), user.getId())){
                       result.setFollowingOrNot(true);
                       break;
                   }
                }
                popularUsers.add(result);
            }
            List<SearchResult>blockedUsers=(List<SearchResult>) blockedUsersResponse.getData();
            ArrayList<SearchResult> temp=new ArrayList<>();
            temp.addAll(popularUsers);
            for (SearchResult u:temp){
                if(Objects.equals(userId.toString(), u.getUserId()))
                    popularUsers.remove(u);
                else
                for (SearchResult p:blockedUsers){
                    if (Objects.equals(p.getUserId(), u.getUserId()))
                        popularUsers.remove(u);

                }
            }

          //  List<User> popularUsers = popularUsersPage.toList();

            return new Response(
                    "Found " + popularUsers.size() + " Users",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    new PagedObject(
                            popularUsers.toArray(),
                            pageNo,
                            usersPage.getTotalPages()
                    )
            );
        }
    }

    public Response makePopular(Long userId) {
        if (userId==null){
            return new Response(
                    "userId cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            return new Response(
                    "No user Found",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        User userUpdated=user.get();

        boolean isPopular;
        if (userUpdated.getPopular()==null)
            isPopular=false;
        else
            isPopular=userUpdated.getPopular();

        String message="";
        if (isPopular){
           message= "Removed popular Successfully";
        }else{
            message= "made popular Successfully";
        }
        userRepository.updatePopular(userId,!isPopular);
        return new Response(
                message,
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),!isPopular);
    }
    public Response makeReporter(Long userId) {
        if (userId==null){
            return new Response(
                    "userId cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            return new Response(
                    "No user Found",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        User userUpdated=user.get();
        boolean isReporter;
        if (userUpdated.getReporter()==null)
            isReporter=false;
        else
            isReporter=userUpdated.getReporter();
        String message="";
        try {

            if (isReporter){
                message= "Removed reporter Successfully";
                userRepository.updateReporter(userId,false);
            }else{
                message= "Made reporter Successfully";
                userRepository.updateReporter(userId,true);
            }

            return new Response(
                    message,
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),!isReporter);

        } catch (Exception e) {
            e.printStackTrace();
        }

            return new Response(
                    message,
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    ));

    }

    public Response deleteUser(Long userId) {
        if (userId==null){
            return new Response(
                    "userId cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            return new Response(
                    "No user Found",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }

        List<Post> postList=postRepository.findPostByUserId(userId);
        for (Post p:postList){
            postRepository.delete(p);
        }
        List<Location> locationList = locationRepository.findByUserId(userId);
        for (Location p:locationList){
            locationRepository.delete(p);
        }
        try {
            userRepository.delete(userId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Response(
                "User deleted successfully",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),true);
    }

    public Response receiveNotification(Long userId) {
        if (userId==null){
            return new Response(
                    "userId cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            return new Response(
                    "No user Found",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        boolean receiveNotification=false;
        if (user.get().getReceiveNotification()!=null){
            receiveNotification=user.get().getReceiveNotification();
        }
        try {
            userRepository.updateReceiveNotification(userId,!receiveNotification);
            String message="";
            if (receiveNotification)
                message="Notification turned off";
            else
                message="Notification turned on";
            return new Response(
                    message,
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),true);
        }catch (Exception e){
            return new Response(
                    e.getMessage(),
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }

    }
}
