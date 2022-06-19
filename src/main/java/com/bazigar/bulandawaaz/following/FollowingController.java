package com.bazigar.bulandawaaz.following;

import com.bazigar.bulandawaaz.util.FollowHelper;
import com.bazigar.bulandawaaz.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/buland-awaaz/api/User")
public class FollowingController {

    @Autowired
    private FollowingService followingService;

    @PostMapping("/follow_user")
    public Response addUserToFollowing(FollowHelper helper) {
        return followingService.addUserToFollowing(helper);
    }

    @PostMapping("/fetch_followers")
    public Response getAllFollowers(Long userId,Integer pageNo) {
        return followingService.getAllFollowers(userId,pageNo);
    }

    @PostMapping("/fetch_following")
    public Response getAllFollowing(Long userId,Integer pageNo) {
        return followingService.getAllFollowing(userId,pageNo);
    }

}
