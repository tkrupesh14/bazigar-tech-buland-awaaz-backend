package com.bazigar.bulandawaaz.music;

import com.bazigar.bulandawaaz.util.BunnyCDN;
import com.bazigar.bulandawaaz.util.PagedObject;
import com.bazigar.bulandawaaz.util.Response;
import com.bazigar.bulandawaaz.util.ResponseStatus;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Service
public class MusicService {

    @Autowired
    private MusicRepository musicRepository;

    @Value("${bazigar.password}")
    private String bazigarPassword;

    public Response addMusic(MultipartFile audioFile, String audioName, String artist, String description, MultipartFile thumbnail) throws IOException, CannotReadException, TagException, InvalidAudioFrameException, ReadOnlyFileException {
        if (audioFile == null) {
            return new Response(
                    "audioFile is required!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        BunnyCDN bunny = new BunnyCDN();
        String audioUrl = bunny.uploadAudioFile(audioFile);
        if (Objects.equals(audioUrl, "Failed to upload audio!")) {
            return new Response(
                    "Audio upload failed!",
                    new ResponseStatus(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED.getReasonPhrase()
                    )
            );
        }

        File convFile = new File(System.getProperty("java.io.tmpdir")+"/"+audioFile.getOriginalFilename());
        audioFile.transferTo(convFile);

        // converting any audio file to .mp3
//        Object convertedAudio = convertAllAudioToMp3(convFile.getAbsolutePath(), System.getProperty("java.io.tmpdir")+"/"+convFile.getParentFile().getName());
//        if (convertedAudio == "Failed to convert!") {
//            return new Response(
//                    "Audio file could not be uploaded!",
//                    new ResponseStatus(
//                            HttpStatus.EXPECTATION_FAILED.value(),
//                            HttpStatus.EXPECTATION_FAILED.getReasonPhrase()
//                    )
//            );
//        }
//        File convertedAudioFile = (File) convertedAudio;
//        System.out.println(bunny.getMimeType(audioFile));
//        System.out.println(bunny.getFileType("USER", audioFile));

        AudioFile audioFile1 = AudioFileIO.read(convFile);
        Long duration = (long)audioFile1.getAudioHeader().getTrackLength();

        Timestamp timer = Timestamp.from(Instant.now());

        String musicThumbnailPath = bunny.uploadMusicLogo(thumbnail);
        if (Objects.equals(musicThumbnailPath, "Failed to upload logo!")) {
            return new Response(
                    "Music logo upload failed!",
                    new ResponseStatus(
                            HttpStatus.EXPECTATION_FAILED.value(),
                            HttpStatus.EXPECTATION_FAILED.getReasonPhrase()
                    )
            );
        }
        Music music = new Music(
                audioUrl,
                duration,
                audioFile.getOriginalFilename(),
                description,
                musicThumbnailPath,
                artist,
                0L
        );
        music.setCreatedAt(timer.getTime());
        musicRepository.save(music);
        return new Response(
                "Audio successfully uploaded",
                new ResponseStatus(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.getReasonPhrase()
                ),
                music.getId()
        );
    }

    private Object convertAllAudioToMp3(String sourcePath, String destinationPath) {
        boolean succeeded = false;
        File target;
        try {
            File source = new File(sourcePath);
            target = new File(destinationPath);
            System.out.println(source);
            System.out.println(target);

            //Audio Attributes
            AudioAttributes javeAudio = new AudioAttributes();
            javeAudio.setCodec("libmp3lame");
            javeAudio.setBitRate(128000);
            javeAudio.setChannels(2);
            javeAudio.setSamplingRate(44100);

            //Encoding attributes
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setOutputFormat("mp3");
            attrs.setAudioAttributes(javeAudio);

            //Encode
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
            succeeded = true;
            return target;

        } catch (Exception ex) {
            ex.printStackTrace();
            succeeded = false;
        }
        return "Failed to convert!";
    }

    public Response getAllMusic(String key, Integer pageNo) {
        if (key == null || pageNo == null) {
            return new Response(
                    "key and pageNo are required and cannot be null!",
                    new ResponseStatus(
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase()
                    )
            );
        }
        if (key.equals(bazigarPassword)) {
            Pageable paged = PageRequest.of(pageNo, 25);
            Page<Music> musicPage = musicRepository.findAllAsPages(paged);
            List<Music> allMusic = musicPage.toList();
            return new Response(
                    "Here is some music to rock and roll!",
                    new ResponseStatus(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase()
                    ),
                    new PagedObject(
                            allMusic.toArray(),
                            pageNo,
                            musicPage.getTotalPages()
                    )
            );
        }
        return new Response(
                "Wrong key!",
                new ResponseStatus(
                        HttpStatus.UNAUTHORIZED.value(),
                        HttpStatus.UNAUTHORIZED.getReasonPhrase()
                )
        );
    }
}
