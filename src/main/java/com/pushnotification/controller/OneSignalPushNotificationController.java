package com.pushnotification.controller;

import com.pushnotification.model.BroadCastedNotifications;
import com.pushnotification.model.OneSignalPushNotification;
import com.pushnotification.repository.BroadcastedPushNotificationRepository;
import com.pushnotification.repository.OneSignalPushNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.unbescape.html.HtmlEscape;

import java.util.*;

/**
 * Create by Weslei Dias.
 **/
@RestController
@RequestMapping("/pushnotification")
public class OneSignalPushNotificationController {

    private final RestTemplate restTemplate = new RestTemplate();


    @Autowired
    private OneSignalPushNotificationRepository repository;

    @Autowired
    private BroadcastedPushNotificationRepository broadcastedNotificationRepository;


    @PostMapping("/sendMessageToAllUsers")
    public String sendMessageToAllUsers(@RequestParam("title") String title, @RequestParam("message") String message) {
        try {
            PushNotificationOptions.sendMessageToAllUsers(title, message);
            BroadCastedNotifications notification = new BroadCastedNotifications();
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setDateTime(new Date());
            broadcastedNotificationRepository.save(notification);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }

    @PostMapping("/sendMessageToUser/{userId}/{oneSignalId}")
    public String sendMessageToUser(@PathVariable("userId") String userId,@PathVariable("oneSignalId") String oneSignalId,
                                    @RequestParam("title") String title, @RequestParam("message") String message) {
        try {

            PushNotificationOptions.sendMessageToUser(title, message, oneSignalId);
            OneSignalPushNotification notification = new OneSignalPushNotification();
            notification.setUserRegistrationId(userId);
            notification.setTitle(title);
            notification.setMessage(message);
            notification.setDateTime(new Date());
            repository.save(notification);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping("/saveUserId/{userId}")
    public void saveUserId(@PathVariable("userId") String userId) {
        //OneSignalPushNotification notification = new OneSignalPushNotification();
        //notification.setIdOneSignal(userId);
        //notification.setUserName("User: " + userId);
        //repository.save(notification);
    }


    @Scheduled(fixedDelay = 1000 * 60 * 30)
    public void sendChuckQuotes() {
        IcndbJoke joke = this.restTemplate.getForObject("http://api.icndb.com/jokes/random",
                IcndbJoke.class);
        PushNotificationOptions.sendMessageToAllUsers("Joke for you", HtmlEscape.unescapeHtml(joke.getValue().getJoke().replaceAll("\"", "")));
    }

    @GetMapping("/getUsers")
    public List<Object> getUsers() {
        List<Object> listValues = new ArrayList<>();
        for (OneSignalPushNotification notification : repository.findAll()) {
            Map<String, Object> mapValues = new HashMap<>();
            mapValues.put("userId", notification.getUserRegistrationId());
            listValues.add(mapValues);
        }

        return listValues;
    }

}
