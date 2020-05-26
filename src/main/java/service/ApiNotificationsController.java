package service;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import service.model.Notification;

@Controller
public class ApiNotificationsController {
    @MessageMapping("/topic/game/register/{gameId}")
    //@SendTo("/topic/game/{gameId}")
    public Notification register(@DestinationVariable String gameId, @Payload Notification notification, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", notification.getSender());
        //headerAccessor.setSubscriptionId(notification.getSender());
        return notification;
    }

    @MessageMapping("/topic/game/{gameId}")
    @SendTo("/topic/game/register/{gameId}")
    public String sendMessage(@Payload String notification) {
        System.out.println(notification);
        return notification;
    }
}
