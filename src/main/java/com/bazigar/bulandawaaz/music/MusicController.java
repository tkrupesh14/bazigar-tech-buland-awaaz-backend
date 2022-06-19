package com.bazigar.bulandawaaz.music;

import com.bazigar.bulandawaaz.util.Response;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

@RestController
@RequestMapping("/my-post-api/index.php/api/User")
public class MusicController {

    @Autowired
    private MusicService musicService;

    @PostMapping(value = "/addMusic")
    private Response addMusic(MultipartFile audioFile, String audioName,
                              String artist, String description, MultipartFile thumbnail) throws IOException, UnsupportedAudioFileException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        return musicService.addMusic(audioFile, audioName, artist, description, thumbnail);
    }

    @PostMapping(value = "/getAllMusic")
    private Response getAllMusic(String key, Integer pageNo) {
        return musicService.getAllMusic(key, pageNo);
    }
}
