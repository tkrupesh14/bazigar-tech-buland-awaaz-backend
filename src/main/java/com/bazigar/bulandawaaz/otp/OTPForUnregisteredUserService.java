package com.bazigar.bulandawaaz.otp;

import com.bazigar.bulandawaaz.User.UserDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OTPForUnregisteredUserService {

    @Autowired
    private OTPForUnregisteredRepository otpRepository;

    public Long addOTPForEmail(UserDataProvider userDataProvider, String OTP) {
        if (userDataProvider.getEmail() != null && OTP != null) {
            Optional<OTPForUnregisteredUser> user = otpRepository.findOTPForUnregisteredUserByEmail(userDataProvider.getEmail());
            if (user.isPresent()) {
                otpRepository.updateOTPByEmailId(userDataProvider.getEmail(), OTP);
                return user.get().getId();
            }
            OTPForUnregisteredUser unregisteredUser = new OTPForUnregisteredUser(
                    userDataProvider.getEmail(),
                    userDataProvider.getPhoneNo(),
                    OTP,
                    "",
                    userDataProvider.getUserName(),
                    userDataProvider.getFullName(),
                    userDataProvider.getPassword(),
                    userDataProvider.getProfileUrl(),
                    userDataProvider.getBio(),
                    userDataProvider.getDob(),
                    "",
                    0L,
                    0L,
                    userDataProvider.getGender(),
                    userDataProvider.getFirebaseToken(),
                    userDataProvider.getFlag(),
                    userDataProvider.getAccess(),
                    userDataProvider.getVerified(),
                    userDataProvider.getLatitude(),
                    userDataProvider.getLongitude(),
                    userDataProvider.getIp(),
                    userDataProvider.getCountry(),
                    userDataProvider.getDeviceName(),
                    userDataProvider.getAddress(),
                    userDataProvider.getCity()
            );
            otpRepository.save(unregisteredUser);
            return unregisteredUser.getId();
        }
        return 0L;
    }

    public Long addOTPForPhoneNo(UserDataProvider userDataProvider, String OTP) {
        if (userDataProvider.getPhoneNo() != null && OTP != null) {
            Optional<OTPForUnregisteredUser> user = otpRepository.findOTPForUnregisteredUserByPhoneNo(userDataProvider.getPhoneNo());
            if (user.isPresent()) {
                otpRepository.updateOTPByPhoneNo(userDataProvider.getPhoneNo(), OTP);
                return user.get().getId();
            }
            OTPForUnregisteredUser unregisteredUser = new OTPForUnregisteredUser(
                    userDataProvider.getEmail(),
                    userDataProvider.getPhoneNo(),
                    OTP,
                    "",
                    userDataProvider.getUserName(),
                    userDataProvider.getFullName(),
                    userDataProvider.getPassword(),
                    userDataProvider.getProfileUrl(),
                    userDataProvider.getBio(),
                    userDataProvider.getDob(),
                    "",
                    0L,
                    0L,
                    userDataProvider.getGender(),
                    userDataProvider.getFirebaseToken(),
                    userDataProvider.getFlag(),
                    userDataProvider.getAccess(),
                    userDataProvider.getVerified(),
                    userDataProvider.getLatitude(),
                    userDataProvider.getLongitude(),
                    userDataProvider.getIp(),
                    userDataProvider.getCountry(),
                    userDataProvider.getDeviceName(),
                    userDataProvider.getAddress(),
                    userDataProvider.getCity()
            );
            otpRepository.save(unregisteredUser);
            return unregisteredUser.getId();
        }
        return 0L;
    }

    public Optional<OTPForUnregisteredUser> findOTPForUnregisteredUserByEmail(String email) {
        return otpRepository.findOTPForUnregisteredUserByEmail(email);
    }

    public Optional<OTPForUnregisteredUser> findOTPForUnregisteredUserByPhoneNo(String phoneNo) {
        return otpRepository.findOTPForUnregisteredUserByPhoneNo(phoneNo);
    }
}
