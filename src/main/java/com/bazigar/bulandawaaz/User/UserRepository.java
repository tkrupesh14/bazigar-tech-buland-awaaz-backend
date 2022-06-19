package com.bazigar.bulandawaaz.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select s from User s where s.userName = ?1")
     Optional<User> findUserByUserName(String userName);

    @Query("select s from User s where s.email = ?1")
    public Optional<User> findUserByEmail(String email);

    @Query("select s from User s where s.googleSignInToken = ?1")
    public Optional<User> findUserByGoogleLogInToken(String googleSignToken);

    @Query("select s from User s where s.facebookSingInToken = ?1")
    public Optional<User> findUserByFacebookLogInToken(String facebookSingInToken);

    @Query("select s from User s where s.city = ?1 or s.state = ?2 and s.isPopular = ?3order by s.followerCount desc")
    public Page<User> getPopularUsers(String city,String state,Boolean isPopular, Pageable pageable);

    @Query(nativeQuery = true, value = "select user.* from user where user.user_name like ?1")
    Page<User> findUsersByUserNameRegexOrFullNameRegex(String regex, Pageable pageable);

    @Query("select s from User s where s.fullName = ?1")
    public List<User> findUserByFullName(String fullName);

    @Query("select s from User s where s.phoneNo = ?1")
    public Optional<User> findUserByPhoneNo(String phoneNo);


    @Query("select f from User f where f.fullPhoneNo = ?1")
    public Optional<User> findUserByFullPhoneNo(String fullPhoneNo);

    @Query(value = "select s from User s where not s.id = ?2 and s.phoneNo = ?1")
    Optional<User> findOtherUserByPhoneNo(String phoneNo, Long userId);

    @Query(value = "select s from User s where not s.id = ?2 and s.email = ?1")
    Optional<User> findOtherUserByEmail(String email, Long userId);

    @Query(value = "select s from User s where not s.id = ?2 and s.city = ?1")
    List<User> findOtherUserByLocation(String city, Long userId);



    @Query("select s.userName from User s")
    public List<String> findAllUserNames();

    @Transactional
    @Modifying
    @Query(value = "update User u set u.phoneNo = ?2 where u.id = ?1")
    public void updateUserPhoneNo(Long userId, String phoneNo);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.isPopular = ?2 where u.id = ?1")
    public void updatePopular(Long userId, Boolean isPopular);

    @Transactional
    @Modifying
    @Query("delete from User u where u.id = ?1")
    public void delete(Long userId);
    @Transactional
    @Modifying
    @Query(value = "update User u set u.isReporter = ?2 where u.id = ?1")
    public void updateReporter(Long userId, Boolean isReporter);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.receiveNotification = ?2 where u.id = ?1")
    public void updateReceiveNotification(Long userId, Boolean isReporter);
    @Transactional
    @Modifying
    @Query(value = "update User u set u.email = ?2 where u.id = ?1")
    public void updateEmail(Long userId, String email);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.password = ?2 where u.id = ?1")
    public void updatePassword(Long userId, String password);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.state = ?2,u.latitude=?3,u.longitude=?4,u.city=?5,u.country=?6 where u.id = ?1")
    public void updateLocation(Long userId, String state,Double latitude,Double longitude,String city,String country);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.gender = ?2 where u.id = ?1")
    public void updateGender(Long userId, String gender);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.dob = ?2 where u.id = ?1")
    public void updateDob(Long userId, String dob);



    @Transactional
    @Modifying
    @Query(value = "update User u set u.userName = ?2 where u.id = ?1")
    public void updateUsername(Long userId, String userName);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.firebaseToken = ?2 where u.id = ?1")
    public void updateUserFirebaseToken(Long userId, String firebaseToken);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.followerCount=(u.followerCount+?2) where u.id = ?1")
    public void updateUserFollowers(Long userId, Long updateFollower);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.updatedAt = ?2 where u.id = ?1")
    public void updateUpdatedAt(Long userId, Long updatedAt);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.createdAt = ?2 where u.id = ?1")
    public void updateCreatedAt(Long userId, Long createdAt);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.token = ?2 where u.id = ?1")
    public void updateToken(Long userId, String token);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.firebaseToken = ?2 where u.id = ?1")
    void updateFirebaseToken(Long userId, String firebaseToken);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.flag = ?2 where u.id = ?1")
    void updateFlag(Long userId, Boolean flag);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.access = ?2 where u.id = ?1")
    void updateAccess(Long userId, Integer access);



    @Transactional
    @Modifying
    @Query(value = "update User u set u.verified = ?2 where u.id = ?1")
    void updateVerified(Long userId, Boolean verified);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.fullName = ?2 where u.id = ?1")
    public void updateFullname(Long userId, String fullName);


    @Transactional
    @Modifying
    @Query(value = "update User u set u.bio = ?2 where u.id = ?1")
    public void updateBio(Long userId, String bio);

    @Transactional
    @Modifying
    @Query(value = "update User u set u.profileUrl = ?2 where u.id = ?1")
    public void updateProfilePic(Long userId, String profileUrl);



}
