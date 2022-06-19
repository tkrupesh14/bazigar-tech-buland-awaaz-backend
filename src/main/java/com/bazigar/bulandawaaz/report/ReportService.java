package com.bazigar.bulandawaaz.report;

import com.bazigar.bulandawaaz.uploads.posts.ReportRepository;
import com.bazigar.bulandawaaz.User.User;
import com.bazigar.bulandawaaz.User.UserRepository;
import com.bazigar.bulandawaaz.uploads.posts.Post;
import com.bazigar.bulandawaaz.uploads.posts.PostRepository;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    public Response report(Report report) {
        if (report.getReportingUserId() == null || report.getReportedUserId() == null) {
            return new Response(
                    "userId of reporting and reported user is required and cannot be null",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (report.getObjectType() == null || report.getObjectId() == null) {
            return new Response(
                    "Type and id of reported subject is required",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (report.getReportType() == null) {
            return new Response(
                    "report description is required",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        Optional<User> reportingUser = userRepository.findById(report.getReportingUserId());

        if (reportingUser.isEmpty()) {
            return new Response(
                    "No user found with the given userId " + report.getReportingUserId(),
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        Optional<User> reportedUser = userRepository.findById(report.getReportedUserId());

        if (reportedUser.isEmpty()) {
            return new Response(
                    "No user found with the given userId " + report.getReportedUserId(),
                    new ResponseStatus(
                            HttpStatus.NOT_FOUND.value(),
                            HttpStatus.NOT_FOUND.getReasonPhrase()
                    )
            );
        }
        if (report.getObjectType().toLowerCase().equals("post")) {
            Optional<Post> reportedPost = postRepository.findById(report.getObjectId());
            if (reportedPost.isEmpty()) {
                return new Response(
                        "No post found with the given postId " + report.getObjectType(),
                        new ResponseStatus(
                                HttpStatus.NOT_FOUND.value(),
                                HttpStatus.NOT_FOUND.getReasonPhrase()
                        )
                );
            } else {
                reportRepository.save(report);
                return new Response(
                        "Post reported successfully",
                        new ResponseStatus(
                                HttpStatus.OK.value(),
                                HttpStatus.OK.getReasonPhrase()
                        )
                );
            }
        }
        reportRepository.save(report);
        return new Response(
                "user reported successfully",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                )
        );
    }

}
