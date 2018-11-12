package top.zhacker.ddd.identity.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.zhacker.boot.event.notification.NotificationLog;
import top.zhacker.boot.event.notification.publisher.NotificationPublisher;
import top.zhacker.ddd.identity.application.NotificationApplicationService;


/**
 * Created by zhacker.
 * Time 2018/6/16 下午9:47
 */
//@RestController
@RequestMapping("/v1/notifications")
public class NotificationApi {
  
  @Autowired
  private NotificationApplicationService notificationApplicationService;
  
  @Autowired
  private NotificationPublisher notificationPublisher;
  
  @GetMapping("current")
  public NotificationLog currentNotificationLog(){
    return notificationApplicationService.currentNotificationLog();
  }
  
  @GetMapping("/{notificationId}")
  public NotificationLog getById(@PathVariable("notificationId") String aNotificationId){
    return notificationApplicationService.notificationLog(aNotificationId);
  }
  
  @PostMapping("publish")
  @Transactional
//  @Scheduled(cron = "*/2 * * * * *")
  public void publish(){
    notificationPublisher.publishNotifications();
  }
}
