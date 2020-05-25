package service;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import service.model.Notification;

@Controller
public class ApiNotificationsController {
    @MessageMapping("/game.register")
    @SendTo("/topic/public")
    public Notification register(@Payload Notification notification, SimpMessageHeaderAccessor headerAccessor) {
        //headerAccessor.getSessionAttributes().put("username", notification.getSender());
        headerAccessor.setSubscriptionId(notification.getSender());
        return notification;
    }

    @MessageMapping("/game.send")
    @SendTo("/topic/public")
    public Notification sendMessage(@Payload Notification notification) {
        return notification;
    }
}
