package service;
import domain.GameEvent;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import service.model.Notification;
import service.pojos.GameEventDTO;

@Controller
public class ApiNotificationsController {

    private final domain.controllers.GameController controller;
    public ApiNotificationsController(){
        controller = new domain.controllers.GameController();
    }

    @MessageMapping("/topic/game/register/{gameId}")
    @SendTo("/topic/game/{gameId}")
    public Notification register(@DestinationVariable String gameId, @Payload Notification notification, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", notification.getSender());
        System.out.println("here");
        //headerAccessor.setSubscriptionId(notification.getSender());
        return notification;
    }

    @MessageMapping("/topic/game/{gameId}")
    @SendTo("/topic/game/register/{gameId}")
    public String sendMessage(@Payload GameEventDTO notification) {
        System.out.println(notification);
        controller.addGameEventToGame(notification.getGameId(),notification.getMinutes(),notification.getEvent(), notification.getDescription());
        notification.pinDate();
        System.out.println(notification);
        return notification.toString();
    }
}
