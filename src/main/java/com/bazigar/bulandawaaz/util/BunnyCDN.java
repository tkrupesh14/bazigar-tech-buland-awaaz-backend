package com.bazigar.bulandawaaz.util;

import com.bazigar.bulandawaaz.User.UserService;
import com.google.gson.Gson;
import okhttp3.*;
import okhttp3.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class BunnyCDN {

    private final String BASE_URL = "https://sg.storage.bunnycdn.com/";
    private final String STORAGE_ZONE_NAME = "buland-awaaz/";
    private final String VIDEO_ACCESS_KEY = "80f7eab6-c98d-4fcc-96e91ad05ba3-435b-4040";
    private final String IMAGE_ACCESS_KEY = "99786880-3476-4569-af6d6283f0e6-ffc4-460d";
    private final String CDN_HOST_NAME = "vz-b8534722-cb2.b-cdn.net";
    private final String POST_COLLECTION_ID = "7c8e3c32-a34a-4d4b-936b-1b55c4ffe90b";
    private final String REELS_COLLECTION_ID = "ba923d09-055c-466a-898b-7355a3b5b9b9";
    private final String MPTV_COLLECTION_ID= "1d099606-5d0b-4d82-9162-e5cdee760c11";
    private final String STORY_COLLECTION_ID = "ae3a4b82-1645-4ce9-b19e-baa069643481";

    public String getMimeType(MultipartFile file) {
        return file.getContentType();
    }

    public String getFileType(String userType, MultipartFile file) {
        String mimeType = getMimeType(file);
        if (Objects.equals(userType, "USER")) {
            return mimeType.contains("image") ? "image" : "video";
        }
        return "";
    }

    public String getUploadPath(String rootDir,
                                String userId, String userType, MultipartFile file) {

        String fileType = getFileType(userType, file);
        Timestamp timestamp = Timestamp.from(Instant.now());
        String mimeType = getMimeType(file);
        String[] postType = mimeType.split("/");
        String encodedFileName = new UserService().randomTokenGenerator() + "."
                + postType[1];
        String uploadPath;
        if (Objects.equals(rootDir, "filterIcon") ||
                Objects.equals(rootDir, "filterLUT")) {
            uploadPath = "filter/"+rootDir+"/"+encodedFileName;
        }
        else {
            uploadPath = "Buland-Awaaz"+"/"+userId+"/"+rootDir+"/"+encodedFileName;
        }
        return uploadPath;
    }

    public Object uploadFile(String localPath, String rootDir,
                             String userId, String userType, MultipartFile file) throws IOException, JSONException {
        if (Objects.equals(getFileType(userType, file), "video")) {
            String guid = uploadVideoFile(localPath, userId, rootDir, file);
            return List.of("https://"+CDN_HOST_NAME+"/"+guid+"/play_360p.mp4",
                    "https://"+CDN_HOST_NAME+"/"+guid+"/thumbnail.jpg",
                    "https://"+CDN_HOST_NAME+"/"+guid+"/preview.webp",
                    "https://"+CDN_HOST_NAME+"/"+guid+"/playlist.m3u8", guid);
        }
        else {
            String uploadPath = getUploadPath(rootDir, userId, userType, file);
            String url = BASE_URL + STORAGE_ZONE_NAME + uploadPath;
            System.out.println(url);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(2, TimeUnit.MINUTES);
            builder.readTimeout(2, TimeUnit.MINUTES);
            builder.writeTimeout(10, TimeUnit.MINUTES);
            OkHttpClient  client = builder.build();
            MediaType mediaType = MediaType.parse("application/octet-stream");
            RequestBody body = RequestBody.create(mediaType, file.getBytes());
            System.out.println(body);
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .addHeader("Content-Type", "application/octet-stream")
                    .addHeader("AccessKey", IMAGE_ACCESS_KEY)
                    .build();
            Response response = client.newCall(request).execute();
            System.out.println(response);
            return response.code() == 201 ? "https://Buland-Awaaz.b-cdn.net/"+uploadPath : "Failed to upload image Bunny";
        }
    }

    public String uploadImageToStorage(MultipartFile file, String rootDir, String userId) throws IOException {
        String uploadPath = getUploadPath(rootDir, userId, "USER", file);
        String url = BASE_URL + STORAGE_ZONE_NAME + uploadPath;
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, file.getBytes());
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("AccessKey", IMAGE_ACCESS_KEY)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response);
        return response.code() == 201 ? "https://Buland-Awaaz.b-cdn.net/"+uploadPath : "Failed to upload image!";
    }

    public String uploadVideoToStorage(MultipartFile file, String rootDir, String userId) throws IOException {
        String uploadPath = getUploadPath(rootDir, userId, "USER", file);
        String url = BASE_URL + STORAGE_ZONE_NAME + uploadPath;
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, file.getBytes());
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("AccessKey", IMAGE_ACCESS_KEY)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response);
        return response.code() == 201 ? "https://Buland-Awaaz.b-cdn.net/"+uploadPath : "Failed to upload video!";
    }

    public String uploadProfileVideoFile(String localPath, String userId, MultipartFile file, String rootDir, String userType) throws IOException {
        String uploadPath = getUploadPath(rootDir, userId, userType, file);
        String url = BASE_URL + STORAGE_ZONE_NAME + uploadPath;
        System.out.println(url);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(2, TimeUnit.MINUTES);
        builder.readTimeout(2, TimeUnit.MINUTES);
        builder.writeTimeout(10, TimeUnit.MINUTES);
        OkHttpClient  client = builder.build();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, file.getBytes());
        System.out.println(body);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("AccessKey", IMAGE_ACCESS_KEY)
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response);
        return response.code() == 201 ? "https://Buland-Awaaz.b-cdn.net/"+uploadPath : "Failed to create video for uploading";
    }

    public String uploadVideoFile(String localPath, String userId,
                                  String rootDir, MultipartFile file) throws IOException, JSONException {
//        FileReader uploadFile = new FileReader(localPath);
        String fileType = getMimeType(file);
        // Creating the video file and obtaining an ID for it
        Object createdVideoData = createVideo(userId, rootDir);
        if (createdVideoData instanceof String) {
            System.out.println(createdVideoData);
            return "Failed to create video for uploading";
        }
        else {
            String guid = ((VideoCreatedResponse) createdVideoData).getGuid();
            String url = "http://video.bunnycdn.com/library/25783/videos/"+guid;
//        Map<String, String> post = Collections.singletonMap("file", "@" + uploadFile);

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(2, TimeUnit.MINUTES);
            builder.readTimeout(2, TimeUnit.MINUTES);
            builder.writeTimeout(10, TimeUnit.MINUTES);
            OkHttpClient  client = builder.build();

//            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/octet-stream");
            RequestBody body = RequestBody.create( file.getBytes(),mediaType);
            Request request = new Request.Builder()
                    .url(url)
                    .put(body)
                    .addHeader("Content-Type", fileType)
                    .addHeader("AccessKey", VIDEO_ACCESS_KEY)
                    .build();
            try {
                Response response = client.newCall(request).execute();
            }catch (Exception e){
                e.printStackTrace();
            }
            return guid;
        }
    }

    private Object createVideo(String userId, String rootDir) throws IOException, JSONException {
        String collectionId = "";
        CollectionsList collectionsList = getAllBunnyCollections();
        // If collection is already created the collection id using the guid field
        for (com.bazigar.bulandawaaz.util.Collection i: collectionsList.getItems()) {
            if (Objects.equals(i.getName(), userId)) {
                collectionId = i.getGuid();
            }
        }
        // If there was no collection that was created for a user, then create it
        if (Objects.equals(collectionId, "")) {
            collectionId = createCollection(userId);
        }
        String videoTitle = "";
        if (Objects.equals(rootDir, "post")) {
            videoTitle = new UserService().randomTokenGenerator() + "P";
        }
        else if (Objects.equals(rootDir, "story")) {
            videoTitle = new UserService().randomTokenGenerator() + "S";
        }
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/*+json");
        RequestBody body = RequestBody.create(mediaType, "{\"title\":\""+videoTitle+"\", \"collectionId\":\""+collectionId+"\"}");
        System.out.println("{\"title\":\""+videoTitle+"\", \"collectionId\":\""+collectionId+"\"}");
        Request request = new Request.Builder()
                .url("http://video.bunnycdn.com/library/25783/videos")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/*+json")
                .addHeader("AccessKey", VIDEO_ACCESS_KEY)
                .build();
        System.out.println(request);
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            String jsonData = response.body().string();
            JSONObject responseAsJson = new JSONObject(jsonData);
            return new Gson().fromJson(responseAsJson.toString(), VideoCreatedResponse.class);
        }
        else {
            return response.toString();
        }
    }

    private Object createVideoAd(String rootDir) throws IOException, JSONException {
        String collectionId = "";
        CollectionsList collectionsList = getAllBunnyCollections();
        String videoTitle = "";
        if (Objects.equals(rootDir, "post")) {
            videoTitle = new UserService().randomTokenGenerator() + "P";
        }
        else if (Objects.equals(rootDir, "story")) {
            videoTitle = new UserService().randomTokenGenerator() + "S";
        }
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/*+json");
        RequestBody body = RequestBody.create(mediaType, "{\"title\":\""+videoTitle+"\", \"collectionId\":\""+collectionId+"\"}");
        System.out.println("{\"title\":\""+videoTitle+"\", \"collectionId\":\""+collectionId+"\"}");
        Request request = new Request.Builder()
                .url("http://video.bunnycdn.com/library/25783/videos")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/*+json")
                .addHeader("AccessKey", VIDEO_ACCESS_KEY)
                .build();
        System.out.println(request);
        Response response = client.newCall(request).execute();
        if (response.code() == 200) {
            String jsonData = response.body().string();
            JSONObject responseAsJson = new JSONObject(jsonData);
            return new Gson().fromJson(responseAsJson.toString(), VideoCreatedResponse.class);
        }
        else {
            return response.toString();
        }
    }

    private CollectionsList getAllBunnyCollections() throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://video.bunnycdn.com/library/25783/collections?page=1&itemsPerPage=100&orderBy=date")
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("AccessKey", VIDEO_ACCESS_KEY)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String jsonData = response.body().string();
            JSONObject responseAsJson = new JSONObject(jsonData);
            System.out.println(responseAsJson);
            return new Gson().fromJson(responseAsJson.toString(), CollectionsList.class);
        }
        return new CollectionsList();
    }

    private String createCollection(String userId) throws IOException, JSONException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/*+json");
        RequestBody body = RequestBody.create(mediaType, "{\"name\":\""+userId+"\"}");
        Request request = new Request.Builder()
                .url("http://video.bunnycdn.com/library/25783/collections")
                .post(body)
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/*+json")
                .addHeader("AccessKey", VIDEO_ACCESS_KEY)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String responseAsString = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseAsString);
            com.bazigar.bulandawaaz.util.Collection newCollection = new Gson().fromJson(jsonResponse.toString(), Collection.class);
            return newCollection.getGuid();
        }
        return "Couldn't create new collection!";
    }

    public String uploadAudioFile(MultipartFile audio) throws IOException {
//        String url = BASE_URL + STORAGE_ZONE_NAME + "audio/"+audio.getOriginalFilename();
//        System.out.println(url);
//        OkHttpClient client = new OkHttpClient();
//        MediaType mediaType = MediaType.parse("application/octet-stream");
//        RequestBody body = RequestBody.create(mediaType, audio.getBytes());
//        System.out.println(body);
//        Request request = new Request.Builder()
//                .url(url)
//                .put(body)
//                .addHeader("Content-Type", "audio/mpeg")
//                .addHeader("AccessKey", IMAGE_ACCESS_KEY)
//                .build();
//        Response response = client
//                .newCall(request)
//                .execute();
//        System.out.println(response);
//        return response.code() == 201 ? "https://Buland-Awaaz.b-cdn.net/"+audio.getOriginalFilename() : "Failed to upload audio!";

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, audio.getBytes());
        Request request = new Request.Builder()
                .url("https://storage.bunnycdn.com/buland-awaaz/audio/"+audio.getOriginalFilename())
                .put(body)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("AccessKey", "80f7eab6-c98d-4fcc-96e91ad05ba3-435b-4040")
                .build();
        Response response = client.newCall(request).execute();
        return response.code() == 201 ? "https://Buland-Awaaz.b-cdn.net/"+audio.getOriginalFilename() : "Failed to upload audio!";
    }

    public String uploadAd(MultipartFile adFile) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, adFile.getBytes());
        Request request = new Request.Builder()
                .url("https://storage.bunnycdn.com/buland-awaaz/customAds/"+adFile.getOriginalFilename())
                .put(body)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("AccessKey", "80f7eab6-c98d-4fcc-96e91ad05ba3-435b-4040")
                .build();
        Response response = client.newCall(request).execute();
        return response.code() == 201 ? "https://Buland-Awaaz.b-cdn.net/"+adFile.getOriginalFilename() : "Failed to upload adFile!";
    }

    public String uploadMusicLogo(MultipartFile logo) throws IOException {
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/octet-stream");
        RequestBody body = RequestBody.create(mediaType, logo.getBytes());
        Request request = new Request.Builder()
                .url("https://storage.bunnycdn.com/buland-awaaz/audio/logo/"+logo.getOriginalFilename())
                .put(body)
                .addHeader("Content-Type", "application/octet-stream")
                .addHeader("AccessKey", "80f7eab6-c98d-4fcc-96e91ad05ba3-435b-4040")
                .build();
        Response response = client.newCall(request).execute();
        return response.code() == 201 ? "https://Buland-Awaaz.b-cdn.net/"+logo.getOriginalFilename() : "Failed to upload logo!";
    }
}
